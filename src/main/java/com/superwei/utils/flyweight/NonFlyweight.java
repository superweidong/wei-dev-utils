package com.superwei.utils.flyweight;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description 非享元
 * @create 2019-11-06 13:05
 */
public class NonFlyweight {

    private String info;

    public NonFlyweight(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
