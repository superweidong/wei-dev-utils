package com.superwei.utils.db;

import lombok.Data;

import java.io.Serializable;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2019-11-21 15:07
 */
@Data
public class Person implements Serializable {
    private int id;
    private String name;
    private String address;
}
