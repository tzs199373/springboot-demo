package com.example.demo;

import com.example.demo.listener.MyEvent;
import com.example.demo.listener.MyListener1;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ServletComponentScan(basePackages = "com.example.demo.servlet")
@EnableScheduling
public class DemoApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
		context.addApplicationListener(new MyListener1());
		context.publishEvent(new MyEvent("TestEvent."));
	}
}
