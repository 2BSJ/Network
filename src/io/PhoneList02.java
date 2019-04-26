package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PhoneList02 {
	public static void main(String[] args)
	{
		Scanner sc1 =null;
		File file = new File("phone.txt"); // 메타 데이터인 파일정보를 얻을때 쓰는 객체 파일 스트림이아님!!!
		try {
			sc1 = new Scanner(file);
			
			while(sc1.hasNextLine())
			{
				String name = sc1.next();
				String p1 = sc1.next();
				String p2= sc1.next();
				String p3 = sc1.next();
				
				System.out.println(name + " : " + p1 +"-" + p2 + "-" + p3);
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(sc1 != null)
			{
				sc1.close();
			}
				
		}
	}
}
