package com.hong;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 布隆过滤器测试
 * https://blog.csdn.net/jifashihan/article/details/130922000
 *
 * @author jiaohongtao
 * @version 1.0
 * @since 2023年07月14日
 */
// @SpringBootTest
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GuavaBloomFilterControllerTest {


    @Autowired
    GuavaBloomFilterController guavaBloomFilterController;

    @Test
    public void test() {
        guavaBloomFilterController.guavaBloomFilter();
    }
}
