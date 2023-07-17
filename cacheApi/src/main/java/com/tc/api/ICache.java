package com.tc.api;

import java.util.Map;

public interface ICache<K, V> extends Map<K, V> {
    /**
     * 给数据设置过期时间
     * 到期暂时不删除，后期补充删除策略
     * @param key
     * @param timeInMills 时间戳
     * @return this
     */
    ICache<K, V> expire(final K key, final long timeInMills);

    /**
     * 指定时间过期
     * @param key
     * @param timeInMills 时间戳
     * @return this
     */
    ICache<K, V> expireAt(final K key, final long timeInMills);
}
