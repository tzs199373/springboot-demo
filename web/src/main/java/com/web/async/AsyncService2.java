package com.web.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@Component
public class AsyncService2 {
    @Async
    public Future<String> asyncSyso(String s) throws InterruptedException {
        Thread.sleep(5000);
        System.out.println(s);
        return new AsyncResult<>(s);
    }

    @Async
    public void asyncSyso2(String s) throws InterruptedException {
        Thread.sleep(5000);
        System.out.println(s);
    }
}
