package com.hong;

import com.hong.util.common.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * @author jiaohongtao
 * @version 1.0
 * @since 2020年11月05日
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RedisTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    /*@Autowired
    private RedisUtil redisUtil;*/

    @Test
    public void testRedis() {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();

        valueOperations.set("a", "b", 30, TimeUnit.SECONDS);
        /*// Object abcd = redisUtil.get("abcd");
        System.out.println(abcd);*/
        System.out.println(valueOperations.get("a"));
        Long time = redisTemplate.getExpire("a");
        System.out.println(time);
    }
}
