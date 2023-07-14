package com.tc.support.evict;

import com.tc.api.ICache;
import com.tc.api.ICacheEvictContext;

public class CacheEvictContext<K, V> implements ICacheEvictContext<K, V> {

    private K key;

    private  ICache<K, V> cache;

    private int size;

    @Override
    public K key() {
        return key;
    }



    @Override
    public ICache<K, V> cache() {
        return cache;
    }

    @Override
    public int size() {
        return size;
    }

    public CacheEvictContext<K, V> key(K key) {
        this.key = key;
        return this;
    }

    public CacheEvictContext<K, V> size(int size) {
        this.size = size;
        return this;
    }

    public CacheEvictContext<K, V> cache(ICache<K, V> cache) {
        this.cache = cache;
        return this;
    }
}
