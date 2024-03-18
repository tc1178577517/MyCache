package com.tc.api;

import java.util.concurrent.TimeUnit;

/**
 *持久化接口
 * @param <K>
 * @param <V>
 */
public interface ICachePersist<K, V> {
    /**
     * 持久化缓存信息
     * @param cache 缓存
     * @since 0.0.7
     */
    void persist(final ICache<K, V> cache);

    /**
     * 延迟时间
     * @return 延迟
     * @since 0.0.10
     */
    long delay();

    /**
     * 时间间隔
     * @return 间隔
     * @since 0.0.10
     */
    long period();

    /**
     * 时间单位
     * @return 时间单位
     * @since 0.0.10
     */
    TimeUnit timeUnit();
}
