package com.tc.support.proxy.dynamic;

import com.tc.api.ICache;
import com.tc.support.proxy.ICacheProxy;
import com.tc.support.proxy.bs.CacheProxyBs;
import com.tc.support.proxy.bs.CacheProxyBsContext;
import com.tc.support.proxy.bs.ICacheProxyBsContext;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxy implements InvocationHandler, ICacheProxy {
    /**
     * 被代理的对象
     */
    private final ICache target;

    public DynamicProxy(ICache target) {
        this.target = target;
    }

    @Override
    public Object proxy() {
        // 我们要代理哪个真实对象，就将该对象传进去，最后是通过该真实对象来调用其方法的
        InvocationHandler handler = new DynamicProxy(target);

        return Proxy.newProxyInstance(handler.getClass().getClassLoader(),
                target.getClass().getInterfaces(), handler);
    }

    /**
     * 这种方式虽然实现了异步执行，但是存在一个缺陷：
     * 强制用户返回值为 Future 的子类。
     *
     * 如何实现不影响原来的值，要怎么实现呢？
     * @param proxy 原始对象
     * @param method 方法
     * @param args 入参
     * @return 结果
     * @throws Throwable 异常
     */
    @Override
    @SuppressWarnings("all")
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        ICacheProxyBsContext context = CacheProxyBsContext.newInstance()
                .method(method).params(args).target(target);
        return CacheProxyBs.newInstance().context(context).execute();
    }
}
