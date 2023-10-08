package com.hong.config;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置 http 也可访问系统
 *
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2022/06/08
 */
@Configuration
public class TomcatHttpsConfig {

    // 解决 https://stackoverflow.com/questions/53298008/spring-boot-2-1-0-management-server-port-on-different-port
    // 关闭 ssl 时需关闭该类
    @Bean
    public TomcatServletWebServerFactory tomcatServletWebServerFactory() {
        CustomTomcatServletWebServerFactory customsTomcatServletWebServerFactory = new CustomTomcatServletWebServerFactory();
        customsTomcatServletWebServerFactory.addAdditionalTomcatConnectors(myConnector());
        return customsTomcatServletWebServerFactory;
    }

    // 下面配置：当使用http协议的9091端口请求数据会转发到9090端口
    private Connector myConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setPort(9091);
        connector.setSecure(false);
        connector.setRedirectPort(9090);
        return connector;
    }

}

