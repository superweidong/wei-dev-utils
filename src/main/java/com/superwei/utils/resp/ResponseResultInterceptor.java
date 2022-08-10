package com.superwei.utils.resp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author weidongge
 */
@Slf4j
@Component
public class ResponseResultInterceptor implements HandlerInterceptor {

    public static final String RESPONSE_RESULT_SIGN = "RESPONSE_RESULT_SIGN";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod){
            final HandlerMethod handlerMethod = (HandlerMethod) handler;
            Class<?> beanType = handlerMethod.getBeanType();
            Method method = handlerMethod.getMethod();
            //判断类上是否有注解
            if (beanType.isAnnotationPresent(ResponseResult.class)){
                log.info(RESPONSE_RESULT_SIGN + "bean annotation {} method {}", beanType, method);
                request.setAttribute(RESPONSE_RESULT_SIGN, beanType.getAnnotation(ResponseResult.class));
            }
            if (method.isAnnotationPresent(ResponseResult.class)){
                log.info(RESPONSE_RESULT_SIGN + "bean {} method annotation {}", beanType, method);
                request.setAttribute(RESPONSE_RESULT_SIGN, method.getAnnotation(ResponseResult.class));
            }
        }
        return true;
    }
}
