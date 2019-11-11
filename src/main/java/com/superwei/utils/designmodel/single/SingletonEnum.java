package com.superwei.utils.designmodel.single;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2019-11-01 11:33
 */
public enum SingletonEnum {

    INSTANCE;


    public static void main(String[] args) {
        SingletonEnum instance = SingletonEnum.INSTANCE;
        System.out.println(instance);
    }
}
