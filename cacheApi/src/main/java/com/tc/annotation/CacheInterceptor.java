package com.tc.annotation;

import java.lang.annotation.*;

/**
 * 缓存拦截器
 * @since 0.0.5
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheInterceptor {

    /**
     * 通用拦截器
     *
     * 1. 耗时统计
     * 2. 慢日志统计
     *
     * etc.
     * @return 默认开启
     * @since 0.0.5
     */
    boolean common() default true;

    /**
     * 是否启用刷新
     * @return false
     * @since 0.0.5
     */
    boolean refresh() default false;

    /**
     * 操作是否需要 append to file，默认为 false
     * 主要针对 cache 内容有变更的操作，不包括查询操作。
     * 包括删除，添加，过期等操作。
     * @return 是否
     * @since 0.0.10
     */
    boolean aof() default false;

}

