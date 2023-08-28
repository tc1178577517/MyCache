package com.tc.api;

/**
 * 加载接口
 * 从磁盘加载缓存信息
 * @param <K>
 * @param <V>
 */
public interface ICacheLoad <K, V>{

    /**
     * 从磁盘加载缓存信息
     * @param cache
     */
    void load(ICache<K, V> cache);
}
