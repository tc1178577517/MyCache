package com.tc.core;

import com.tc.api.ICacheContext;
import com.tc.api.ICacheEvict;
import com.tc.api.ICacheEvictContext;

import java.util.Map;

/**
 * 缓存上下文
 * @param <K>
 * @param <V>
 */
public class CacheContext<K, V> implements ICacheContext<K, V> {

    private Map<K, V> map;

    private int size;

    private ICacheEvict<K, V> cacheEvict;
    @Override
    public Map<K, V> map() {
        return map;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public ICacheEvict<K, V> cacheEvict() {
        return cacheEvict;
    }

    public CacheContext<K, V> cacheEvict(ICacheEvict<K, V> cacheEvict) {
        this.cacheEvict = cacheEvict;
        return this;
    }

    public CacheContext<K, V> map(Map<K, V> map) {
        this.map = map;
        return this;
    }

    public CacheContext<K, V> size(int size) {
        this.size = size;
        return this;
    }

}
