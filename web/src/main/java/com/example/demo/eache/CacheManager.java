package com.example.demo.eache;

import com.example.demo.util.HttpClientPoolUtil;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CacheManager {
    private static Map<String,String> headMap = new HashMap<String,String>();
    @CacheEvict
    public void flushCache() {

    }

    @Cacheable(cacheNames = "testCacheable")
    public String testCacheable() throws Exception {
        System.out.println("执行缓存方法");
        String str = "缓存值";
        return str;
    }
}
