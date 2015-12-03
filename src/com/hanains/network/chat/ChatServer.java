package com.hanains.network.chat;

import java.io.IOException;
import java.io.Writer;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {

	private static final int PORT = 8080;

	public static void main(String[] args) {

		ServerSocket serverSocket = null;
		List<Writer> listWriters = new ArrayList<Writer>();
		try {
			serverSocket = new ServerSocket();

			String localhost = InetAddress.getLocalHost().getHostAddress();
			serverSocket.bind(new InetSocketAddress(localhost, PORT));
			consoleLog("연결기다림 " + localhost + ":" + PORT);

			while (true) {
				Socket socket = serverSocket.accept();
				Thread thread = new ChatServerThread(socket, listWriters);
				thread.start();
			}
		} catch (IOException ex) {
			consoleLog("Server error:" + ex);
		} finally {
			try {
				if (serverSocket != null && serverSocket.isClosed() == false) {
					serverSocket.close();
				}
			} catch (IOException ex) {
				consoleLog("error:" + ex);
			}
		}
	}

	public static void consoleLog(String message) {
		System.out.println("[Server] " + message);
	}
}
