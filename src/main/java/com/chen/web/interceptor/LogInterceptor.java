package com.chen.web.interceptor;

import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author chen
 * @date 2020-12-29-22:40
 */
public class LogInterceptor implements HandlerInterceptor {

    public static final String TRACE_ID = "traceId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果有上层调用就用上层的ID
        String traceId = request.getHeader(TRACE_ID);
        if (traceId == null) {
            traceId = getTraceId();
        }

        MDC.put("traceId", traceId);
        return true;
    }

    private String getTraceId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        // Do nothing because of no business
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //调用结束后删除
        MDC.remove(TRACE_ID);
    }
}
