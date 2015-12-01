package com.hanains.network.echo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {

	private static final String SERVER_IP = "192.168.1.10";
	private static final int SERVER_PORT = 5050;

	public static void main(String[] args) {

		Socket socket = null;
		InputStream inputStream = null;
		OutputStream outputStream = null;
		Scanner scanner;

		try {
			// 소켓 생성
			socket = new Socket();

			// 서버 연결
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
			System.out.println("[클라이언트]서버연결 성공");

			// IOStream 받아오기
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();

			// 데이터 쓰기/읽기
			byte[] buffer = new byte[256];
			while (true) {
				System.out.print(">> ");
				scanner = new Scanner(System.in);
				String str = scanner.nextLine();
				outputStream.write(str.getBytes("UTF-8"));

				int readByCount = inputStream.read(buffer);
				String data = new String(buffer, 0, readByCount);
				System.out.println(data);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("[클라이언트]에러:" + e);
			e.printStackTrace();
		} finally {
			try {
				// 자원정리
				if (inputStream != null) {
					inputStream.close();
				}
				if (outputStream != null) {
					outputStream.close();
				}
				if (socket != null && socket.isClosed() == false) {
					socket.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
