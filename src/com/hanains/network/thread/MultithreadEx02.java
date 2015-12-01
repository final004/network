package com.hanains.network.thread;

import java.util.ArrayList;
import java.util.List;

public class MultithreadEx02 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		List list = new ArrayList();
		
		Thread thread1 = new DigitThread(list);
		Thread thread2 = new LowerCaseAlphabetThread(list);
		Thread thread3 = new DigitThread(list);
		
		thread1.start();
		thread2.start();
		thread3.start();
	}
}
