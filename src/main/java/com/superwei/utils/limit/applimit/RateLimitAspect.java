package com.superwei.utils.limit.applimit;

import java.lang.annotation.*;

/**
 * @author apple
 */

@Inherited
@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimitAspect {

}
