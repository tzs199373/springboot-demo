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

    @RequestMapping("/httpTest")
    public void httpTest(@RequestParam String name,HttpServletResponse response) throws Exception{
        String s = "http://119.96.194.83:8843/picture/mobanxiazai/"+URLEncoder.encode("房屋信息导入模板V1.0.xls", StandardCharsets.UTF_8.toString());
        System.out.println(s);
        response.sendRedirect(s);
    }

    @RequestMapping("/httpTest2")
    public String httpTest2(@RequestParam String name) throws Exception{
        String result = name;
        return result;
    }

    @RequestMapping("/qryArticleList")
    public String qryArticleList(@RequestParam Integer currentPage,
                                 @RequestParam Integer pageSize) throws Exception{
        System.out.println("currentPage:"+currentPage);
        //模仿所有数据
        JSONArray totalArray = new JSONArray();
        for (int i = 0; i < 21; i++) {
            JSONObject item = new JSONObject();
            item.element("date","2019-02-15 10:31:30");
            item.element("name","tzs");
            item.element("email","566236587@qq.com");
            item.element("address","China");
            totalArray.add(item);
        }
        int totalCount = totalArray.size();
        int totalPage = (int) Math.ceil(totalCount/pageSize);

        //模仿取分页数据
        JSONArray rstArray = new JSONArray();
        int start = (currentPage-1)*pageSize;
        int end = currentPage*pageSize;
        for (int i = start; i < end; i++) {
            if(i < totalArray.size()){
                rstArray.add(totalArray.getJSONObject(i));
            }
        }

        //组合返回值
        JSONObject rst = new JSONObject();
        JSONObject data = new JSONObject();
        data.element("totalPage",totalPage);
        data.element("totalCount",totalCount);
        data.element("dataList",rstArray);
        rst.element("data",data);
        rst.element("msg","success");
        rst.element("code","200");
        return rst.toString();
    }
}

