package com.hanains.network.echo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class EchoServerReceiveThread extends Thread {

	private Socket socket;

	public EchoServerReceiveThread(Socket socket) {
		// TODO Auto-generated constructor stub
		this.socket = socket;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		// IOStream 받아오기
		try {
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();

			// 데이터 읽고 쓰기
			try {
				byte[] buffer = new byte[256];
				while (true) {
					int readByteCount = inputStream.read(buffer);
					if (readByteCount < 0) {
						System.out.println("[서버]클라이언트 " + socket.getPort() + "로부터 연결 끊김");
						break;
					}
					String data = new String(buffer, 0, readByteCount);
					System.out.println("[서버]클라이언트 " + socket.getPort() + "로부터 수신한 데이터:" + data);

					// 7.데이터 보내기
					outputStream.write(data.getBytes("UTF-8"));
					outputStream.flush();
				}
			} catch (IOException e) {
				System.out.println("[서버]클라이언트 " + socket.getPort() + "에러:" + e);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
