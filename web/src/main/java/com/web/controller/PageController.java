package com.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by asus on 2019/4/1.
 */
@Controller
public class PageController {
    @RequestMapping ("/web1")
    public String websocket(){
        return "websocket1";
    }

    @RequestMapping("/web2")
    public String websocket2(){
        return "websocket2";
    }

    @RequestMapping(value = "/login.htm")
    public String test(){
        return "login";
    }

    @RequestMapping(value = "/fileUpload")
    public String fileUpload(){
        return "fileUpload";
    }

    @RequestMapping(value = "/fileDown")
    public String fileDown(){
        return "fileDown";
    }

    @RequestMapping("/sse")
    public String ssepage() {
        return "sse";
    }

    @RequestMapping("/sse2")
    public String ssepage2() {
        return "sse2";
    }

    @RequestMapping("/count")
    public String countpage() {
        return "requestCount";
    }

}
