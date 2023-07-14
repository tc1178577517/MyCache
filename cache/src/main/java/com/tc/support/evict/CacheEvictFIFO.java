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
    public void evict(ICacheEvictContext<K, V> context) {
        final ICache<K, V> cache = context.cache();
        //如果查出限制，淘汰数据
        if(cache.size() >= context.size()){
            K evictKey = queue.remove();
            //移除队头
            cache.remove(evictKey);
        }

        //将新加入的元素放入队尾
        final K key = context.key();
        queue.add(key);
    }
}
