package http;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.file.Files;

public class RequestHandler extends Thread {
	private static String documentRoot = "";
	
	static {			
			documentRoot = RequestHandler.class.getClass().getResource("/webapp")
					.getPath();
	}
	private Socket socket;
	//
	public RequestHandler( Socket socket ) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		try {
			// get IOStream
			

			// logging Remote Host IP Address & Port
			InetSocketAddress inetSocketAddress = ( InetSocketAddress )socket.getRemoteSocketAddress();
			consoleLog( "connected from " + inetSocketAddress.getAddress().getHostAddress() + ":" + inetSocketAddress.getPort() );
			
			//get IOStream
			OutputStream os = socket.getOutputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
			
			
			String request = null;
			
			while(true) {
				String line = br.readLine();
				
				//브라우저가 연결을 끊으면...
				if(line == null) {
					break;
				}
				
				// Request Header만 읽음
				if("".equals(line)) {//헤더부분이 끝나면 ( body부분이 빈개행으로 시작하니까)
					//bufferedReader가 알아서 개행이있으니 \r\n이 아니라 ""로 구함
					break; //지금은 바디내용이 없으니 break;
					//is body해서 위에 인스턴스변수 flag를 false에서 true로 바꿔주고 while문을 종료시키고
					//바디부분을 시작시키면됨
				}
				// Header의 첫번째 라인만 처리
				if(request==null) {
					request = line;
				}
			}
				//consoleLog("received : " + request);
				String[] tokens = request.split(" ");
				if("GET".contentEquals(tokens[0])) {
					consoleLog("Request:" + tokens[1]);
					System.out.println(tokens[0]);
					System.out.println(tokens[1]);
					System.out.println(tokens[2]);
					responseStaticResource(os,tokens[1],tokens[2]);
				}
				else { //POST, PUT, DELETE, HEAD, CONNECT 와 같은 METHOD는 무시
					//consoleLog("Bad Request:" + tokens[1]);
					
					//응답 예시
					//HTTP/1.1 400 Bad Request\r\n 
					// Content-Type:text/html; charset=utf-8\r\n
					// \r\n
					// HTML 에러 문서(./webapp/error/400.html)
					
					response400Error(os,tokens[2]);
					
					consoleLog("Bad Request:" + request);
				}
				//CRUD -> insert,select,update,delete
				//R 이 GET 임 // 서버에게 달라할때
				//C 이 POST 임
				//U 는 PUT 임 바디부분에 업데이트 보낼때
				//D 는  DELETE
			
			
			
			// 예제 응답입니다.
			// 서버 시작과 테스트를 마친 후, 주석 처리 합니다.
			//os.write( "HTTP/1.1 200 OK\r\n".getBytes( "UTF-8" ) );
			//os.write( "Content-Type:text/html; charset=utf-8\r\n".getBytes( "UTF-8" ) );
			//헤더와 바디부분을 나누는 거 \r\n 으로 구분
			//os.write( "\r\n".getBytes() );
			//os.write( "<h1>이 페이지가 잘 보이면 실습과제 SimpleHttpServer를 시작할 준비가 된 것입니다.</h1>".getBytes( "UTF-8" ) );

		} catch( Exception ex ) {
			consoleLog( "error:" + ex );
		} finally {
			// clean-up
			try{
				if( socket != null && socket.isClosed() == false ) {
					socket.close();
				}
				
			} catch( IOException ex ) {
				consoleLog( "error:" + ex );
			}
		}			
	}

	public void responseStaticResource(OutputStream os, String url, String protocol)throws IOException{
		
		if("/".equals(url))
			url ="/index.html";
		
		File file = new File(documentRoot + url);
		if(file.exists()==false) {
			//응답 예시
			//HTTP/1.1 404 File Not Found\r\n 
			// Content-Type:text/html; charset=utf-8\r\n
			// \r\n
			// HTML 에러 문서(./webapp/error/404.html)
			
			response404Error(os,protocol);
			return;
		}
		//nio  new io
		byte[] body = Files.readAllBytes(file.toPath());//내용을 읽어서 String으로 어떻게할게아니고 네트워크로 뿌려버릴거니까 byte로하면 편함 굳이 다르게 할 필요가 없음.
		String contentType = Files.probeContentType(file.toPath());
		
		//응답
		//os.write( "HTTP/1.1 200 OK\r\n".getBytes( "UTF-8" ) );
		os.write( (protocol + " 200 OK\r\n").getBytes( "UTF-8" ) );
		
		os.write( ("Content-Type:" + contentType + "; charset=utf-8\r\n").getBytes( "UTF-8" ) );
		//content -type 뒤에 타입을 mime 타입이라고 그런다
		
		os.write( "\r\n".getBytes() );
		
		//os.write( "<h1>이 페이지가 잘 보이면 실습과제 SimpleHttpServer를 시작할 준비가 된 것입니다.</h1>".getBytes( "UTF-8" ) );
		os.write(body);
		
	}
	
	public void response404Error(OutputStream os,String protocol)  throws IOException {
		
		String url = "./error/404.html";
		File file = new File(documentRoot + url);
		byte[] body = Files.readAllBytes(file.toPath());
		String contentType = Files.probeContentType(file.toPath());
		
		os.write( (protocol + " 404 filenotfound\r\n").getBytes( "UTF-8" ) );
		os.write( ("Content-Type:" + contentType + "; charset=utf-8\r\n").getBytes( "UTF-8" ) );
		os.write( "\r\n".getBytes() );
		os.write(body);
	}
	
	public void response400Error(OutputStream os,String protocol)  throws IOException {
		
		String url = "./error/400.html";
		File file = new File(documentRoot + url);
		byte[] body = Files.readAllBytes(file.toPath());
		String contentType = Files.probeContentType(file.toPath());
		
		os.write( (protocol + " 400 Bad Request\r\n").getBytes( "UTF-8" ) );
		os.write( ("Content-Type:" + contentType + "; charset=utf-8\r\n").getBytes( "UTF-8" ) );
		
		//개행 줌으로써 바디부분을 알려줌
		os.write( "\r\n".getBytes() );
		os.write(body);
	}
	
	public void consoleLog( String message ) {
		System.out.println( "[RequestHandler#" + getId() + "] " + message );
	}
}
