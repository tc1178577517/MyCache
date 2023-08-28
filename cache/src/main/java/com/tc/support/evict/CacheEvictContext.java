package com.tc.support.evict;

import com.tc.api.ICache;
import com.tc.api.ICacheEvictContext;

public class CacheEvictContext<K, V> implements ICacheEvictContext<K, V> {

    private K key;

    private  ICache<K, V> cache;

    private int size;

    /**
     * 返回key
     * @return key
     */
    @Override
    public K key() {
        return key;
    }


    /**
     * 返回cache对象
     * @return cache对象
     */
    @Override
    public ICache<K, V> cache() {
        return cache;
    }

    /**
     * 返回上下文大小
     * @return 上下文大小
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * 设置上下文的key并CacheEvictContext对象
     * @param key
     * @return
     */
    public CacheEvictContext<K, V> key(K key) {
        this.key = key;
        return this;
    }

    /**
     * 设置上下文的大小并返回CacheEvictContext对象
     * @param size
     * @return
     */
    public CacheEvictContext<K, V> size(int size) {
        this.size = size;
        return this;
    }

    /**
     * 设置cache并返回CacheEvictContext对象
     * @param cache
     * @return
     */
    public CacheEvictContext<K, V> cache(ICache<K, V> cache) {
        this.cache = cache;
        return this;
    }
}
