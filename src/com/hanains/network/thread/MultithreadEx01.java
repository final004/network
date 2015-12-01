package com.hanains.network.thread;

public class MultithreadEx01 {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		Thread digitThread = new DigitThread();
		digitThread.start();
		
		for(char c='A'; c<='Z'; c++){
			System.out.print(c);
			Thread.sleep(1000);
		}
	}

}
