package com.tc.support.proxy;

import com.tc.api.ICache;
import com.tc.support.proxy.cglib.CglibProxy;
import com.tc.support.proxy.dynamic.DynamicProxy;

import java.lang.reflect.Proxy;

public final class CacheProxy {

    private CacheProxy(){}

    /**
     * 获取对象代理
     * @param cache
     * @return 代理信息
     * @param <K>
     * @param <V>
     */
    private static <K, V>ICache<K, V> getProxy(final ICache<K, V> cache){

        final Class clazz = cache.getClass();

        // 如果targetClass本身是个接口或者targetClass是JDK Proxy生成的,则使用JDK动态代理。
        // 参考 spring 的 AOP 判断
        if (clazz.isInterface() || Proxy.isProxyClass(clazz)) {
            return (ICache<K,V>) new DynamicProxy(cache).proxy();
        }

        return (ICache<K,V>) new CglibProxy(cache).proxy();
    }
}
