package com.tc.support.load;

import com.tc.api.ICache;
import com.tc.api.ICacheLoad;

/**
 * 加载缓存工具类
 */
public class CacheLoads {

    private CacheLoads(){}


    public static <K,V> ICacheLoad<K,V> none() {
        return new CacheLoadNone<>();
    }
}
