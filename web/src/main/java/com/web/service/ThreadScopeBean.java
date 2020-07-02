package com.web.service;

import com.web.config.threadScope.ThreadScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ThreadScope.SCOPE_THREAD)
@DependsOn("customScopeConfigurer")
public class ThreadScopeBean{
    public ThreadScopeBean(@Value(ThreadScope.SCOPE_THREAD) String  beanScope)  {
        System.out.println(String.format("Ïß³Ì:%s,create  ThreadScopeBean,{sope=%s},{this=%s}",  Thread.currentThread(),  beanScope,  this));
    }
}


