package test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Localhost {

	public static void main(String[] args)  {
		// TODO Auto-generated method stub
		try {
			
			InetAddress inetAddress = InetAddress.getLocalHost();
			
			String hostname = inetAddress.getHostName();
			String hostAddress = inetAddress.getHostAddress();
			System.out.println(hostname + ":" + hostAddress);
			
			byte[] addresses = inetAddress.getAddress();
			for( byte address : addresses)
			{
				System.out.print(address & 0x000000ff);
				System.out.print('.');
			}
			
	//		InetAddress[] inetAddresses = InetAddress.getAllByName(hostname);
		//	for(InetAddress addr : inetAddresses)
			//{
			//	System.out.println(addr.getHostAddress());
			//}
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
