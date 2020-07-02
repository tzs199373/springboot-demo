package com.web.startrun;

import com.web.service.ThreadScopeBean;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class TestRunner implements CommandLineRunner {
    @Autowired
    ObjectFactory<ThreadScopeBean> threadScopeBeanObjectFactory;

    @Override
    public void run(String... strings) throws Exception {
        //使用容器获取bean
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread() + "," + threadScopeBeanObjectFactory.getObject());
                System.out.println(Thread.currentThread() + "," + threadScopeBeanObjectFactory.getObject());
            }).start();
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
