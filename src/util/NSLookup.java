package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NSLookup {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String hostname=" ";
		BufferedReader br = null;
		Scanner sc1 = new Scanner(System.in);
		while(true)
		{
		System.out.print("> ");
		try {
			InputStreamReader is = new InputStreamReader(System.in,"utf-8");
			br = new BufferedReader(is);
			hostname = br.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if("quit".equals(hostname))
		{
			System.out.println("exit the program");
			break;
		}
		InetAddress[] inetAddresses;
		try {
			inetAddresses = InetAddress.getAllByName(hostname);

			for(InetAddress addr : inetAddresses)
			{
				System.out.println(addr.getHostAddress());
			}
		
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}


	}

}
