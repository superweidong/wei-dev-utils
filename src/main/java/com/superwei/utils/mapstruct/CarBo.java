package com.superwei.utils.mapstruct;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2020-06-13 14:35
 */
@Data
@Accessors(chain = true)
public class CarBo {

    private int id;
    private String name;
    private byte type;
    private BigDecimal money;
    private String date;
}
