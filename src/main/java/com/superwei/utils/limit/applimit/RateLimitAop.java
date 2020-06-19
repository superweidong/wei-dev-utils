package com.superwei.utils.limit.applimit;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author weidongge
 * @program shebao-fenhao
 * @description
 * @create 2020-06-19 15:38
 */
@Component
@Scope
@Aspect
@Slf4j
public class RateLimitAop {

    @Autowired
    private HttpServletResponse response;

    private RateLimiter rateLimiter = RateLimiter.create(5.0);

    /**
     * 比如说，我这里设置"并发数"为5
     */
    @Pointcut("@annotation(com.superwei.utils.limit.applimit.RateLimitAspect)")
    public void serviceLimit() {

    }
    @Around("serviceLimit()")
    public Object around(ProceedingJoinPoint joinPoint) {
        Boolean flag = rateLimiter.tryAcquire();
        Object obj = null;
        try {
            if (flag) {
                obj = joinPoint.proceed();
            }else{
                String result = "错误";
                output(response, result);
            }
        } catch (Throwable e) {
            log.error("异常", e);
        }
        log.info("flag= {} obj= {}", flag, obj);
        return obj;
    }
    public void output(HttpServletResponse response, String msg) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(msg.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            log.error("异常", e);
        } finally {
            if (Objects.nonNull(outputStream)){
                outputStream.flush();
                outputStream.close();
            }
        }
    }


}
