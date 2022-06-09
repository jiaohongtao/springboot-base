package com.hong.util.date20200612;

import com.google.common.collect.Maps;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.util.Date;
import java.util.Map;

/**
 * 类映射类
 *
 * @author jiaohongtao
 * @version 1.0
 * @since 2020年06月12日
 */
public class ReflectUtil {
    private static Logger logger = LoggerFactory.getLogger(ReflectUtil.class);

    public static Object getObject(Object dest, Map<String, Object> addProperties) {
        PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
        PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(dest);
        Map<String, Class> propertyMap = Maps.newHashMap();
        for (PropertyDescriptor d : descriptors) {
            if (!"class".equalsIgnoreCase(d.getName())) {
                propertyMap.put(d.getName(), d.getPropertyType());
            }
        }
        addProperties.forEach((k, v) -> {
            String sclass = v.getClass().toString();
            if ("class java.util.Date".equals(sclass)) {//对日期进行处理
                propertyMap.put(k, Long.class);
            } else {
                propertyMap.put(k, v.getClass());
            }

        });
        DynamicBean dynamicBean = new DynamicBean(dest.getClass(), propertyMap);
        propertyMap.forEach((k, v) -> {
            try {
                if (!addProperties.containsKey(k)) {
                    dynamicBean.setValue(k, propertyUtilsBean.getNestedProperty(dest, k));
                }
            } catch (Exception e) {
                logger.error("动态添加字段出错", e);
            }
        });
        addProperties.forEach((k, v) -> {
            try {
                String sclass = v.getClass().toString();
                if ("class java.util.Date".equals(sclass)) {//动态添加的字段为date类型需要进行处理
                    Date date = (Date) v;
                    dynamicBean.setValue(k, date.getTime());
                } else {
                    dynamicBean.setValue(k, v);
                }
            } catch (Exception e) {
                logger.error("动态添加字段值出错", e);
            }
        });
        return dynamicBean.getTarget();
    }
}

