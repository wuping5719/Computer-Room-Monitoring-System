package com.ouc.dcrms.task;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TaskStartUtil {

    public void testStartTask() {
	@SuppressWarnings("resource")
	ClassPathXmlApplicationContext taskContext = new ClassPathXmlApplicationContext(
		"classpath*:/spring/spring.xml",
		"classpath*:/spring/spring-mybatis.xml",
		"classpath*:/spring/spring-druid.xml",
		"classpath*:/spring/spring-rabbitmq.xml",
		"classpath*:/spring/dubbo-core-provider.xml",
		"classpath*:/spring-task/spring-lts-*.xml");
	taskContext.start();
	while (true) {}
    }
}
