package com.tc;

import com.tc.api.ICache;
import com.tc.bs.CacheBootstrap;
import com.tc.core.Cache;
import com.tc.listener.MyRemoveListener;
import com.tc.load.MyCacheLoad;
import com.tc.support.load.CacheLoads;
import com.tc.support.persist.CachePersists;
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

    @Test
    public void LoadTest() throws InterruptedException {
        Cache<String, String> cache = (Cache<String, String>) CacheBootstrap.<String,String>newInstance()
                .size(4)
                .load(new MyCacheLoad())
                .build();

        System.out.println(cache.keySet());
    }

    @Test
    public void PersistsTest() throws InterruptedException {
        Cache<String, String> cache = (Cache<String, String>) CacheBootstrap.<String,String>newInstance()
                .load(new MyCacheLoad())
                .persist(CachePersists.<String, String> dbJson("C:\\Users\\volcano\\Desktop\\1.rdb"))
                .build();

        Assert.assertEquals(2, cache.size());
        TimeUnit.SECONDS.sleep(5);
    }

//    @Test
//    public void LogTest(){
//        Cache<String, String> cache = (Cache<String, String>) CacheBootstrap.<String,String>newInstance()
//                .size(2)
//                .addRemoveListener(new MyRemoveListener<String, String>())
//                .load(new MyCacheLoad())
//                .build();
//
//        cache.put("removeLog1","test");
//        cache.put("removeLog2","test");
////        cache.clear();
//    }

    /**
     * 删除监听器测试
     */
    @Test
    public void cacheRemoveListenerTest() {
        ICache<String, String> cache = CacheBootstrap.<String,String>newInstance()
                .size(1)
                .addRemoveListener(new MyRemoveListener<String, String>())
                .build();

        cache.put("1", "1");
        cache.put("2", "2");
    }

    /**
     * 持久化 AOF 接口测试
     * @since 0.0.10
     */
    @Test
    public void persistAofTest() throws InterruptedException {
        ICache<String, String> cache = CacheBootstrap.<String,String>newInstance()
                .persist(CachePersists.<String, String>aof("C:\\Users\\volcano\\Desktop\\1.aof"))
                .build();

        cache.put("1", "1");
        cache.expire("1", 10);
        cache.remove("2");

        TimeUnit.SECONDS.sleep(1);
    }

    /**
     * 加载 AOF 接口测试
     * @since 0.0.10
     */
    @Test
    public void loadAofTest() throws InterruptedException {
        ICache<String, String> cache = CacheBootstrap.<String,String>newInstance()
                .load(CacheLoads.<String, String>aof("default.aof"))
                .build();

        Assert.assertEquals(1, cache.size());
        System.out.println(cache.keySet());
    }
}
