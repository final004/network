package com.hanains.network.chat;

import java.io.BufferedReader;
import java.io.IOException;

public class ChatClientReceiveThread extends Thread {

	private BufferedReader bufferedReader;

	public ChatClientReceiveThread(BufferedReader bufferedReader) {
		// TODO Auto-generated constructor stub
		this.bufferedReader = bufferedReader;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String data;

		try {
			while (true) {
				data = bufferedReader.readLine();
				String[] tokens = data.split(":");
				if ("quit".equals(tokens[0])) {
					System.out.println(tokens[1]);
					break;
				} else if ("message".equals(tokens[0])) {
					System.out.println(" <<" + tokens[1]);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally {
//			try {
//				if (bufferedReader != null) {
//					bufferedReader.close();
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
		}
	}
}
