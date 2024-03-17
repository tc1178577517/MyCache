package com.tc.support.evict;

import com.tc.api.ICache;
import com.tc.api.ICacheEvict;
import com.tc.api.ICacheEvictContext;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 淘汰策略——先进先出
 * @param <K>
 * @param <V>
 */
public class CacheEvictFIFO<K, V> implements ICacheEvict<K, V> {
    private Queue<K> queue = new LinkedList<>();

    @Override
    public boolean evict(ICacheEvictContext<K, V> context) {
        boolean result = false;
        final ICache<K,V> cache = context.cache();
        // 超过限制，执行移除
        if(cache.size() >= context.size()) {
            K evictKey = queue.remove();
            // 移除最开始的元素
            cache.remove(evictKey);
            result = true;
        }

        // 将新加的元素放入队尾
        final K key = context.key();
        queue.add(key);

        return result;
    }
}
