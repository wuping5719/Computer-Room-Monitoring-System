package com.ouc.dcrms.core.rmq;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

/**
 * @author WuPing
 * @version 2017年3月15日 下午10:22:42
 */

public class MsgSubscribe {
    private static final String EXCHANGE_NAME = "Logs";

    public static void main(String[] argv) throws Exception {
	ConnectionFactory factory = new ConnectionFactory();
	factory.setHost("localhost");
	Connection connection = factory.newConnection();
	Channel channel = connection.createChannel();

	channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
	String queueName = channel.queueDeclare().getQueue();
	channel.queueBind(queueName, EXCHANGE_NAME, "");

	System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

	QueueingConsumer consumer = new QueueingConsumer(channel);
	channel.basicConsume(queueName, true, consumer);

	while (true) {
	    QueueingConsumer.Delivery delivery = consumer.nextDelivery();
	    String message = new String(delivery.getBody());

	    System.out.println(" [x] Received '" + message + "'");
	}
    }
}
