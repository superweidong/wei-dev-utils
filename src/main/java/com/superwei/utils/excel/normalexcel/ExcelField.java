package com.superwei.utils.excel.normalexcel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description:  excel字段
 * @auther: weidongge
 * @date: 2018/10/25 18:31
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelField {
    String name();
}
