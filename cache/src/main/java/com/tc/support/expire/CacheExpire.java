package com.tc.support.expire;

import com.tc.api.ICache;
import com.tc.api.ICacheExpire;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 轮询清理：固定 100ms 清理一次，每次最多清理 100 个
 * @param <K>
 * @param <V>
 */
public class CacheExpire<K, V> implements ICacheExpire<K, V>{
    /**
     * 单次清空的数量限制
     */
    private static final int LIMIT = 100;

    /**
     * 维护的过期表
     * 用于存储已经过期的key和应该过期的时间
     */
    private final Map<K, Long> expireMap = new HashMap<>();

    /**
     * 缓存实现
     */
    private final ICache<K, V> cache;

    /**
     * 线程执行类
     */
    private static final ScheduledExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();


    public CacheExpire(ICache<K, V> cache) {
        this.cache = cache;
        this.init();
    }

    private void init(){
        EXECUTOR_SERVICE.scheduleAtFixedRate(new ExpireThread(), 100, 100, TimeUnit.MILLISECONDS);
    }

    private class ExpireThread implements Runnable{

        @Override
        public void run() {
            //判断是否非空
            if(expireMap.isEmpty()) return;
            //处理
            int count = 0;
            for(Map.Entry<K, Long> entry : expireMap.entrySet()) {
                if(count >= LIMIT) return;
                expireKey(entry);
                count++;
            }
        }
    }

    /**
     * 执行过期的操作--entry为参数
     * @param entry
     */
    private void expireKey(Map.Entry<K, Long> entry) {
        final K key = entry.getKey();
        final Long expireAt = entry.getValue();

        //删除的逻辑处理
        long currentTime = System.currentTimeMillis();
        if(currentTime >= expireAt){
            expireMap.remove(key);
            //如果线程在此终止，后续可通过惰性删除删除这条缓存的数据
            cache.remove(key);
        }
    }

    /**
     * 过期处理key
     * @param key
     */
    private void expireKey(final K key){
        Long expireAt = expireMap.get(key);
        if(expireAt == null) return;
        long currentTime = System.currentTimeMillis();
        if(currentTime >= expireAt){
            expireMap.remove(key);
            //如果线程在此终止，后续可通过惰性删除删除这条缓存的数据
            cache.remove(key);
        }
    }



    @Override
    public void expire(K key, long expireAt) {
        expireMap.put(key, expireAt);
    }

    @Override
    public void refreshExpire(Collection<K> keyList) {
        if(keyList.isEmpty()) return;
        if(keyList.size() < expireMap.size()) for (K key : keyList) expireKey(key);
        else for(Map.Entry<K, Long> entry : expireMap.entrySet()) this.expireKey(entry);

    }

    @Override
    public Long expireTime(K key) {
        return expireMap.get(key);
    }


}
