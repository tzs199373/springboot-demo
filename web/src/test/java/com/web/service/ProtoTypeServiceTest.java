package com.web.service;

import com.web.WebApplication;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

@SpringBootTest(classes = WebApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class ProtoTypeServiceTest {

    @Autowired
    ObjectFactory<ThreadScopeBean> threadScopeBeanObjectFactory;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void hello() throws Exception {
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                System.out.println("haha,my test");
                System.out.println(Thread.currentThread() + "," + threadScopeBeanObjectFactory.getObject());
                System.out.println(Thread.currentThread() + "," + threadScopeBeanObjectFactory.getObject());
            }).start();
            TimeUnit.SECONDS.sleep(1);
        }
    }

}