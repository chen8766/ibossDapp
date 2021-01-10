package com.chen.web.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author chen
 * @date 2021-01-10-20:08
 */
@Aspect
@Component
public class FeignInvokeAspect {
    
    public static final Logger logger = LoggerFactory.getLogger(FeignInvokeAspect.class);

    public static final ThreadLocal<Map<String, Long>> timeConsumedThreadLocal = ThreadLocal.withInitial(LinkedHashMap::new);

    public static final ThreadLocal<Boolean> isTimerStarted = ThreadLocal.withInitial(() -> false);

    @Pointcut("execution(public * com.chen.web.controller..*.*(..))")
    private void webInvoke() {
        // 定义切点
    }

    @Pointcut("execution(public * com.chen.web.eosapi..*.*(..))")
    private void feignInvoke() {
        // 定义切点
    }

    @Around("webInvoke()")
    public Object doWebInvokeAdvice(ProceedingJoinPoint pjp) {
        long start = 0L;
        long end = 0L;
        try {
            isTimerStarted.set(true);
            start = System.currentTimeMillis();
            Object obj = pjp.proceed();
            end = System.currentTimeMillis();
            return obj;
        } catch (Throwable throwable) {
            logger.error(throwable.getMessage(), throwable);
        } finally {
            Map<String, Long> timeConsumedMap = timeConsumedThreadLocal.get();
            Long sum = 0L;
            for (Map.Entry<String, Long> stringLongEntry : timeConsumedMap.entrySet()) {
                Long consumed = stringLongEntry.getValue();
                sum += consumed;
                logger.info("{}：{}ms", stringLongEntry.getKey(), consumed);
            }
            logger.info("===== feign invoke total: {}ms =====", sum);
            logger.info("{}：{}ms", getMethodName(pjp), end - start);
            logger.info("=============== end ================");
            timeConsumedThreadLocal.remove();
        }
        return null;
    }

    @Around("feignInvoke()")
    public Object doFeignInvokeAdvice(ProceedingJoinPoint pjp) throws Throwable {
        if (Boolean.FALSE.equals(isTimerStarted.get())) {
            return pjp.proceed();
        }

        long start = System.currentTimeMillis();
        Object obj = pjp.proceed();
        long end = System.currentTimeMillis();
        Map<String, Long> timeConsumedMap = timeConsumedThreadLocal.get();
        timeConsumedMap.put(getMethodName(pjp), end - start);
        return obj;
    }

    public String getMethodName(ProceedingJoinPoint pjp) {
        return pjp.getSignature().getDeclaringTypeName() + " " + pjp.getSignature().getName();
    }
}
