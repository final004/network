package com.hanains.network.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ChatClient {

	private static final String SERVER_IP = "192.168.1.10";
	private static final int SERVER_PORT = 8080;
	private static String nickname;
	private static String data;

	private static PrintWriter printWriter;
	private static BufferedReader bufferedReader;

	public static void main(String[] args) {

		Socket socket = null;
		Scanner scanner = null;

		try {
			socket = new Socket();
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
			System.out.println("[클라이언트]서버연결 성공");

			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8),true);

			System.out.print("닉네임>> ");
			scanner = new Scanner(System.in);
			nickname = scanner.nextLine();
			printWriter.println("join:" + nickname);
			printWriter.flush();

			Thread thread = new ChatClientReceiveThread(bufferedReader);
			thread.start();

			while (true) {
//				System.out.println(nickname + ">> ");
				String input = scanner.nextLine();
				if ("quit".equals(input)) {
					doQuit();
					break;
				} else {
					doMessage(input);
				}

//				printWriter.println(input);
//				printWriter.flush();
//				data = bufferedReader.readLine();
//				System.out.println("<- " + data);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			consoleLog("Client " + socket.getPort() + "error:" + e);
		} finally {
			try {
//				if (bufferedReader != null) {
//					bufferedReader.close();
//				}
//				if (printWriter != null) {
//					printWriter.close();
//				}
				if (socket != null && socket.isClosed() == false) {
					socket.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static void doQuit() {
		// TODO Auto-generated method stub
		String data = nickname + "님이 퇴장하였습니다.";
		printWriter.println("quit:" + data);
		printWriter.flush();
	}
	private static void doMessage(String input) {
		// TODO Auto-generated method stub
		printWriter.println("message:" + input);
		printWriter.flush();
	}
	
	public static void consoleLog(String message) {
		System.out.println("[Client] " + message);
	}
}
