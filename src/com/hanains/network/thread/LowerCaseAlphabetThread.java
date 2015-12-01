package com.hanains.network.thread;

import java.util.ArrayList;
import java.util.List;

public class LowerCaseAlphabetThread extends Thread {
	private List list = new ArrayList();
	
	public LowerCaseAlphabetThread(){
		
	}
	
	public LowerCaseAlphabetThread(List list) {
		// TODO Auto-generated constructor stub
		this.list = list;
	}

	@Override
	public void run() {
		for (char c = 'a'; c <= 'z'; c++) {
			System.out.print(c);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
