package com.web.controller;

import com.commonutils.util.json.JSONObject;
import com.commonutils.util.security.SecurityCodeCreater;
import com.commonutils.util.string.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping("/login")
public class LoginController {

    /* 获取验证码 */
    @RequestMapping(params = "getSecurityImage")
    public void getSecurityImage(HttpServletResponse response, HttpServletRequest request) {
        HttpSession session = request.getSession();
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            String data = "1111";//实际情况是由业务层随机生成
            session.setAttribute("code", data);
            ImageIO.write(SecurityCodeCreater.getImage(data), "jpg", os);
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            if(os != null){
                try {
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
