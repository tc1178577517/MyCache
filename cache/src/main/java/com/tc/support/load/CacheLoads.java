package com.tc.support.load;

import com.tc.api.ICache;
import com.tc.api.ICacheLoad;

/**
 * 加载缓存工具类
 */
public class CacheLoads {

    private CacheLoads(){}

    /**
     * RDB文件加载模式
     * @return
     * @param <K>
     * @param <V>
     */
    public static <K,V> ICacheLoad<K,V> none() {
        return new CacheLoadNone<>();
    }

    /**
     * AOF 文件加载模式
     * @param dbPath 文件路径
     * @param <K> key
     * @param <V> value
     * @return 值
     * @since 0.0.10
     */
    public static <K,V> ICacheLoad<K,V> aof(final String dbPath) {
        return new CacheLoadAof<>(dbPath);
    }
}
