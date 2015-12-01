package com.hanains.network.thread;

public class MultithreadEx03 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Thread thread = new Thread(new DigitRunnableImpl());
		thread.start();
		
	}

}
