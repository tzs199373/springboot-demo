package com.web.scheduled;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PrintTask {

//    @Scheduled(fixedDelay = 3000*1,initialDelay=1000*10)
    public void fixedDelay() throws InterruptedException {
        Thread.sleep(2000);
        System.out.println("fixedDelay:"+new Date(System.currentTimeMillis()));
    }
}
