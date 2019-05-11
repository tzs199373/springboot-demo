package com.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Ȩ����֤������
 */
public class AuthFilter implements Filter {

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)arg0;
        HttpServletResponse response = (HttpServletResponse)arg1;
        System.out.println("doing");
        if(needLogin(request)) {
            response.sendRedirect("login");
            return;
        }
        chain.doFilter(arg0, arg1);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    /**
     * �ж��Ƿ���Ҫ��¼
     * @param request
     * @return
     */
    private boolean needLogin(HttpServletRequest request) {
        return false;
    }
}
