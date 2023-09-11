package com.tc.support.persist;


import com.tc.api.ICache;
import com.tc.api.ICachePersist;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 定时执行的持久化策略
 * @param <K>
 * @param <V>
 */
public class InnerCachePersist <K, V>{
    /**
     * 缓存信息
     */
    private final ICache<K,V> cache;

    /**
     * 缓存持久化策略
     */
    private final ICachePersist<K,V> persist;

    /**
     * 线程执行类
     * @since 0.0.3
     */
    private static final ScheduledExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();

    public InnerCachePersist(ICache<K, V> cache, ICachePersist<K, V> persist) {
        this.cache = cache;
        this.persist = persist;
        
        this.init();
    }

    private void init() {
        EXECUTOR_SERVICE.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try{
                    System.out.println("线程开始持久化缓存");
                    persist.persist(cache);
                    System.out.println("持久化缓存结束");
                }catch (Exception e){
                    System.out.println("持久化异常");
                    e.printStackTrace();
                }

            }
        }, 0, 10, TimeUnit.MINUTES);
    }
}
