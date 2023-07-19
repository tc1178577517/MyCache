package com.tc;

import com.tc.bs.CacheBootstrap;
import com.tc.core.Cache;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;


public class CacheBsTest {

    @Test
    public void release0_1Test(){
        Cache<String, String> cache = (Cache<String, String>) CacheBootstrap.<String, String>newInstance()
                .size(3)
                .build();

        cache.put("1", "1");
        cache.put("2", "2");
        cache.put("3", "3");
        cache.put("4", "4");

        Assert.assertEquals(3, cache.size());
        System.out.println(cache.keySet());
    }

    @Test
    public void release0_2Test() throws InterruptedException {
        Cache<String, String> cache = (Cache<String, String>) CacheBootstrap.<String,String>newInstance()
                .size(3)
                .build();
        cache.put("1", "1");
        cache.put("2", "2");

        cache.expire("1", 10);
        Assert.assertEquals(2, cache.size());

        TimeUnit.MILLISECONDS.sleep(50);
        Assert.assertEquals(1, cache.size());

        System.out.println(cache.keySet());
    }
}
