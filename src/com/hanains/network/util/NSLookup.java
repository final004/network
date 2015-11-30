package com.hanains.network.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NSLookup {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String hostname;
		InetAddress[] inetAddress;
		while(true){
			System.out.print("> ");
			hostname = scanner.nextLine();
			if("exit".equals(hostname)){
				System.exit(0);
			}
			else{
				try {
					inetAddress = InetAddress.getAllByName(hostname);
					for(int i=0; i<inetAddress.length; i++){
						System.out.println(inetAddress[i].getHostName() + " : " + inetAddress[i].getHostAddress());
					}
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}	
		}
	}
}
