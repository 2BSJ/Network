package io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class PhoneList01 {

	public static void main(String[] args) {

		BufferedReader br = null;
		try {
			//기반스트림
			//보조스트림 1(bytes->char)
			
			br = new BufferedReader(new InputStreamReader(new FileInputStream("phone.txt"), "UTF-8"));
			String line = null;
			while((line = br.readLine()) !=null)
			{
				StringTokenizer st = new StringTokenizer(line, "\t ");
				
				int index = 0;
				while(st.hasMoreElements())
				{
					String token = st.nextToken();
					System.out.print(token);
					if(index ==0) //이름
					{
						System.out.print(":");
					}
					else if(index ==1) // 전화번호 1
					{
						System.out.print("-");
					}
					else if(index ==2) // 전화번호2
					{
						System.out.print("-");
					}
					
					index++;
				}
				System.out.println();
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(br !=null)
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
	}

}
