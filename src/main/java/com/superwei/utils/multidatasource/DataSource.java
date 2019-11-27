package com.superwei.utils.multidatasource;


import java.lang.annotation.*;

/**
 * @author weidongge
 * @date: 2019/11/27
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {

    DataSourceType value() default DataSourceType.MASTER;
}
