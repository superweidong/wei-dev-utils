package com.superwei.utils.designmodel.prototype;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description 原型模式
 * @create 2019-10-31 17:00
 */
public class Prototype {


    public static void main(String[] args) throws Exception{
        PrototypeV1 prototypeV1 = new PrototypeV1();
        PrototypeV1 clone = (PrototypeV1)prototypeV1.clone();

        Class<? extends PrototypeV1> aClass = prototypeV1.getClass();
        boolean equals = prototypeV1.getClass().equals(clone.getClass());

        System.out.println(prototypeV1);
        System.out.println(clone);
        System.out.println(equals);
    }


    static class PrototypeV1 implements Cloneable{

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }




}
