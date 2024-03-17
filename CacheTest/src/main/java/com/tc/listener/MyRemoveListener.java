package com.tc.listener;

import com.tc.api.ICacheRemoveListener;
import com.tc.api.ICacheRemoveListenerContext;

/**
 * @since 0.0.6
 */
public class MyRemoveListener<K,V> implements ICacheRemoveListener<K,V> {

    @Override
    public void listen(ICacheRemoveListenerContext<K, V> context) {
        System.out.println("【删除提示】--监听器Test--" + context.key());
    }

}
