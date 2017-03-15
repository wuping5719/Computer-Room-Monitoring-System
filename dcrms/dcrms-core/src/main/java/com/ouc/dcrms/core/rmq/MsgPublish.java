package com.ouc.dcrms.core.rmq;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

/**
 * @author WuPing
 * @version 2017年3月15日 下午10:19:50
 */

public class MsgPublish {

    private static final String EXCHANGE_NAME = "Logs";

    public static void main(String[] argv) throws Exception {
	ConnectionFactory factory = new ConnectionFactory();
	factory.setHost("localhost");
	Connection connection = factory.newConnection();
	Channel channel = connection.createChannel();

	channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

	String message = getMessage(argv);

	channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
	System.out.println(" [x] Send '" + message + "'");

	channel.close();
	connection.close();
    }

    private static String getMessage(String[] strings) {
	if (strings.length < 1)
	    return "info: Hello World!";
	return joinStrings(strings, " ");
    }

    private static String joinStrings(String[] strings, String delimiter) {
	int length = strings.length;
	if (length == 0)
	    return "";
	StringBuilder words = new StringBuilder(strings[0]);
	for (int i = 1; i < length; i++) {
	    words.append(delimiter).append(strings[i]);
	}
	return words.toString();
    }

}
