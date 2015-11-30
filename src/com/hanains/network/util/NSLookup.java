package com.hanains.network.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NSLookup {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String hostname = scanner.nextLine();
		InetAddress[] inetAddress;
		while(true){
			if("exit".equals(hostname)){
				System.exit(0);
			}
			else{
				try {
					inetAddress = InetAddress.getAllByName(hostname);
					System.out.println(inetAddress);
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}	
		}
	}
}
