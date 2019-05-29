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

    /* ��ȡ��֤�� */
    @RequestMapping("getSecurityImage")
    public void getSecurityImage(HttpServletResponse response, HttpServletRequest request) {
        HttpSession session = request.getSession();
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            String data = SecurityCodeCreater.getRandString();//ʵ���������ҵ����������,�����������Ա��û���¼�ȶ�
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