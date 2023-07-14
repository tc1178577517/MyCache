package com.tc.api;

import java.util.Map;

/**
 * 缓存上下文
 * @param <K>
 * @param <V>
 */
public interface ICacheContext<K, V>{
    /**
     *
     * @return map
     */
    Map<K, V> map();

    /**
     * 大小限制
     * @return
     */
    int size();

    /**
     * 淘汰策略
     * @return
     */
    ICacheEvict<K, V> cacheEvict();
}
