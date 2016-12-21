package com.ouc.dcrms.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ouc.dcrms.client.dto.UserDTO;
import com.ouc.dcrms.client.service.UserServiceClient;

public class ConsumerTest {

    @Autowired
    private static UserServiceClient userServiceClient;

    public static void main(String[] args) {

	@SuppressWarnings("resource")
	ClassPathXmlApplicationContext cxt = new ClassPathXmlApplicationContext(
		new String[] { "classpath:spring/dubbo-consumer.xml"}, true);

	cxt.start();

	userServiceClient = (UserServiceClient) cxt.getBean("userServiceClient");
	UserDTO userDTO = userServiceClient.getUserById(1);
	System.out.println("用户:" + userDTO.getName());
	
    }
}
