package com.engine.service;

import com.engine.component.MyComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyService {
    @Autowired
    MyComponent myComponent;

    public void printPropertiesValue(){
        System.out.println(myComponent.getUsername());
    }

}
