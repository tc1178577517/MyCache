package com.tc.load;

import com.tc.api.ICache;
import com.tc.api.ICacheLoad;

public class MyCacheLoad implements ICacheLoad<String, String> {
    @Override
    public void load(ICache<String, String> cache) {
        cache.put("testLoad1", "test1");
        cache.put("testLoad2", "test2");
        System.out.println("loading");
    }
}
