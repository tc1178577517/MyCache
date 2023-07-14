package com.tc;

import com.github.houbb.heaven.support.cache.ICache;
import com.tc.bs.CacheBootstrap;
import com.tc.core.Cache;
import org.junit.Assert;
import org.junit.Test;




public class CacheBsTest {

    @Test
    public void helloTest(){
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
}
