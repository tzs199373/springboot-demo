package com.web.controller;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.io.IOException;

public class SSEThread implements Runnable {
    private SseEmitter sseEmitter;
    private int times = 0;//??????????

    public SSEThread(SseEmitter sseEmitter) {
        this.sseEmitter = sseEmitter;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("???times=" + times);
                sseEmitter.send(System.currentTimeMillis());
                times++;
                Thread.sleep(1000);
                if (times > 4) {
                    System.out.println("????finish???");
                    sseEmitter.send(SseEmitter.event().name("finish").id("6666").data("????????????"));
                    System.out.println("????complete");
                    sseEmitter.complete();
                    System.out.println("complete??times=" + times);
                    break;
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
