package com.web.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class MyListener2 implements ApplicationListener<MyEvent> {
    public void onApplicationEvent(MyEvent event) {
        System.out.println(String.format("%s????????????%s.", MyListener2.class.getName(), event.getSource()));
    }
}

