package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.util.HttpClientPoolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by asus on 2019/3/5.
 */
@RestController
public class HTTPController {
    private static Map<String,String> headMap = new HashMap<String,String>();

    static{
//        headMap.put("Content-Type", "application/json;charset=UTF-8");
//        headMap.put("Content-Type", "application/x-www-form-urlencoded");
    }

    @PostMapping("/httpTest")
    public String test(@RequestParam String name) throws Exception{
        String result = HttpClientPoolUtil.post("http://127.0.0.1:8900/testUrl","",headMap);
        return result;
    }
}

