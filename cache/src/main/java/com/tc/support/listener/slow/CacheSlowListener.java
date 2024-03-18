package com.tc.support.listener.slow;

import com.alibaba.fastjson.JSON;
import com.tc.api.ICacheSlowListener;
import com.tc.api.ICacheSlowListenerContext;
import com.tc.support.interceptor.common.CacheInterceptorCost;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;

/**
 * 慢日志监听类
 * @since 0.0.9
 */
public class CacheSlowListener implements ICacheSlowListener {

    private static final Log log = LogFactory.getLog(CacheInterceptorCost.class);

    @Override
    public void listen(ICacheSlowListenerContext context) {
        log.warn("[Slow] methodName: {}, params: {}, cost time: {}",
                context.methodName(), JSON.toJSON(context.params()), context.costTimeMills());
    }

    @Override
    public long slowerThanMills() {
        return 1000L;
    }

}
