package com.web.statistics;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author tzs
 * @version 1.0
 * @Description 统计请求封装类
 * @since 2020/5/7 9:48
 */
public class Indicator{
    private static final Indicator INSTANCE = new Indicator();

    //请求数
    private final AtomicLong requestConut = new AtomicLong(0);
    //成功数
    private final AtomicLong successConut = new AtomicLong(0);
    //失败数
    private final AtomicLong failureConut = new AtomicLong(0);

    public static Indicator getINSTANCE() {
        return INSTANCE;
    }

    public long newRequestReceived(){
        return requestConut.incrementAndGet();
    }

    public long newRequestProcessed(){
        return successConut.incrementAndGet();
    }

    public long requestProcessedFailed(){
        return failureConut.incrementAndGet();
    }

    public long getRequestCount(){
        return requestConut.get();
    }

    public long getSuccessCount(){
        return successConut.get();
    }

    public long getFailureCount(){
        return failureConut.get();
    }

    public void reset(){
        requestConut.set(0);
        successConut.set(0);
        failureConut.set(0);
    }
}
