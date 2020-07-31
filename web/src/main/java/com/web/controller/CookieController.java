package com.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/cookie")
public class CookieController {
    @RequestMapping("/add")
    public String addCookie(HttpServletResponse response) throws Exception{
        Cookie cookie = new Cookie("cookiename","cookievalue");
        response.addCookie(cookie);
        return "success";
    }

    @RequestMapping("/get")
    public String getCookie(HttpServletRequest request) throws Exception{
        Cookie[] cookies = request.getCookies();//这样便可以获取一个cookie数组
        String s = "cookiename";
        for(Cookie cookie : cookies){
            if(cookie.getName().equals(s)){
                return  cookie.getValue();
            }
        }
        return "false";
    }
}
