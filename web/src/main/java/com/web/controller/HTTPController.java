package com.web.controller;

import cn.yueshutong.springbootstartercurrentlimiting.annotation.CurrentLimiter;
import com.commonutils.util.http.HttpClientPoolUtil;
import com.commonutils.util.http.RemoteAddrUtil;
import com.commonutils.util.json.JSONArray;
import com.commonutils.util.json.JSONObject;
import com.web.async.AsyncService;
import com.web.model.Bill;
import com.web.service.ProtoTypeService;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

@Controller
public class HTTPController {
    @Autowired
    private AsyncService asyncService;
    @Autowired
    private ObjectFactory<ProtoTypeService> protoTypeServiceObjectFactory;

    @RequestMapping("/hello")
    @ResponseBody
    @CurrentLimiter(QPS = 10)
    public String hello( HttpServletResponse response) throws Exception{
        long time = System.currentTimeMillis();
        System.out.println(time);
        return "{\"time\":\""+time+"\"}";
    }

    @RequestMapping("/account")
    @ResponseBody
    public Bill account(@RequestBody Bill bill,String sign, HttpServletResponse response) throws Exception{
        System.out.println(sign);
        return bill;
    }

    @RequestMapping("/async")
    public String async(String msg,HttpServletResponse response) throws Exception{
        asyncService.asyncSyso(msg);
        return "SUCCESS";
    }

    @RequestMapping("/getRemoteAddr")
    @CurrentLimiter(QPS = 10)
    public String getRemoteAddr(String msg, HttpServletRequest request,HttpServletResponse response) throws Exception{
        String remoteAddr = RemoteAddrUtil.getRemoteAddr(request);
        System.out.println(remoteAddr);
        return remoteAddr;
    }

    @RequestMapping("/prototype")
    public String testprototype(String msg, HttpServletRequest request,HttpServletResponse response) throws Exception{
        String remoteAddr = request.getRemoteAddr();
        System.out.println(protoTypeServiceObjectFactory.getObject());
        protoTypeServiceObjectFactory.getObject().hello();
        return remoteAddr;
    }

    @RequestMapping(value = "/update")
    public String updateAttr( Map<String, String> map) {
        System.out.println(map.get("a"));
        return "success";
    }

    @RequestMapping(value = "/test")
    public String test(HttpServletRequest request) throws IOException {
        BufferedReader br = request.getReader();
        String str, wholeStr = "";
        while((str = br.readLine()) != null){
            wholeStr += str;
        }
        System.out.println(wholeStr);
        return wholeStr;
    }
}

