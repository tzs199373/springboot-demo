package com.web;

import com.web.aop.*;
import com.web.listener.MyEvent;
import com.web.listener.MyListener1;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
//@EnableAspectJAutoProxy(proxyTargetClass=true)
//@ServletComponentScan(basePackages = "com.web.servlet")
//@EnableScheduling
public class WebApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(WebApplication.class, args);
//		context.addApplicationListener(new MyListener1());
//		context.publishEvent(new MyEvent("TestEvent."));
		Target target =  context.getBean(Target.class);
		target.targetMethod("hello");
	}
}
