package com.hanains.network.echo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

	private static final int PORT = 5050;

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		Thread thread;

		try {
			// 서버 소켓 생성
			serverSocket = new ServerSocket();

			// 바인딩
			InetAddress inetAddress = InetAddress.getLocalHost();
			String localhost = inetAddress.getHostAddress();
			serverSocket.bind(new InetSocketAddress(localhost, PORT));
			System.out.println("[서버]바인딩 " + localhost + ":" + PORT);

			while (true) {
				// 연결 요청 대기(accept)
				Socket socket = serverSocket.accept();

				// 스레드
				thread = new EchoServerReceiveThread(socket);
				thread.start();

				// 연결 성공
				InetSocketAddress inetSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
				String remoteHostAddress = inetSocketAddress.getAddress().getHostAddress();
				int remoteHostPort = inetSocketAddress.getPort();
				System.out.println("[서버]연결됨 from " + remoteHostAddress + ":" + remoteHostPort);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 서버 소켓 닫기
			if (serverSocket != null && serverSocket.isClosed() == false) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}