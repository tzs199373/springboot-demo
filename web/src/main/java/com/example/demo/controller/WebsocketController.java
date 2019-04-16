package com.example.demo.controller;

import com.example.demo.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

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
