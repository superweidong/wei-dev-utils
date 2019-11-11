package com.superwei.utils.designmodel.flyweight;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description 享元工厂
 * @create 2019-11-06 13:09
 */
public class FlyWeightFactory {

    private static Map<String, FlyWeight>  flyWeightMap = new HashMap<>();

    public static FlyWeight getFlyweight(String key){
        FlyWeight flyWeight;
        if (Objects.isNull(flyWeightMap.get(key))){
            flyWeight = new RealFlyWeight(key);
            flyWeightMap.put(key, flyWeight);
        }else{
            flyWeight = flyWeightMap.get(key);
        }
        return flyWeight;
    }


    public static void main(String[] args) {
        NonFlyweight nonFlyweight = new NonFlyweight("非享元");

        FlyWeight flyWeight = FlyWeightFactory.getFlyweight("a");
        flyWeight.pattern(nonFlyweight);

    }
}
