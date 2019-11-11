package com.superwei.utils.designmodel.single;

import java.util.Objects;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2019-11-01 10:56
 */
public class SingletonLazy {

    private static volatile  SingletonLazy singletonLazy;

    private SingletonLazy() {
    }

    public synchronized static SingletonLazy getInstance(){
        if (Objects.isNull(singletonLazy)){
            singletonLazy = new SingletonLazy();
        }
        return singletonLazy;
    }

    public static void main(String[] args) {
        SingletonLazy instance = SingletonLazy.getInstance();
        System.out.println(instance);
    }
}
