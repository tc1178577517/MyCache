package com.tc.api;

/**
 * 淘汰策略
 * @param <K>
 * @param <V>
 */
public interface ICacheEvict<K, V> {
    /**
     *
     * @param context
     */
    void evict(final ICacheEvictContext<K, V> context);
}
