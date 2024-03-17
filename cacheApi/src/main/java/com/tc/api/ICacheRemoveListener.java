package com.tc.api;

//删除监听器接口
public interface ICacheRemoveListener<K,V> {
    /**
     * 监听
     * @param context 上下文
     * @since 0.0.6
     */
    void listen(final ICacheRemoveListenerContext<K,V> context);
}
