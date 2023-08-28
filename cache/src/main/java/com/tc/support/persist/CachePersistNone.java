package com.tc.support.persist;

import com.tc.api.ICache;
import com.tc.api.ICachePersist;

public class CachePersistNone<K, V> implements ICachePersist<K, V> {
    @Override
    public void persist(ICache<K, V> cache) {

    }
}
