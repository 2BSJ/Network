package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

public class EchoServerReceiveThread extends Thread {

	
	private Socket socket;
	
	public EchoServerReceiveThread(Socket socket){
		this.socket=socket;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		InetSocketAddress inetRemoteSocketAddress = (InetSocketAddress)socket.getRemoteSocketAddress();
		//다운캐스팅은 암묵적이아니라 명시해야한다.
		//업캐스팅은 반대.
		String remoteHostAddress = inetRemoteSocketAddress.getAddress().getHostAddress();
		int remotePort = inetRemoteSocketAddress.getPort();
		EchoServer.log("[server] connected by client[" + remoteHostAddress + ":" + remotePort + " ]");


		try
		{
			//4. IOStream 받아오기
			//원래 소켓의 인풋스트림은 SocketInputStream인데 추상화해서 부모클래스를 가져옴
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();

			BufferedReader br = new BufferedReader(
					new InputStreamReader(socket.getInputStream(),"utf-8") );
			
			PrintWriter pr = new PrintWriter(
					new OutputStreamWriter(socket.getOutputStream(),"utf-8"),true );
			
			
			
			
			
			while(true)
			{	
				//5. 데이터 읽기
				String data = br.readLine();
				if(data == null) {
					EchoServer.log("closed by client");
					break;
				}
				
				EchoServer.log("received:" + data);
				
				//6. 데이터 쓰기
				pr.println(data);
				
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
	}
}
