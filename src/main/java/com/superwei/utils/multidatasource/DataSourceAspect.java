package com.superwei.utils.multidatasource;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2019-11-27 19:03
 */
@Aspect
@Order(1)
@Component
@Slf4j
public class DataSourceAspect {

    @Pointcut("@annotation(com.wlfu.common.annotation.DataSource)")
    public void dsPointCut() {

    }

    @Around("dsPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Class<?> aClass = point.getTarget().getClass();
        Class<?>[] interfaces = aClass.getInterfaces();

        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        DataSource dataSource = method.getAnnotation(DataSource.class);
        if (dataSource != null) {
            DynamicDataSourceHolder.setDataSourceType(dataSource.value().name());
        }else if(Objects.nonNull(aClass.getAnnotation(DataSource.class))){
            dataSource = aClass.getAnnotation(DataSource.class);
            DynamicDataSourceHolder.setDataSourceType(dataSource.value().name());
        }else if (interfaces.length>0 && Objects.nonNull(interfaces[0].getAnnotation(DataSource.class))){
            dataSource = interfaces[0].getAnnotation(DataSource.class);
            DynamicDataSourceHolder.setDataSourceType(dataSource.value().name());
        }else{
            DynamicDataSourceHolder.setDataSourceType(DataSource.class.getName());
        }
        try {
            return point.proceed();
        } finally {
            // 销毁数据源 在执行方法之后
            DynamicDataSourceHolder.removeDataSourceType();
        }
    }
}


