package com.ouc.dcrms.core.rmq;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

/**
 * @author WuPing
 * @version 2017年3月15日 下午9:51:43
 */

public class MsgReceive {

    private final static String QUEUE_NAME = "Hello";

    public static void main(String[] argv) throws Exception {
	ConnectionFactory factory = new ConnectionFactory();
	factory.setHost("localhost");
	Connection connection = factory.newConnection();
	Channel channel = connection.createChannel();

	channel.queueDeclare(QUEUE_NAME, false, false, false, null);
	System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

	QueueingConsumer consumer = new QueueingConsumer(channel);
	channel.basicConsume(QUEUE_NAME, true, consumer);

	while (true) {
	    QueueingConsumer.Delivery delivery = consumer.nextDelivery();
	    String message = new String(delivery.getBody());
	    System.out.println(" [x] Received '" + message + "'");
	}
    }

}
