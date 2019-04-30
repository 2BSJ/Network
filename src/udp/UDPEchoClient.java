package udp;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class UDPEchoClient {

	private static final String SERVER_IP = "192.168.1.5";
	private static final int SERVER_PORT = 5002;
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		DatagramSocket serverSocket = null;
		//public final void main -> 메소드를 오버라이드 하지 말라는의미 
		//메인함수를 쓰는곳이 여기가 끝이라는거

		//public final class -> 클래스를 상속 하지 말라는 의미
		//객체변수 클래스변수 static 

		//1. 소켓 생성
		Scanner scanner = null;
		DatagramSocket socket = null;


		try {
			scanner = new Scanner(System.in);
			socket = new DatagramSocket();
			//2. 서버 연결
			//socket.connect(new InetSocketAddress(SERVER_IP,SERVER_PORT));
			//		log("connected");
			//서버 연결은 뺌
		

			/*
			//3. IOStream 받아오기

			BufferedReader br = new BufferedReader(
					new InputStreamReader(socket.getInputStream(),"utf-8") );

			PrintWriter pr = new PrintWriter(
					new OutputStreamWriter(socket.getOutputStream(),"utf-8"),true );
*/ //iostream도 안받아 와도됨

			while(true)
			{
				System.out.print(">>");
				String line = scanner.nextLine();
				if("quit".contentEquals(line)) {
					break;
				}
				
				byte[] sendData = line.getBytes("utf-8");
				
				DatagramPacket sendPacket = new DatagramPacket(sendData,
						sendData.length,
						new InetSocketAddress(SERVER_IP,UDPEchoServer.PORT));
				
				socket.send(sendPacket);

				//6. 데이터 쓰기
				//pr.println(line);//ln 으로 개행을 붙여야 서버에서 인식함 print로 보내면 안됨

				//7. 데이터 읽기
//				String data = br.readLine();
	//			if(data ==null) {
		//			log("closed by server");
			//		break;
			//	}
				DatagramPacket receivePacket = new DatagramPacket(new byte[UDPEchoServer.BUFFER_SIZE], UDPEchoServer.BUFFER_SIZE);
				socket.receive(receivePacket);

				String message = new String(receivePacket.getData(), 0, receivePacket.getLength(),"utf-8");
				
				//8. console 출력
				System.out.println("<<" + message);
			}



		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

				if(scanner !=null) {
					scanner.close();
				}
				if(socket != null && socket.isClosed() == false)
					socket.close();
			} 
		}
	
	public static void log(String log)
	{
		System.out.println("[server] " + log);
	}

}
