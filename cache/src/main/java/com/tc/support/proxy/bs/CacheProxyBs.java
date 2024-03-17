package com.tc.support.proxy.bs;

import com.tc.api.ICache;

import java.util.List;

public final class CacheProxyBs {

    private CacheProxyBs(){}

    /**
     * 代理上下文
     * @since 0.0.4
     */
    private ICacheProxyBsContext context;

//    /**
//     * 默认通用拦截器
//     *
//     * JDK 的泛型擦除导致这里不能使用泛型
//     * @since 0.0.5
//     */
//    @SuppressWarnings("all")
//    private final List<ICacheInterceptor> commonInterceptors = CacheInterceptors.defaultCommonList();
//
//    /**
//     * 默认刷新拦截器
//     * @since 0.0.5
//     */
//    @SuppressWarnings("all")
//    private final List<ICacheInterceptor> refreshInterceptors = CacheInterceptors.defaultRefreshList();

    /**
     * 新建对象实例
     * @return 实例
     * @since 0.0.4
     */
    public static CacheProxyBs newInstance() {
        return new CacheProxyBs();
    }

    public CacheProxyBs context(ICacheProxyBsContext context) {
        this.context = context;
        return this;
    }

    /**
     * 执行
     * @return 结果
     * @since 0.0.4
     * @throws Throwable 异常
     */
    @SuppressWarnings("all")
    public Object execute() throws Throwable {
        //1. 开始的时间
        final long startMills = System.currentTimeMillis();
        final ICache cache = context.target();
//        CacheInterceptorContext interceptorContext = CacheInterceptorContext.newInstance()
//                .startMills(startMills)
//                .method(context.method())
//                .params(context.params())
//                .cache(context.target())
//                ;

//        //1. 获取刷新注解信息
//        CacheInterceptor cacheInterceptor = context.interceptor();
//        this.interceptorHandler(cacheInterceptor, interceptorContext, cache, true);

        //2. 正常执行
        Object result = context.process();

//        final long endMills = System.currentTimeMillis();
//        interceptorContext.endMills(endMills).result(result);

        // 方法执行完成
//        this.interceptorHandler(cacheInterceptor, interceptorContext, cache, false);
        return result;
    }

    /**
     * 拦截器执行类
     * @param cacheInterceptor 缓存拦截器
     * @param interceptorContext 上下文
     * @param cache 缓存
     * @param before 是否执行执行
     * @since 0.0.5
     */
//    @SuppressWarnings("all")
//    private void interceptorHandler(CacheInterceptor cacheInterceptor,
//                                    CacheInterceptorContext interceptorContext,
//                                    ICache cache,
//                                    boolean before) {
//        if(cacheInterceptor != null) {
//            //1. 通用
//            if(cacheInterceptor.common()) {
//                for(ICacheInterceptor interceptor : commonInterceptors) {
//                    if(before) {
//                        interceptor.before(interceptorContext);
//                    } else {
//                        interceptor.after(interceptorContext);
//                    }
//                }
//            }
//
//            //2. 刷新
//            if(cacheInterceptor.refresh()) {
//                for(ICacheInterceptor interceptor : refreshInterceptors) {
//                    if(before) {
//                        interceptor.before(interceptorContext);
//                    } else {
//                        interceptor.after(interceptorContext);
//                    }
//                }
//            }
//        }
//    }

}
