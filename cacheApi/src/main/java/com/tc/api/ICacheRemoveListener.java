package com.tc.api;

//删除监听器接口
public interface ICacheRemoveListener<K,V> {

    void listen(final ICacheRemoveListenerContext<K,V> context);
}
