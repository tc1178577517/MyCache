package com.tc.support.persist;

/**
 * 持久化明细
 * @param <K>
 * @param <V>
 */
public class PersistEntry<K, V> {
    private  K key;

    private V value;

    private Long expire;

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public Long getExpire() {
        return expire;
    }

    public void setExpire(Long expire) {
        this.expire = expire;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }
}
