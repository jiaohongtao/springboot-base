package com.hong.test.company;

import com.alibaba.fastjson.JSON;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.apache.geode.cache.client.ClientRegionFactory;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.apache.geode.cache.query.FunctionDomainException;
import org.apache.geode.cache.query.NameResolutionException;
import org.apache.geode.cache.query.QueryInvocationTargetException;
import org.apache.geode.cache.query.TypeMismatchException;
import org.apache.geode.cache.query.internal.ResultsBag;

import java.util.Date;
import java.util.Iterator;

public class FeodeDemo {
    String regionName = "user";
    int userNum = 10;

    public static void main(String[] args) {
        new FeodeDemo().test();
    }

    public void test() {
        Region<Integer, Object> region = null;
        //使用池连接的方式创建一个定位器
        ClientCache cacheS = new ClientCacheFactory().addPoolLocator("10.1.3.13", 10334).create();
        //在已有的缓存服务器上连接一个region，CACHING_PROXY表示允许本地数据存储还可以参照官方文档动态创建region
        ClientRegionFactory<Integer, Object> rf = cacheS.createClientRegionFactory(ClientRegionShortcut.CACHING_PROXY);
        region = rf.create(regionName);
        //获取当前的分布式服务器
        System.out.println(cacheS.getCurrentServers());

        System.out.println("开始创建用户");
        create(region);
        select(region);
        System.out.println("完成创建用户");

        System.out.println("开始修改用户");
        update(region);
        select(region);
        System.out.println("完成修改用户");


        System.out.println("开始删除用户");
        delete(region, 108);
        select(region);
        System.out.println("完成删除用户");


        System.out.println("开始清空表");
        truncate(region);
        select(region);
        System.out.println("完成清空表");

        //关闭表
        region.close();

    }

    /**
     * 查询user表
     */
    public void select(Region<Integer, Object> region) {
        try {
            Object objList = region.query("select * from /" + regionName + " u where u.age>15");

            if (objList instanceof ResultsBag) {
                for (Object o : (ResultsBag) objList) {
                    UserBean userBean = (UserBean) o;
                    System.out.println("User信息： " + JSON.toJSONString(userBean));
                }
            }
            Object obj = region.get(108);
            if (obj instanceof UserBean) {
                System.out.println("User108的信息： " + obj);
            }
        } catch (FunctionDomainException | TypeMismatchException | NameResolutionException | QueryInvocationTargetException e) {
            e.printStackTrace();
        }

    }

    //增加10个
    public void create(Region<Integer, Object> region) {
        region.entrySet();
        for (int i = 0; i < userNum; i++) {
            int id = i + 100;
            // region.put(id, new UserBean(id, 10 + i, "username:" + id, new Date()));
            region.create(id, new UserBean(id, 10 + i, "username:" + id, new Date()));
        }
    }

    public void update(Region<Integer, Object> region) {
        UserBean user108 = (UserBean) region.get(108);
        if (user108 != null) {
            System.out.println("User108信息" + JSON.toJSONString(user108));
            user108.setAge(12);
            region.put(user108.getId(), user108);
        }
    }

    //删除某个用户
    public void delete(Region<Integer, Object> region, int id) {
        region.remove(id);
    }

    //清空表
    public void truncate(Region<Integer, Object> region) {
        region.clear();
    }
}