package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket; //ctr shift o
import java.net.SocketTimeoutException;

public class TCPClient {
	private static final String SERVER_IP = "192.168.1.5";
	private static final int SERVER_PORT = 5000; 
	//final 붙이면 값 변경이 불가능 다른곳에서 대입할수 없음.!!

	public static void main(String[] args) {

		//public final void main -> 메소드를 오버라이드 하지 말라는의미 
		//메인함수를 쓰는곳이 여기가 끝이라는거

		//public final class -> 클래스를 상속 하지 말라는 의미
		//객체변수 클래스변수 static 

		//1. 소켓 생성
		Socket socket = null;


		try {

			socket = new Socket();
			
			//1-1. 소켓 버퍼 사이즈 확인
			int receiveBufferSize = socket.getReceiveBufferSize();
			int sendBufferSize = socket.getSendBufferSize();
			System.out.println(receiveBufferSize + ":" + sendBufferSize);
			
			//1-2. 소켓 버퍼 사이즈 변경
			socket.setReceiveBufferSize(1024*10);
			socket.setSendBufferSize(1024*10);
			
			System.out.println("------------------------");
			receiveBufferSize = socket.getReceiveBufferSize();
			sendBufferSize = socket.getSendBufferSize();
			System.out.println(receiveBufferSize + ":" + sendBufferSize);
			
			
			//1-3. SO_NODELAY(Nagle Algorithm off)
			socket.setTcpNoDelay(true);
			
			//1-4 SO_TIMEOUT
			socket.setSoTimeout(1000);
			
			//2. 서버 연결
			socket.connect(new InetSocketAddress(SERVER_IP,SERVER_PORT));
			System.out.println("[client] connected");

			//3. IOStream 받아오기
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();

			//4.쓰기
			String data = "Hello Wordl\n";
			os.write(data.getBytes("utf-8"));

			//5.읽기
			byte[] buffer = new byte[256];
			int readByteCount = is.read(buffer); // blocking
			if(readByteCount == -1) {
				System.out.println("[client] closed by server");
			}

			data = new String(buffer, 0, readByteCount,"utf-8");
			System.out.println("[client ] received : " + data);
		} catch (SocketTimeoutException e) {
			System.out.println("[client] time out");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(socket != null && socket.isClosed() == false)
					socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
