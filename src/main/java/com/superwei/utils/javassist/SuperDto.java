package com.superwei.utils.javassist;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author weidongge
 * @date 2022-02-24 15:27
 */
@Data
public class SuperDto {

    @ExcelProperty("名称")
    private java.lang.String name;

    @ExcelProperty("年龄")
    private Integer age;

    @ExcelProperty("金额")
    private java.math.BigDecimal amount;

}
