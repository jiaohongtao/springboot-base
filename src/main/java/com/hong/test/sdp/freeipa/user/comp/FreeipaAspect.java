package com.hong.test.sdp.freeipa.user.comp;

import com.hong.test.sdp.freeipa.user.util.FreeipaClientCompo;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * 拦截 FreeipaClientCompo 类中需要初始化的方法
 * 暂不使用，因为初始化是的参数 集群ID 为动态值，不能做前置初始化
 *
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2023/09/19
 */
@Aspect
@Component
public class FreeipaAspect {

    @Resource
    private FreeipaClientCompo freeipaClient;

    // 拦截 FreeipaClientCompo 类 且 不带有 IgnoreInit 注解的方法
//    @Before("execution(* com.hong.test.sdp.freeipa.user.util.FreeipaClientCompo.*(..))" +
//            " && !@annotation(com.hong.test.sdp.freeipa.user.entity.IgnoreInit)")
//    public void beforeMethodExecution() throws IOException, URISyntaxException {
//        System.out.println("初始化Freeipa");
//        freeipaClient.init();
//    }
}
