package com.tc.api;

import java.util.Collection;

public interface ICacheExpire<K, V>{
    //定期删除
    void expire(final K key, final long expireAt);

    //惰性删除
    void refreshExpire(final Collection<K> keyList);

    /**
     * 待过期的 key
     * 不存在，则返回 null
     * @param key 待过期的 key
     * @return 结果
     * @since 0.0.8
     */
    Long expireTime(final K key);
}
