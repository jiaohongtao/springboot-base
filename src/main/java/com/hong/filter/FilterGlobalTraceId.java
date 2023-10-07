package com.hong.filter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

/**
 * 使用filter增加全局traceId，方便日志查找
 * https://blog.csdn.net/qq_36881106/article/details/132830193
 */
public class FilterGlobalTraceId implements Filter {

    private static final String TRACE_ID = "traceId";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初始化操作
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String oldTraceId = httpRequest.getHeader(TRACE_ID);

            if (StringUtils.isNotBlank(oldTraceId)) {
                // 生成唯一的traceId
                MDC.put(TRACE_ID, generateTraceId());
            } else {
                MDC.put(TRACE_ID, oldTraceId);
            }
            chain.doFilter(request, response);
        } finally {
            // 清除MDC的traceId值，确保在请求结束后不会影响其他请求的日志
            MDC.remove(TRACE_ID);
        }
    }

    @Override
    public void destroy() {
        // 清理操作
    }

    private String generateTraceId() {
        // 在此处生成唯一的traceId，并返回
        return UUID.randomUUID().toString();
    }
}