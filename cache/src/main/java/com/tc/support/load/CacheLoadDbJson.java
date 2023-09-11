package com.tc.support.load;

import com.alibaba.fastjson.JSON;
import com.github.houbb.heaven.util.io.FileUtil;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.tc.api.ICache;
import com.tc.api.ICacheLoad;
import com.tc.support.persist.PersistEntry;

import java.util.Collection;
import java.util.List;

public class CacheLoadDbJson<K,V> implements ICacheLoad<K,V> {

    private final String dbPath;

    public CacheLoadDbJson(String dbPath) {
        this.dbPath = dbPath;
    }

    @Override
    public void load(ICache<K, V> cache) {
        List<String> lines = FileUtil.readAllLines(dbPath);
        System.out.println("开始载入文件");
        if(lines.isEmpty()) {
            System.out.println("文件内容为空");
            return;
        }
        for(String line : lines){
            if(StringUtil.isEmpty(line)) continue;

            //简单类型的序列化
            PersistEntry<K, V> entry = JSON.parseObject(line, PersistEntry.class);

            K key = entry.getKey();
            V value = entry.getValue();
            Long expire = entry.getExpire();

            cache.put(key, value);
            if(ObjectUtil.isNotNull(expire)) cache.expireAt(key, expire);
        }
    }
}
