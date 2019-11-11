package com.superwei.utils.designmodel.single;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2019-11-01 10:54
 */
public class Singleton {
    private static final Singleton singleton = new Singleton();
    private Singleton() {
    }

    public static Singleton getInstance(){
        return singleton;
    }

    public static void main(String[] args) {
        Singleton instance = Singleton.getInstance();

        System.out.println(instance);
    }
}



