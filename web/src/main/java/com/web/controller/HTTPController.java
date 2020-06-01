package com.web.controller;

import com.commonutils.util.http.HttpClientPoolUtil;
import com.commonutils.util.json.JSONArray;
import com.commonutils.util.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HTTPController {
    private static Map<String,String> headMap = new HashMap<String,String>();

    static{
//        headMap.put("Content-Type", "application/json;charset=UTF-8");
//        headMap.put("Content-Type", "application/x-www-form-urlencoded");
    }

    @RequestMapping("/downTemplate")
    public void downTemplate(@RequestParam String name,HttpServletResponse response) throws Exception{
        String s = "http://119.96.194.83:8843/picture/mobanxiazai/"+URLEncoder.encode("房屋信息导入模板V1.0.xls", StandardCharsets.UTF_8.toString());
        System.out.println(s);
        response.sendRedirect(s);
    }
}

