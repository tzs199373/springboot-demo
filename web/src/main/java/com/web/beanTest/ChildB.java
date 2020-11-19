package com.web.beanTest;

import org.springframework.stereotype.Component;

@Component
public class ChildB extends Father {
    @Override
    protected void print() {
        System.out.println("ChildB");
    }
}
