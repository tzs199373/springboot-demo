package com.web.controller;

import com.commonutils.util.http.HttpClientPoolUtil;
import com.commonutils.util.json.JSONArray;
import com.commonutils.util.json.JSONObject;
import com.web.async.AsyncService;
import com.web.model.Bill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

@RestController
public class HTTPController {
    private static Map<String,String> headMap = new HashMap<String,String>();

    @Autowired
    private AsyncService asyncService;

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

    @RequestMapping("/account")
    public Bill account(Bill bill,String sign, HttpServletResponse response) throws Exception{
        System.out.println(sign);
        return bill;
    }

    @RequestMapping("/async")
    public String async(String msg,HttpServletResponse response) throws Exception{
        asyncService.asyncSyso(msg);
        return "SUCCESS";
    }

    @RequestMapping("/getRemoteAddr")
    public String getRemoteAddr(String msg, HttpServletRequest request,HttpServletResponse response) throws Exception{
        String remoteAddr = request.getRemoteAddr();
        System.out.println(remoteAddr);
        return remoteAddr;
    }


}

