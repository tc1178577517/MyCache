package com.tc.api;

import java.util.List;
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

    /**
     * 获取缓存的过期处理类
     * @return ICacheExpire
     */
    ICacheExpire<K,V> expire();


    /**
     * 从磁盘中加载缓存信息
     * @param cacheLoad
     * @return
     */
    ICache<K, V> load(ICacheLoad<K, V> cacheLoad);

    List<ICacheRemoveListener<K, V>> removeListeners();

    /**
     * 持久化类
     * @return 持久化类
     * @since 0.0.10s
     */
    ICachePersist<K,V> persist();

    /**
     * 慢日志监听类列表
     * @return 监听器列表
     * @since 0.0.9
     */
    List<ICacheSlowListener> slowListeners();
}
