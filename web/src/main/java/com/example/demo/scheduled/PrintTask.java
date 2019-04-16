package com.example.demo.scheduled;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PrintTask {

    /**
     * 上一个调用开始后再次调用的延时，不用等待上次调用完成
     */
//    @Scheduled(fixedDelay = 3000*1,initialDelay=1000*10)
    public void fixedDelay() throws InterruptedException {
        Thread.sleep(2000);
        System.out.println("执行测试fixedRate时间："+new Date(System.currentTimeMillis()));
    }
}
