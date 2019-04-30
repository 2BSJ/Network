package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {
	private static final String SERVER_IP = "192.168.1.5";
	private static final int SERVER_PORT = 7000;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ServerSocket serverSocket = null;
		//public final void main -> 메소드를 오버라이드 하지 말라는의미 
				//메인함수를 쓰는곳이 여기가 끝이라는거

				//public final class -> 클래스를 상속 하지 말라는 의미
				//객체변수 클래스변수 static 

				//1. 소켓 생성
		Scanner scanner = null;
				Socket socket = null;
				
				


				try {
					scanner = new Scanner(System.in);
					socket = new Socket();
					
										
					//2. 서버 연결
					socket.connect(new InetSocketAddress(SERVER_IP,SERVER_PORT));
					log("connected");

					//3. IOStream 받아오기
					
					BufferedReader br = new BufferedReader(
							new InputStreamReader(socket.getInputStream(),"utf-8") );
					
					PrintWriter pr = new PrintWriter(
							new OutputStreamWriter(socket.getOutputStream(),"utf-8"),true );
					

					while(true)
					{
						System.out.print(">>");
						String line = scanner.nextLine();
						if("quit".contentEquals(line)) {
							break;
						}
						
						//6. 데이터 쓰기
						pr.println(line);//ln 으로 개행을 붙여야 서버에서 인식함 print로 보내면 안됨
						
						//7. 데이터 읽기
						String data = br.readLine();
						if(data ==null) {
							log("closed by server");
							break;
						}
						
						//8. console 출력
						System.out.println("<<" + data);
					}

					

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {
						if(scanner !=null) {
							scanner.close();
						}
						if(socket != null && socket.isClosed() == false)
							socket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
	}
	
	public static void log(String log)
	{
		System.out.println("[server] " + log);
	}


}
