package com.web.service;

import com.web.controller.HTTPController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class ProtoTypeService {

//    @Autowired
//    private HTTPController httpController;
    public void hello(){
        System.out.println("hello world");
    }
}
