package com.hong.controller;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2022/09/06
 */
@Slf4j
@RestController
@RequestMapping("/redis")
public class RedissonController {

    @Autowired
    RedissonClient redissonClient;

    private final ReentrantLock reentrantLock = new ReentrantLock();

    @GetMapping("{id}")
    public Boolean getData(@PathVariable String id) {
        RLock lock = redissonClient.getLock(id);

        boolean tryLock;
        try {
            tryLock = lock.tryLock(2L, 2L, TimeUnit.SECONDS);
            // tryLock = lock.tryLock(2L, TimeUnit.SECONDS);
            if (tryLock) {
                log.info("获取锁成功，等待获取数据...");
                TimeUnit.SECONDS.sleep(10);
                log.info("获取数据到数据");
                return true;
            } else {
                log.info("获取锁失败");
                return false;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return false;
    }

    @GetMapping("get2/{id}")
    public Boolean getData2(@PathVariable String id) {
        RLock lock = redissonClient.getLock(id);

        try {
            boolean tryLock = lock.tryLock(2L, 2L, TimeUnit.SECONDS);
            if (tryLock) {
                log.info("获得了锁");
                TimeUnit.SECONDS.sleep(10);
            }
            return true;
        } catch (InterruptedException e) {
            log.error("获取锁失败，", e);
            return false;
        } finally {
            log.info("准备释放锁");
            lock.unlock();
        }
    }

    @GetMapping("get3/{id}")
    public Boolean getData3(@PathVariable String id) {
        try {
            boolean tryLock = reentrantLock.tryLock(2L, TimeUnit.SECONDS);
            if (tryLock) {
                log.info("获取锁成功");
                TimeUnit.SECONDS.sleep(5L);
            } else {
                log.info("获取锁失败");
            }
            return true;
        } catch (InterruptedException e) {
            log.error("获取锁失败，", e);
            return false;
        } finally {
            if (reentrantLock.isLocked()) {
                log.info("释放锁");
                reentrantLock.unlock();
            }
        }
    }
}
