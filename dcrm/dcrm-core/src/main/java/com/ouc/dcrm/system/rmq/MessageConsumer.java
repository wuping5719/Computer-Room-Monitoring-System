package com.ouc.dcrm.system.rmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * @author WuPing
 * @version 2016年11月28日 下午10:00:24
 */

public class MessageConsumer implements MessageListener {

    @Override
    public void onMessage(Message message) {
	try{
	    System.out.println(message.toString());
	    System.out.println(message.getMessageProperties().getAppId());
	    
	    String receiveMsg = null;
	    receiveMsg = new String(message.getBody(),"UTF-8");
	    System.out.println(receiveMsg);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
