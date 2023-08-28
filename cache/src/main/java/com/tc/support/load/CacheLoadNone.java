package com.tc.support.load;

import com.tc.api.ICache;
import com.tc.api.ICacheLoad;

public class CacheLoadNone<K, V> implements ICacheLoad {
    @Override
    public void load(ICache cache) {
        //不加载
    }
}
