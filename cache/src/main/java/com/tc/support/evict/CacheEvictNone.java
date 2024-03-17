package com.tc.support.evict;

import com.tc.api.ICacheEvict;
import com.tc.api.ICacheEvictContext;

/**
 * 淘汰策略——无淘汰
 * @param <K>
 * @param <V>
 */
public class CacheEvictNone <K, V> implements ICacheEvict<K, V> {
    @Override
    public boolean evict(ICacheEvictContext<K, V> context) {

        return false;
    }
}
