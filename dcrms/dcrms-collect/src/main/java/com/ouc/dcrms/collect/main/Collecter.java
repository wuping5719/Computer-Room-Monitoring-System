package com.ouc.dcrms.collect.main;

import java.util.Date;
import java.util.Random;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ouc.dcrms.core.model.AlertRecord;
import com.ouc.dcrms.core.service.AlertServiceCore;

/**
 * @author WuPing
 * @version 2016年12月13日 上午11:30:28
 */

public class Collecter extends TimerTask implements Runnable {
    
    @Autowired
    private static AlertServiceCore alertServiceCore;
    
    //采集线程编号
    private int id;
    
    public Collecter(int id) {
	this.id = id;
	
	@SuppressWarnings("resource")
	ClassPathXmlApplicationContext cxt = new ClassPathXmlApplicationContext(
		new String[] { "classpath*:spring/spring.xml",
			"classpath*:spring/spring-mybatis.xml",
			"classpath*:spring/spring-druid.xml",
			"classpath*:spring/spring-rabbitmq.xml",
			"classpath*:spring/dubbo-core-provider.xml" });

	cxt.start();
	alertServiceCore = (AlertServiceCore) cxt.getBean("alertServiceCore");
	
	System.out.println("采集线程" + id + "开始运行！");
    }
    
    @SuppressWarnings("deprecation")
    @Override
    public void run() {
	Date now = new Date();
	
	synchronized (this) {
	    AlertRecord alertRecord = new AlertRecord();
	    alertRecord.setAlerttime(now);

	    Random random = new Random();
	    int insID = random.nextInt(100) + 1;
	    alertRecord.setInstrumentid(insID);
	    alertRecord.setSiteid(1);
	    int reasonID = random.nextInt(50) + 1;
	    alertRecord.setReasonid(reasonID);
	    alertRecord.setSolved(0);
	    alertServiceCore.insertAlertRecord(alertRecord);
	}
	
	System.out.println("采集线程"+ id +"运行时间为:" + now.toLocaleString());
    }
}
