package com.ouc.dcrms.core.rmq;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

/**
 * @author WuPing
 * @version 2017年3月15日 下午9:49:06
 */

// RabbitMQ的几种典型使用场景: http://www.cnblogs.com/luxiaoxun/p/3918054.html

public class MsgSend {

    private final static String QUEUE_NAME = "Hello";

    public static void main(String[] argv) throws Exception {
	ConnectionFactory factory = new ConnectionFactory();
	factory.setHost("localhost");
	Connection connection = factory.newConnection();
	Channel channel = connection.createChannel();

	channel.queueDeclare(QUEUE_NAME, false, false, false, null);
	String message = "Hello World!";
	channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
	System.out.println(" [x] Send '" + message + "'");

	channel.close();
	connection.close();
    }
}
