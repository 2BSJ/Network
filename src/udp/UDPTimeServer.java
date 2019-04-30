package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UDPTimeServer {

	public static final int PORT = 5001;
	public static final int BUFFER_SIZE = 1024;
	public static void main(String[] args) {
		DatagramSocket socket = null;
		DatagramPacket sendPacket = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd(E) HH:mm:ss a");
		Calendar cal = Calendar.getInstance();
		/*
		int YEAR = cal.get(Calendar.YEAR);
		int MONTH = cal.get(Calendar.MONTH)+1;
		int DATE = cal.get(Calendar.DATE);
		int HOUR = cal.get(Calendar.HOUR);
		int MINUTE = cal.get(Calendar.MINUTE);
		int SECOND = cal.get(Calendar.SECOND);
		String time = YEAR+"-"+MONTH+"-"+DATE+" "+HOUR+":"+MINUTE+":"+SECOND;
		//일일히 할 필요없이 simpledateformat 객체 만들고 양식 적어논다음 date객체 넣기만하면 쉽게 생성
		*/
		
		try {
			//1. socket 생성
			socket = new DatagramSocket(PORT);
			
			while(true) {
				//2. 데이터 수신
				//오는 패킷 복사
				DatagramPacket receivePacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
				socket.receive(receivePacket);
				
				byte[] data = receivePacket.getData();
				int length = receivePacket.getLength();
				String message = new String(data, 0, length, "utf-8");
				//클라이언트에서 요청메세지인 ""이 들어오면 데이터 전송을 시간으로 해줌
				if(message.equals("")) {
					String time1=sdf.format(cal.getTime());
					//3-1 요청들어왔을때 타임 전송
					System.out.println("[server] requested time");
					byte[] sendTime1 = time1.getBytes("utf-8");
					sendPacket = new DatagramPacket(sendTime1, sendTime1.length,receivePacket.getAddress(),receivePacket.getPort());
					socket.send(sendPacket);
				}
				else {
				
					System.out.println("[server] received:" + message);
					//3. 데이터 전송
					byte[] sendData = message.getBytes("utf-8");
					sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), receivePacket.getPort());
					socket.send(sendPacket);
				}
			}
			
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if(socket!=null && socket.isClosed()==false)
			socket.close();
		}


	}

}
