package com.superwei.utils.flyweight;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description 具体享元
 * @create 2019-11-06 13:06
 */
public class RealFlyWeight implements FlyWeight {

    private String key;

    public RealFlyWeight(String key) {
        this.key = key;
    }

    @Override
    public void pattern(NonFlyweight nonFlyweight) {

        System.out.println("享元");

        System.out.println(nonFlyweight.getInfo());
    }
}
