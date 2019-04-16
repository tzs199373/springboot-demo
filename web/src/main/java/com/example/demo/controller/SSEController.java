package com.example.demo.controller;

import java.util.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class SSEController {
    private Map<String, Map<String, Object>> pushMap = new Hashtable<>();

    @RequestMapping(value = "/push", produces = "text/event-stream;charset=UTF-8")
    @ResponseBody
    public String sse() {
        Random r = new Random();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "data:" + r.nextInt(1000) + "?" + "\n\n";
    }

    @RequestMapping("/sseEmitter")
    @ResponseBody
    public SseEmitter sseEmitterCall() {
        SseEmitter sseEmitter = new SseEmitter();
        Thread t = new Thread(new SSEThread(sseEmitter));
        t.start();
        return sseEmitter;
    }

    @RequestMapping(value = "/alertPush.do")
    public SseEmitter alertPush(HttpServletRequest request) {
        final SseEmitter sseEmitter = new SseEmitter(3600000L);
        HttpSession session = request.getSession();
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("sseEmitter", sseEmitter);
            pushMap.put(session.getId(), paramMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sseEmitter;
    }


    @RequestMapping("/sse")
    public String ssepage() {
        return "sse";
    }

    @RequestMapping("/sse2")
    public String ssepage2() {
        return "sse2";
    }


}
