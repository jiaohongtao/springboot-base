package com.hong.test.company;

import java.util.Map;
import java.util.Set;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.RegionAttributes;
import org.apache.geode.cache.client.*;
import org.apache.geode.distributed.ServerLauncher;
import org.apache.geode.management.api.ClusterManagementListResult;
import org.apache.geode.management.api.ClusterManagementService;
import org.apache.geode.management.cluster.client.ClusterManagementServiceBuilder;
import org.apache.geode.management.runtime.RuntimeRegionInfo;


/**
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2023/01/29
 */
public class GeodeTest {

    public static void main(String[] args) {
        /*ClientCache cache = new ClientCacheFactory()
                .addPoolLocator("10.1.3.13", 10334)
                .create();

        getRegin(cache);

        if (!cache.isClosed()) {
            cache.close();
        }*/



        /*ClusterManagementService cmsClient = new ClusterManagementServiceBuilder()
                .setHost("localhost")
                .setPort("7070")
                .build();
        Region regionConfig = new Region();
        ClusterManagementListResult<Region, RuntimeRegionInfo> list = cmsClient.list(region);*/

        ServerLauncher serverLauncher = new ServerLauncher.Builder()
                .set("start-dev-rest-api", "true")
                .set("http-service-port", "8080")
                .set("http-service-bind-address", "10.1.0.152")
                .setPdxReadSerialized(true)
                .build();

        serverLauncher.start();

        System.out.println("REST server successfully started");
    }

    static void createRegin(ClientCache cache) {
        Region<String, String> region = cache
                .<String, String>createClientRegionFactory(ClientRegionShortcut.CACHING_PROXY)
                .create("hello");

        region.put("1", "Hello");
        region.put("2", "World");

        for (Map.Entry<String, String> entry : region.entrySet()) {
            System.out.format("key = %s, value = %s\n", entry.getKey(), entry.getValue());
        }
    }

    static void getRegin(ClientCache cache) {
        System.out.println("========================");
        Map<String, RegionAttributes<Object, Object>> stringRegionAttributesMap = cache.listRegionAttributes();
        stringRegionAttributesMap.forEach((k, v) -> {
            System.out.println(k);
            System.out.println(v.getPoolName());
            System.out.println(v);
        });

        /*Set<Region<?, ?>> regions = cache.rootRegions();
        regions.forEach((region -> region.keySet().forEach(k -> System.out.println(k + ":" + region.get(k)))));*/
    }
}
