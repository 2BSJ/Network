package echo;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class EchoServer {
	
	private static final int SERVER_PORT = 7000;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerSocket serverSocket = null;
		try {
			// 1. 서버소켓 생성
			serverSocket = new ServerSocket();

			// 2. 주소바인딩
			// : Socket에 SocketAddress(IPAddress + Port) 를 바인딩한다.

			//InetAddress inetAddress = InetAddress.getLocalHost();
			//String localhost = inetAddress.getHostAddress();
			//serverSocket.bind(new InetSocketAddress(localhost, 5000));
			//serverSocket.bind(new InetSocketAddress(inetAddress, 5000));

			serverSocket.bind(new InetSocketAddress("0.0.0.0", SERVER_PORT));
			log("server starts...[port:"+SERVER_PORT +"]");

			
			while(true) {
			//3.accept
			// : 클라이언트의 연결요청을 기다림
			Socket socket = serverSocket.accept(); // blocking
			
			Thread thread = new EchoServerReceiveThread(socket);
			thread.start();
			}
			
			
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {

			try {
				if(serverSocket !=null && serverSocket.isClosed() == false)
					serverSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void log(String log)
	{
		System.out.println("[server#"+Thread.currentThread().getId()+ " ]"+ log);
	}

}
