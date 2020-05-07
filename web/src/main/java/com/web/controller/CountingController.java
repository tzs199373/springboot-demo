package com.web.controller;

import com.web.statistics.Indicator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tzs
 * @version 1.0
 * @Description
 * @since 2020/5/7 10:35
 */
@RestController
@RequestMapping("/counting")
public class CountingController {
    @RequestMapping ("/flushRequestCount")
    public String flushRequestCount(){
        Indicator.getINSTANCE().reset();
        return "success";
    }

}
