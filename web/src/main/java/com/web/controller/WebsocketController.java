package com.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by asus on 2019/4/1.
 */
@Controller
public class WebsocketController {
    @RequestMapping ("/web1")
    public String websocket(){
        return "websocket1";
    }

    @RequestMapping("/web2")
    public String websocket2(){
        return "websocket2";
    }

    @RequestMapping(value = "/login")
    public String test(){
        return "index";
    }

//    @Bean
//    public InternalResourceViewResolver viewResolver() {
//        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//        viewResolver.setPrefix("/WEB-INF/view/");
//        viewResolver.setSuffix(".jsp");
//        return viewResolver;
//    }
}
