package com.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/session")
public class SessionController {
    @RequestMapping("/addAttribute")
    public String addSession(HttpServletRequest request,HttpServletResponse response) throws Exception{
        HttpSession session = request.getSession();
        session.setAttribute("name","value");
        return "success";
    }

    @RequestMapping("/getAttribute")
    public String getSession(HttpServletRequest request,HttpServletResponse response) throws Exception{
        HttpSession session = request.getSession();
        System.out.println(session.getId());
        return (String)session.getAttribute("name");
    }
}
