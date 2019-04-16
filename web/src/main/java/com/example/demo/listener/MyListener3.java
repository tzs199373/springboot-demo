package com.example.demo.listener;

import org.springframework.context.ApplicationListener;

public class MyListener3 implements ApplicationListener<MyEvent> {
    public void onApplicationEvent(MyEvent event) {
        System.out.println(String.format("%s监听到事件源：%s.", MyListener3.class.getName(), event.getSource()));
    }
}

