package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class TCPServer {

	public static void main(String[] args) {

		ServerSocket serverSocket = null;
		try {
			// 1. 서버소켓 생성
			serverSocket = new ServerSocket();

			
			//1-1 Time-Wait 시간에 소켓에 해당 포트번호 할당을 가능하게 하기 위함
			serverSocket.setReuseAddress(true);
			// 2. 주소바인딩
			// : Socket에 SocketAddress(IPAddress + Port) 를 바인딩한다.

			//InetAddress inetAddress = InetAddress.getLocalHost();
			//String localhost = inetAddress.getHostAddress();
			//serverSocket.bind(new InetSocketAddress(localhost, 5000));
			//serverSocket.bind(new InetSocketAddress(inetAddress, 5000));

			serverSocket.bind(new InetSocketAddress("0.0.0.0", 5000));

			//3.accept
			// : 클라이언트의 연결요청을 기다림
			Socket socket = serverSocket.accept(); // blocking

			InetSocketAddress inetRemoteSocketAddress = (InetSocketAddress)socket.getRemoteSocketAddress();
			//다운캐스팅은 암묵적이아니라 명시해야한다.
			//업캐스팅은 반대.
			String remoteHostAddress = inetRemoteSocketAddress.getAddress().getHostAddress();
			int remotePort = inetRemoteSocketAddress.getPort();

			System.out.println("[server] connected by client[" + remoteHostAddress + ":" + remotePort + " ]");


			try
			{
				//4. IOStream 받아오기
				//원래 소켓의 인풋스트림은 SocketInputStream인데 추상화해서 부모클래스를 가져옴
				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();

				while(true)
				{
					//5. 데이터 읽기
					//inputstream,outputstream은 byte계열임 다
					byte[] buffer = new byte[256];
					int readByteCount = is.read(buffer); // block 되어있어서 위에 accept로 못들어감
					//그렇기 떄문에 하나를 연결하고 나서 다른 클라로 연결하려면 연결이 안된다.
					if(readByteCount == -1) {
						//정상종료( close() 메소드 호출 하고 끊은경우)
						//close() 메서드가 호출되면 4번의 클라이언트와 서버간의 패킷교환이 일어나는데
						//이런 교환이 일어나야 우아한 종료가 된다.
						System.out.println("[server] closed by client");
					}
					String data = new String(buffer, 0,readByteCount,"utf-8");
					System.out.println("[server] received:" + data);
					
					//6.데이터  쓰기
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					os.write(data.getBytes("utf-8"));
				}

			}catch(IOException e)
			{
				e.printStackTrace();
			}finally {
				try {
					if(socket != null && socket.isClosed() == false )
						socket.close();
				}
				catch(SocketException e)
				{
					System.out.println("[server] sudden closed by client");
				}
				catch(IOException e) {
					e.printStackTrace();
				}
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

}

//accept를 쓰면 backlog가 있는데
