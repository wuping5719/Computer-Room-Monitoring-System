package com.ouc.dcrms.test;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ouc.dcrms.core.rmq.MessageProducer;

/**
 * @author WuPing
 * @version 2016年11月28日 下午10:06:24
 */

public class MessageTest {
    @Autowired
    private static MessageProducer mProducer;

    private final static String queue_key = "queueTestKey";

    public static void main(String[] args) throws Exception {
	try {
	    @SuppressWarnings("resource")
	    ClassPathXmlApplicationContext cxt = new ClassPathXmlApplicationContext(
		    new String[] { "classpath:spring/spring.xml",
			    "classpath:spring/spring-rabbitmq.xml" });

	    cxt.start();

	    mProducer = (MessageProducer) cxt.getBean("messageProducer");
	    
	    Map<String,Object> msg = new HashMap<>();
	    msg.put("data", "Hello, RabbmitMQ!");
	    //mProducer.sendDataToQueue(queue_key, msg);
	    
	    String msg2 = "1: Hello, RabbmitMQ!";
	    mProducer.sendDataToQueue(queue_key, msg2);
	    
            // 暂停一下，好让消息消费者去取消息打印出来
	    Thread.sleep(1000);
	} catch (Exception e) {
	    e.printStackTrace();
	    System.out.println(e.getMessage());
	    Logger log = Logger.getLogger(MessageTest.class);
	    log.error(e.getMessage()); // 将异常输出到文件
	}
    }
}
