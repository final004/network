package com.hanains.network.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

public class RequestHandler extends Thread {

	private Socket socket;

	public RequestHandler(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {

		BufferedReader bufferedReader = null;
		OutputStream outputStream = null;

		try {
			// get IOStream
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outputStream = socket.getOutputStream();

			// logging Remote Host IP Address & Port
			InetSocketAddress inetSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
			SimpleHttpServer
					.consolLog("connected from " + inetSocketAddress.getHostName() + ":" + inetSocketAddress.getPort());

			// 예제 응답입니다.
			// 서버 시작과 테스트를 마친 후, 주석 처리 합니다.
			// outputStream.write( "HTTP/1.1 200 OK\r\n".getBytes( "UTF-8" ) );
			// outputStream.write( "Content-Type:text/html;
			// charset=UTF-8\r\n".getBytes( "UTF-8" ) );
			// outputStream.write( "\r\n".getBytes() );
			// outputStream.write( "<h1>이 페이지가 잘 보이면 실습과제 SimpleHttpServer를 시작할
			// 준비가 된 것입니다.</h1>".getBytes( "UTF-8" ) );

			String request = "";
			SimpleHttpServer.consolLog("=============== request information ==============");
			while (true) {
				String line = bufferedReader.readLine();
				if (line == null || "".equals(line)) {
					break;
				}
				if ("".equals(request)) {
					request = line;
				}
				SimpleHttpServer.consolLog(line);
			}
			SimpleHttpServer.consolLog("==================================================");

			// 요청처리
			String[] tokens = request.split("");
			if("GET".equals(tokens[0])){
				responseStaticResource(outputStream, tokens[1], tokens[2]);
			}else{
				response400Error(outputStream, tokens[2]);
			}
			
		} catch (Exception ex) {
			SimpleHttpServer.consolLog("error:" + ex);
		} finally {
			// clean-up
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}

				if (outputStream != null) {
					outputStream.close();
				}

				if (socket != null && socket.isClosed() == false) {
					socket.close();
				}

			} catch (IOException ex) {
				SimpleHttpServer.consolLog("error:" + ex);
			}
		}

	}
	private void responseStaticResource(OutputStream outputStream, String url, String protocol) throws IOException {
		
		// default html 처리
		
		// File 객체 생성
		File file = new File( "./webapp" + url );
		
		// File 존재 여부 체크
		if(file.exists() == false){
			response404Error(outputStream, protocol);
			return;
		}
		
		if("/".equals(url)){
			url = "/index.html";
		}
		
		Path path = file.toPath();
		byte[] body = Files.readAllBytes( path ); 
		
		outputStream.write( "HTTP/1.1 200 OK\r\n".getBytes( "UTF-8" ));
		outputStream.write( "Content-Type:image/html\r\n".getBytes( "UTF-8" ) );
		outputStream.write( "\r\n".getBytes() );
		outputStream.write( body );	
	}
	private void response400Error(OutputStream outputStream, String error){
		
	}
	private void response404Error(OutputStream outputStream, String protocol){
		
	}
}