package com.hong;

import com.hong.bean.Person;
import com.hong.util.common.PropertiesUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author jiaohongtao
 * @version 1.0
 * @since 2020年11月05日
 */
// @SpringBootTest
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Test1 {

    @Autowired
    PropertiesUtil propertiesUtil;

    @Autowired
    Person person;

    @Test
    public void test1() {
        propertiesUtil.getName();
        System.out.println(person);
    }
}
