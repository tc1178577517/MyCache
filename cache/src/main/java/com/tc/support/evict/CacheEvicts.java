package com.tc.support.evict;

import com.tc.api.ICacheEvict;

public class CacheEvicts {
    /**
     * 无策略
     * @return
     * @param <K>
     * @param <V>
     */
    public static <K, V> ICacheEvict<K, V> none(){
        return new CacheEvictNone<>();
    }

    /**
     * 先进先出
     * @return
     * @param <V>
     * @param <K>
     */
    public static <V, K> ICacheEvict<K,V> fifo() {
        return new CacheEvictFIFO<>();
    }
}
