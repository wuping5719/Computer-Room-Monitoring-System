package com.ouc.dcrm.system.test;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ouc.dcrm.system.dto.UserDTO;
import com.ouc.dcrm.system.service.UserServiceClient;

public class UsersProvider {

    @Autowired
    private static UserServiceClient userServiceClient;

    public static void main(String[] args) throws Exception {
	try {
	    @SuppressWarnings("resource")
	    ClassPathXmlApplicationContext cxt = new ClassPathXmlApplicationContext(
		    new String[] { "classpath:spring/spring.xml",
			    "classpath:spring/spring-mybatis.xml",
			    "classpath:spring/dubbo-provider.xml" });

	    cxt.start();

	    userServiceClient = (UserServiceClient) cxt.getBean("userServiceClient");
	    UserDTO userDTO = userServiceClient.getUserById(1);
	    System.out.println("用户:" + userDTO.getName());

	    System.in.read(); // 模拟开启
	} catch (Exception e) {
	    e.printStackTrace();
	    System.out.println(e.getMessage());
	    Logger log = Logger.getLogger(UsersProvider.class);
	    log.error(e.getMessage()); // 将异常输出到文件
	}

    }
}
