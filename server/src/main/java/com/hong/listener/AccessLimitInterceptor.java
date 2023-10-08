//package com.hong.listener;
//
//import cn.hutool.json.JSONUtil;
//import com.hong.annotation.AccessLimit;
//import com.hong.bean.Result;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Component;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.concurrent.TimeUnit;
//
///**
// * 拦截恶意多次请求接口
// * href: https://onestar.newstar.net.cn/blog/73
// *
// * @author jiaohongtao
// * @version 1.0.0
// * @since 2022/02/25
// */
//@Slf4j
//@Component
//public class AccessLimitInterceptor implements HandlerInterceptor {
//    @Autowired
//    private RedisTemplate<String, String> redisTemplate;
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        // 判断请求是否属于方法的请求
//        if (handler instanceof HandlerMethod) {
//            HandlerMethod hm = (HandlerMethod) handler;
//            // 获取方法中的注解,看是否有该注解
//            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
//            if (null == accessLimit) {
//                return true;
//            }
//
//            // 指定时间内
//            long seconds = accessLimit.seconds();
//            // 允许请求次数
//            int maxCount = accessLimit.maxCnt();
//
//            String key = request.getServletPath() + ":" + request.getRemoteAddr();
//
//            // 从redis中获取用户访问的次数
//            String count = redisTemplate.opsForValue().get(key);
//            int countInt = StringUtils.isBlank(count) ? 0 : Integer.parseInt(count);
//            log.info("记录数为：{}", countInt);
//            if (countInt == 0) {
//                log.info("第一次访问");
//                redisTemplate.opsForValue().set(key, "1", seconds, TimeUnit.SECONDS);
//                return true;
//            }
//
//            if (countInt < maxCount) {
//                // 访问次数加一
//                countInt++;
//                redisTemplate.opsForValue().set(key, String.valueOf(countInt), 0);
//                return true;
//            }
//
//            // 超出访问次数
//            // if (countInt >= maxCount) {
//            // response 返回 json 请求过于频繁请稍后再试
//            // 设置次数访问过多时，不可访问的时长 1 分钟
//            redisTemplate.opsForValue().set(key, String.valueOf(countInt), 30, TimeUnit.SECONDS);
//            response.setCharacterEncoding("UTF-8");
//            response.setContentType("application/json; charset=utf-8");
//            response.getWriter().write(JSONUtil.toJsonStr(Result.failed("请求过于频繁")));
//            return false;
//            // }
//        }
//        return true;
//    }
//}
