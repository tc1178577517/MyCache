package com.tc.support.expire;

import com.tc.api.ICache;
import com.tc.api.ICacheExpire;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 删除策略——时间排序策略
 *
 * 优点：定时删除不用做过多消耗
 * 缺点：对惰性删除不太友好
 * @param <K>
 * @param <V>
 */

public class CacheExpireSort <K, V> implements ICacheExpire<K, V> {

    /**
     * 单次清空的数量限制
     */
    private static final int LIMIT = 100;

    /**
     * 排序
     */
    private final Map<Long, List<K>> sortMap = new TreeMap<>(new Comparator<Long>() {
        @Override
        public int compare(Long o1, Long o2) {
            return (int)(o1 - o2);
        }
    });

    /**
     * 过期map
     */
    private final Map<K, Long> expireMap = new HashMap<>();

    /**
     * 缓存实现
     */
    private final ICache<K, V> cache;

    private static final ScheduledExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();

    public CacheExpireSort(ICache<K, V> cache) {
        this.cache = cache;
        this.init();
    }

    /**
     * 初始化任务
     */
    private void init() {
        EXECUTOR_SERVICE.scheduleAtFixedRate(new ExpireThread(), 100, 100,TimeUnit.MILLISECONDS);
    }

    /**
     * 定时线程执行类
     */
    private class ExpireThread implements Runnable{

        @Override
        public void run() {

        }
    }

    @Override
    public void expire(K key, long expireAt) {

    }

    @Override
    public void refreshExpire(Collection<K> keyList) {

    }

    @Override
    public Long expireTime(K key) {
        return null;
    }
}
