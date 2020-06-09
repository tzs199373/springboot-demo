package com.web.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;


@Component
public class AsyncService {
    @Autowired
    private AsyncService2 asyncService2;

//    @Async
    public void asyncSyso(String s) throws InterruptedException {
        asyncService2.asyncSyso2(s);
        System.out.println("555555555555");
    }
}
