package io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class KeyboardTest { // Scanner의 원리도 비슷

	public static void main(String[] args) {
		//기반스트림(표준입력, 키보드, System.in)

		//보조스트림1 (필요한이유 기반스트림을 통해 들어온 byte값이 불편하니까 이걸
		//byte|byte|byte -> char
		//byte[] data = {56,17,32} --안
		//String s = new String ( data,3,"utf-8");
		//위의 과정을 밑에 클래스에서 알아서 해줌
		BufferedReader br = null;
		try {
			InputStreamReader isr = 
					new InputStreamReader(System.in,"utf-8");

			//보조스트림2
			//char1|char2|char3|\n -> " char1char2char3" 
			//개행을 만나면 캐릭터를 String형으로 변환하여 반환
			br = new BufferedReader(isr);

			//read
			String line = null;
			while((line = br.readLine()) != null)
			{
				if("exit".contentEquals(line))
				{
					break;
				}
				System.out.println(">>" + line);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				if(br!=null)
					br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


	}

}
