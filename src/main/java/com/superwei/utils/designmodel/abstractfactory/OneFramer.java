package com.superwei.utils.designmodel.abstractfactory;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2019-11-01 10:35
 */
public class OneFramer implements Framer {

    @Override
    public Animal getAnimal() {
        System.out.println("生成一个马");
        return new Hourse();
    }

    @Override
    public Vegetable getVegetable() {
        System.out.println("生成一个甘蓝");
        return new GanLan();
    }
}
