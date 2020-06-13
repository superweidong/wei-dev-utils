package com.superwei.utils.mapstruct;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2020-06-13 14:35
 */
@Data
@Accessors(chain = true)
public class CarVo {

    private int id;
    private String voName;
    private byte voType;
    private Long money;
    private Date date;
}
