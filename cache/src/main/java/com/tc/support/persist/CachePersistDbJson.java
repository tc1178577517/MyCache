package com.tc.support.persist;

import com.github.houbb.heaven.util.util.JsonUtil;
import com.tc.api.ICache;
import com.tc.api.ICachePersist;
import com.github.houbb.heaven.util.io.FileUtil;
import com.alibaba.fastjson.JSON;

import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Set;

/**
 * 通过Json方式持久化
 */
public class CachePersistDbJson<K , V> implements ICachePersist<K , V> {

    //数据库路径
    private final String dbPath;

    public CachePersistDbJson(String dbPath) {
        this.dbPath = dbPath;
    }

    /**
     * 以Json的方式持久化到文件中
     * @param cache 缓存
     */
    @Override
    public void persist(ICache cache) {
        Set<Map.Entry<K,V>> entrySet = cache.entrySet();

        // 创建文件
        FileUtil.createFile(dbPath);
        System.out.println(dbPath);
        // 清空文件
        FileUtil.truncate(dbPath);

        for(Map.Entry<K, V> entry : entrySet){
            K key = entry.getKey();
            Long expireTime = cache.expire().expireTime(key);

            PersistEntry persistEntry = new PersistEntry();

            persistEntry.setKey(key);
            persistEntry.setValue(entry.getValue());
            persistEntry.setExpire(expireTime);

            String data = JSON.toJSONString(persistEntry);
            FileUtil.write(dbPath, data, StandardOpenOption.APPEND);

        }

    }
}
