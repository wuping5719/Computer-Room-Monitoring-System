package com.ouc.dcrms.main;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author WuPing
 * @version 2016年12月22日 下午11:17:16
 */

public class Main {
    
    private static Timer timer = new Timer();
    
    static public class Task extends TimerTask {  
	@SuppressWarnings("deprecation")
	@Override
	public void run() {
	    System.out.println("采集线程运行时间为" + new Date().toLocaleString());
	}
    }
    
    public static void main(String[] args) {
	Collecter collecter1 = new Collecter(1);
	Collecter collecter2 = new Collecter(2);
	
	Thread thread1 = new Thread(collecter1);
	thread1.start();
	
	Thread thread2 = new Thread(collecter2);
	thread2.start();
	
	timer.schedule(collecter1, new Date(), 1000*5);
	timer.schedule(collecter2, new Date(), 1000*10);
    }
}
