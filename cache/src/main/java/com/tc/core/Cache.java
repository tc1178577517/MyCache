package com.tc.core;

import com.tc.api.*;
import com.tc.exception.CacheRuntimeException;
import com.tc.support.evict.CacheEvictContext;
import com.tc.support.expire.CacheExpire;
import com.tc.support.load.CacheLoads;
import com.tc.support.persist.InnerCachePersist;
import lombok.extern.log4j.Log4j;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

@Log4j
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
    /**
     * 过期策略
     * 暂时不做暴露
     */
    private ICacheExpire<K, V> cacheExpire;

    /**
     * 从磁盘中加载缓存信息
     */
    private ICacheLoad<K, V> load;

    private ICachePersist<K, V> cachePersist;

    /**
     * 用上下文构造cache
     * @param context
     */
    public Cache(CacheContext<K, V> context) {
        this.map = context.map();
        this.sizeLimit = context.size();
        this.cacheEvict = context.cacheEvict();
        this.cacheExpire = new CacheExpire<>(this);
    }

    /**
     * 从磁盘加载缓存
     * @return
     */
    public ICache<K, V> load(){
        return this;
    }

    public ICache<K, V> load(ICacheLoad cacheLoad){
        this.load = cacheLoad;
        return this;
    }

    public ICache<K, V> persist(ICachePersist<K, V> cachePersist){
        this.cachePersist = cachePersist;
        return this;
    }

    public void setCachePersist(ICachePersist<K, V> cachePersist){
        this.cachePersist = cachePersist;
    }

    /**
     * 初始化方法
     *
     */
    public void init(){
        this.load.load(this);
        this.cacheExpire = new CacheExpire<>(this);

        // 初始化持久化
        if(this.cachePersist != null) {
            new InnerCachePersist<>(this, cachePersist);
        }
    }

    /**
     * 为key设置过期时间
     * @param key
     * @param timeInMills 时间戳
     * @return
     */
    @Override
    public ICache<K, V> expire(K key, long timeInMills) {

        long expireTime = System.currentTimeMillis() + timeInMills;
        return this.expireAt(key, expireTime);
    }

    public ICacheExpire<K, V> expire() {
        return this.cacheExpire;
    }

    @Override
    public ICache<K, V> expireAt(K key, long timeInMills) {
        this.cacheExpire.expire(key, timeInMills);
        return this;
    }

    @Override
    public int size() {
        //1. 刷新所有过期信息
        this.refreshExpireAllKeys();

        return map.size();
    }



    @Override
    public boolean isEmpty() {
        //1. 刷新所有过期信息
        this.refreshExpireAllKeys();

        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        //1. 刷新所有过期信息
        this.refreshExpireAllKeys();

        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        //刷新所有过期信息
        K genericKey = (K) key;
        this.cacheExpire.refreshExpire(Collections.singletonList(genericKey));

        return map.get(key);
    }

    @Override
    public V put(K key, V value) {
        //检查是否需要淘汰数据
        CacheEvictContext<K, V> context = new CacheEvictContext<>();
        context.key(key).size(sizeLimit).cache(this);
        cacheEvict.evict(context);

        //判断淘汰后信息
        if(isSizeLimit()){
            throw new CacheRuntimeException("当前队列已满，数据添加失败！");
        }
        //输出日志
        log.debug("添加缓存—— key 为"+key.toString()+" value 为"+value.toString());
        //执行添加
        return map.put(key, value);
    }

    private boolean isSizeLimit() {
        final int currentSize = this.size();
        return currentSize >= this.sizeLimit;
    }

    @Override
    public V remove(Object key) {
        //输出日志
        log.debug("key为"+key.toString()+" value 为"+this.get(key)+" 的缓存被删除");
        return map.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        map.putAll(m);
    }

    @Override
    public void clear() {
        //输出日志
        log.debug("缓存被清空");
        map.clear();
    }

    @Override
    public Set<K> keySet() {
        //1. 刷新所有过期信息
        this.refreshExpireAllKeys();

        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        //1. 刷新所有过期信息
        this.refreshExpireAllKeys();

        return map.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        //1. 刷新所有过期信息
        this.refreshExpireAllKeys();

        return map.entrySet();
    }

    private void refreshExpireAllKeys() {
        this.cacheExpire.refreshExpire(map.keySet());
    }
}
