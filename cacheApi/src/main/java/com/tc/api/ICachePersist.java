package com.tc.api;

/**
 *
 * @param <K>
 * @param <V>
 */
public interface ICachePersist<K, V> {
    /**
     * 持久化缓存信息
     * @param cache 缓存
     */
    void persist(final ICache<K, V> cache);

}
