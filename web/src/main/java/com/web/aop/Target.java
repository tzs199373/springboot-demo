package com.web.aop;

import org.springframework.stereotype.Component;

@Component
public class Target {
    public String targetMethod(String str){
        System.out.println("目标方法："+str);
        return str;
    }
}
