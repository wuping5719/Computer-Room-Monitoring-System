package com.ouc.dcrms.test;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ouc.dcrms.core.model.User;
import com.ouc.dcrms.core.service.UserServiceCore;

public class CoreProviderTest {

    @Autowired
    private static UserServiceCore userServiceCore;

    public static void main(String[] args) throws Exception {
	try {
	    @SuppressWarnings("resource")
	    ClassPathXmlApplicationContext cxt = new ClassPathXmlApplicationContext(
		    new String[] { "classpath:spring/spring.xml",
			    "classpath:spring/spring-mybatis.xml",
			    "classpath:spring/spring-druid.xml",
			    "classpath:spring/spring-rabbitmq.xml",
			    "classpath:spring/dubbo-core-provider.xml" });

	    cxt.start();

	    userServiceCore = (UserServiceCore) cxt.getBean("userServiceCore");
	    User user = userServiceCore.searchUserById(1);
	    System.out.println("用户:" + user.getName());

	    System.in.read(); // 模拟开启
	} catch (Exception e) {
	    e.printStackTrace();
	    System.out.println(e.getMessage());
	    Logger log = Logger.getLogger(CoreProviderTest.class);
	    log.error(e.getMessage()); // 将异常输出到文件
	}
    }
}
