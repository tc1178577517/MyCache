package com.tc.bs;


import com.github.houbb.heaven.util.common.ArgUtil;
import com.tc.api.ICache;
import com.tc.api.ICacheEvict;
import com.tc.api.ICacheLoad;
import com.tc.api.ICachePersist;
import com.tc.core.Cache;
import com.tc.core.CacheContext;
import com.tc.support.evict.CacheEvicts;
import com.tc.support.load.CacheLoads;
import com.tc.support.persist.CachePersists;

import java.util.HashMap;
import java.util.Map;

public class CacheBootstrap<K, V> {
    private CacheBootstrap(){};

    /**
     * 创建对象实例
     * @return
     * @param <K>
     * @param <V>
     */
    public static <K, V> CacheBootstrap<K, V> newInstance(){
        return new CacheBootstrap<>();
    }

    /**
     * map实现
     */
    private Map<K, V> map = new HashMap<>();

    //默认不初始化
    private ICachePersist<K, V> cachePersist = CachePersists.none();

    /**
     * 大小限制
     */
    private int size = Integer.MAX_VALUE;

    /**
     * 加载策略--默认不加载
     */
    private ICacheLoad<K, V> cacheLoad = CacheLoads.none();

    /**
     * 淘汰策略
     */
    private ICacheEvict<K, V> evict = CacheEvicts.fifo();

    /**
     * 设置size信息
     * @param size
     * @return
     */
    public CacheBootstrap<K, V> size(int size){
        ArgUtil.notNegative(size, "size");
        this.size = size;
        return this;
    }

    /**
     * 设置淘汰策略
     * @param evict
     * @return
     */
    public CacheBootstrap<K, V> evict(ICacheEvict<K, V> evict) {
        this.evict = evict;
        return this;
    }

    /**
     * map 的实现
     * @param map
     * @return
     */
    public CacheBootstrap map(Map<K, V> map){
        ArgUtil.notNull(map, "map");
        this.map = map;
        return this;
    }

    /**
     * 设置持久化策略
     * @return
     */
    public CacheBootstrap persist(ICachePersist<K, V> cachePersist){
        this.cachePersist = cachePersist;
        return this;
    }

    /**
     * 构建缓存信息
     * @return
     */
    public ICache<K, V> build(){
        CacheContext<K, V> context = new CacheContext<>();
        context.cacheEvict(evict);
        context.map(map);
        context.size(size);
        Cache<K, V> cache = new Cache<>(context);
        cache.load(cacheLoad);
        cache.persist(cachePersist);
        cache.init();
        return cache;
    }

    /**
     * 设置加载策略
     * @param cacheLoad
     * @return
     */
    public CacheBootstrap<K, V> load(ICacheLoad<K, V> cacheLoad){
        this.cacheLoad = cacheLoad;
        return this;
    }
}
