package com.superwei.utils.excel.normalexcel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: excelsheet
 * @auther: weidongge
 * @date: 2018/10/25 18:32
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelSheet {
    String name();
}
