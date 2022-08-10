package com.superwei.utils.idempotent;

import cn.hutool.core.util.CharUtil;
import com.alibaba.fastjson.JSON;
import com.superwei.utils.beancopy.BusinessException;
import com.superwei.utils.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author weidongge
 * @since 2022-08-05 10:21
 **/
@Aspect
@Component
@Slf4j
public class IdempotentAspect {

    /**
     * redis锁前缀
     **/
    public static final String KEY = "Idem|";
    /**
     * 毫秒转换
     **/
    public static final int SECOND = 1000;

    @Autowired
    private RedisUtil redisUtil;

    @Pointcut(value = "@annotation(com.superwei.utils.idempotent.Idempotent)")
    public void aspect() {
    }

    @Before("aspect()")
    public void handle(JoinPoint joinPoint) throws Exception {
        // 获取参数值
        Object[] args = joinPoint.getArgs();
        //获取注解的信息
        Idempotent idempotent = this.getDeclaredAnnotation(joinPoint);
        //构造缓存的key 此处使用的是方法路径 + 参数的hashcode 作为key，在个别情况下有可能会重复
        String paramsMd5 = Arrays.stream(args).map(JSON::toJSONString)
                .map(jsonStr -> IdempotentHelper.getMd5(jsonStr, idempotent.excludeKeys()))
                .collect(Collectors.joining(Character.toString(CharUtil.UNDERLINE)));
        String methodPath = joinPoint.getSignature().getDeclaringTypeName() + CharUtil.DOT + joinPoint.getSignature().getName();
        //key=前缀+方法路基+参数MD5串
        String redisKey = StringUtils.isEmpty(idempotent.keyPrefix()) ? KEY :  idempotent.keyPrefix() + methodPath + paramsMd5;
        //加锁
        if (!redisUtil.lock(redisKey, idempotent.expired() * SECOND)) {
            throw new BusinessException("操作过于频繁，请稍后再试！");
        }
    }

    /**
     * 获取方法中声明的注解
     */
    public Idempotent getDeclaredAnnotation(JoinPoint joinPoint) throws NoSuchMethodException {
        // 拿到方法对应的参数类型
        Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
        // 根据类、方法、参数类型（重载）获取到方法的具体信息
        Method objMethod = joinPoint.getTarget().getClass().getMethod(joinPoint.getSignature().getName(), parameterTypes);
        // 拿到方法定义的注解信息
        return objMethod.getDeclaredAnnotation(Idempotent.class);
    }
}

