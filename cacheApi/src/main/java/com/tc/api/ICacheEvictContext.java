package com.tc.api;

public interface ICacheEvictContext<K, V>{
    K key();
    ICache<K, V> cache();

    /**
     * 获取大小
     * @return
     */
    int size();
}
