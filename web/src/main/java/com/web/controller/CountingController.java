package com.web.controller;

import com.web.statistics.Indicator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/counting")
public class CountingController {
    @RequestMapping ("/flushRequestCount")
    public String flushRequestCount(){
        Indicator.getINSTANCE().reset();
        return "success";
    }

}
