package com.web.filter;

import com.web.statistics.Indicator;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CountingFilter implements Filter{
    final Indicator indicator = Indicator.getINSTANCE();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("requestConut£º"+indicator.newRequestReceived());
        filterChain.doFilter(servletRequest,servletResponse);
        int statusCode = ((HttpServletResponse) servletResponse).getStatus();
        if(0 == statusCode || 2 == statusCode/100){
            System.out.println("successConut£º"+indicator.newRequestProcessed());
        }else{
            System.out.println("failureConut£º"+indicator.requestProcessedFailed());
        }
    }

    @Override
    public void destroy() {

    }
}


