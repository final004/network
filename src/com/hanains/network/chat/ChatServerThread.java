package com.hanains.network.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ChatServerThread extends Thread {

	private String nickname;
	private Socket socket;
	private List<Writer> listWriters;

	BufferedReader bufferedReader;
	PrintWriter printWriter;
	
	public ChatServerThread(Socket socket, List<Writer> listWriters) {
		// TODO Auto-generated constructor stub
		this.socket = socket;
		this.listWriters = listWriters;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
//		InetSocketAddress inetSocketAddress = (InetSocketAddress)socket.getRemoteSocketAddress();
//		String remoteHostAddress = inetSocketAddress.getAddress().getHostAddress();
//		int remoteHostPort = inetSocketAddress.getPort();
//		ChatServer.consoleLog("연결됨 from " + remoteHostAddress + ":" + remoteHostPort);
		
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(true){
			
			try {
				String data = bufferedReader.readLine();
				if(data == null){
					ChatServer.consoleLog("클라이언트 " + socket.getPort() + "로부터 연결 끊김");
					doQuit(printWriter);
					break;
				}
				System.out.println(data);
				
				String[] tokens = data.split(":");
				if("join".equals(tokens[0])){
					doJoin(tokens[1], printWriter);
				}else if("message".equals(tokens[0])){
					doMessage(tokens[1]);
				}else if("quit".equals(tokens[0])){
					doQuit(printWriter);
				}else{
					consoleLog("에러:알수 없는 요청(" + tokens[0] + ")");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}
	
	private void doJoin(String nickName, PrintWriter printWriter) {
		// TODO Auto-generated method stub
		this.nickname = nickName;
		
		String data = nickName + "님이 참여하였습니다.";
		broadcast("message:" + data);
		addWriter(printWriter);
	}
	private void doMessage(String message) {
		// TODO Auto-generated method stub
		broadcast("message:" + message);
	}
	private void doQuit(PrintWriter printWriter) {
		// TODO Auto-generated method stub
		removeWriter(printWriter);
		String data = nickname + "님이 퇴장하였습니다.";
		broadcast("quit:" + data);
//		printWriter.println("quit:" + data);
	}

	private void addWriter(PrintWriter printWriter) {
		// TODO Auto-generated method stub
		synchronized (listWriters) {
			listWriters.add(printWriter);
		}
	}
	
	private void removeWriter(PrintWriter printWriter) {
		// TODO Auto-generated method stub
		synchronized (listWriters) {
			listWriters.remove(printWriter);
		}
	}

	private void broadcast(String data) {
		// TODO Auto-generated method stub
		synchronized (listWriters) {
			for(Writer writer : listWriters){
				PrintWriter printWriter = (PrintWriter) writer;
				printWriter.println(data);
				printWriter.flush();
			}
		}
	}

	public void consoleLog(String message) {
		System.out.println(nickname + ">> " + message);
	}
}
