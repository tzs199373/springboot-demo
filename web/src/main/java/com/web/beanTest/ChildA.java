package com.web.beanTest;

import org.springframework.stereotype.Component;

@Component
public class ChildA extends Father {
    private int a= 2;
    @Override
    protected void print() {
        System.out.println("ChildA");
        System.out.println("==========================================="+this.a);
        System.out.println("==========================================="+this.getA());
    }
}
