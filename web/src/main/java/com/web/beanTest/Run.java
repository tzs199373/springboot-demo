package com.web.beanTest;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Run implements ApplicationContextAware {
    @Autowired
    private ApplicationContext ac;
    public void init(){
        Map<String, Father> settings = ac.getBeansOfType(Father.class);
        settings.forEach((key,val) ->{
            val.print();
        });
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        init();
    }
}
