package com.tc.core;

import com.tc.api.ICache;
import com.tc.api.ICacheEvict;
import com.tc.exception.CacheRuntimeException;
import com.tc.support.evict.CacheEvictContext;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class Cache<K, V> implements ICache<K, V> {
    /**
     * map信息
     */
    private final Map<K, V> map;

    /**
     * 大小限制
     */
    private final int sizeLimit;
    /**
     * 淘汰策略
     */
    private final ICacheEvict<K, V> cacheEvict;

    public Cache(CacheContext<K, V> context) {
        this.map = context.map();
        this.sizeLimit = context.size();
        this.cacheEvict = context.cacheEvict();
    }

    @Override
    public ICache<K, V> expire(K key, long timeInMills) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ICache<K, V> expireAt(K key, long timeInMills) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return map.get(key);
    }

    @Override
    public V put(K key, V value) {
        //尝试淘汰
        CacheEvictContext<K, V> context = new CacheEvictContext<>();
        context.key(key).size(sizeLimit).cache(this);
        cacheEvict.evict(context);

        //判断淘汰后信息
        if(isSizeLimit()){
            throw new CacheRuntimeException("当前队列已满，数据添加失败！");
        }
        //执行添加
        return map.put(key, value);
    }

    private boolean isSizeLimit() {
        final int currentSize = this.size();
        return currentSize >= this.sizeLimit;
    }

    @Override
    public V remove(Object key) {
        return map.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        map.putAll(m);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        return map.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return map.entrySet();
    }
}
