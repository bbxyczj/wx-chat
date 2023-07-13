package com.talent.wxChat.aop;

import cn.hutool.json.JSONUtil;
import com.google.common.base.Stopwatch;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class LogInterceptor {

    private static final Logger log = LoggerFactory.getLogger(LogInterceptor.class);
    private static final ThreadLocal<Stopwatch> STOP_WATCH_INFO = new ThreadLocal<>();

    @Pointcut("execution(public * com.talent.wxChat.controller..*(..))")
    public void log(){}

    @AfterReturning(returning = "object", pointcut = "log()")
    public void after(JoinPoint joinPoint, Object object) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        log.info("{}方法返回参数：{} 耗时：{}ms", method.getName(), JSONUtil.toJsonStr(object),
                STOP_WATCH_INFO.get().elapsed(TimeUnit.MILLISECONDS));
    }

    @Before("log()")
    public void before(JoinPoint joinPoint) {
        if (STOP_WATCH_INFO.get() != null) {
            // 异常情况不走after
            STOP_WATCH_INFO.remove();
        }
        STOP_WATCH_INFO.set(Stopwatch.createStarted());
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object object = null;
        if (null != joinPoint.getArgs() && joinPoint.getArgs().length > 0) {
            object = joinPoint.getArgs()[0];
        }
        log.info("{}方法请求参数：{}", method.getName(), JSONUtil.toJsonStr(object));

    }

}
