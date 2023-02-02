package com.hong.test.company;

import java.util.Map;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.*;


/**
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2023/01/29
 */
public class GeodeTest {

    public static void main(String[] args) {
        ClientCache cache = new ClientCacheFactory()
                .addPoolLocator("10.1.3.13", 10334)
                .create();
        Region<String, String> region = cache
                .<String, String>createClientRegionFactory(ClientRegionShortcut.CACHING_PROXY)
                .create("hello");

        region.put("1", "Hello");
        region.put("2", "World");

        for (Map.Entry<String, String> entry : region.entrySet()) {
            System.out.format("key = %s, value = %s\n", entry.getKey(), entry.getValue());
        }
        cache.close();
    }
}
