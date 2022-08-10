package com.superwei.utils.idempotent;

import java.lang.annotation.*;

/**
 * 接口幂等设置注解
 *
 * @author weidongge
 * @since 2022-08-05 10:21
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Idempotent {
    /**
     * 幂等时间间隔（秒） 默认3秒 可根据业务调整
     */
    int expired() default 3;

    /**
     * 参数中需要在md5中排除的属性 默认为空
     */
    String[] excludeKeys() default {};

    /**
     * 缓存key前缀 可根据项目区分
     */
    String keyPrefix() default "";
}
