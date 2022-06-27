package com.hong.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;

/**
 * 开启 actuator ssl时，需重写 TomcatServletWebServerFactory，为配置 http 重定向 https 用
 *
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2022/06/27
 */
public class CustomTomcatServletWebServerFactory extends TomcatServletWebServerFactory {
    @Override
    protected void postProcessContext(Context context) {
        SecurityConstraint securityConstraint = new SecurityConstraint();
        securityConstraint.setUserConstraint("CONFIDENTIAL");
        SecurityCollection securityCollection = new SecurityCollection();
        securityCollection.addPattern("/*");
        securityConstraint.addCollection(securityCollection);
        context.addConstraint(securityConstraint);
    }

    @Override
    protected void customizeConnector(Connector connector) {
        int maxSize = 50000000;
        int maxHeaderSize = 8192;
        super.customizeConnector(connector);
        connector.setMaxPostSize(maxSize);
        connector.setMaxSavePostSize(maxSize);
        if (connector.getProtocolHandler() instanceof AbstractHttp11Protocol) {
            AbstractHttp11Protocol<?> protocolHandler = (AbstractHttp11Protocol<?>) connector.getProtocolHandler();
            protocolHandler.setMaxSwallowSize(maxSize);
            protocolHandler.setMaxHttpHeaderSize(maxHeaderSize);
        }
    }
}
