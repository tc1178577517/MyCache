package com.tc.api;

import java.util.Collection;

public interface ICacheExpire<K, V>{
    //定期删除
    void expire(final K key, final long expireAt);

    //惰性删除
    void refreshExpire(final Collection<K> keyList);
}
