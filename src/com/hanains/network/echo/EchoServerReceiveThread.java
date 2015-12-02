package com.hanains.network.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class EchoServerReceiveThread extends Thread {

	private Socket socket;
	BufferedReader bufferedReader = null;
	PrintWriter printWriter = null;

	public EchoServerReceiveThread(Socket socket) {
		// TODO Auto-generated constructor stub
		this.socket = socket;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		// IOStream 받아오기
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			// outputStream = socket.getOutputStream();
			printWriter = new PrintWriter(socket.getOutputStream());

			// 데이터 읽고 쓰기
			try {

				// char[] buffer = new char[256];
				while (true) {
					String data = bufferedReader.readLine();
					if (data == null) {
						break;
					}

					// int readCharCount = bufferedReader.read(buffer);
					// if (readCharCount < 0) {
					// System.out.println("[서버]클라이언트 " + socket.getPort() + "로부터
					// 연결 끊김");
					// break;
					// }
					// String data = new String(buffer, 0, readCharCount);
					System.out.println("[서버]클라이언트 " + socket.getPort() + "로부터 수신한 데이터:" + data);

					// 7.데이터 보내기
					// outputStream.write(data.getBytes("UTF-8"));
					// outputStream.flush();
					printWriter.print(data);
					printWriter.flush();
				}
			} catch (IOException e) {
				System.out.println("[서버]클라이언트 " + socket.getPort() + "에러:" + e);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
