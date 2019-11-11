package com.superwei.utils.designmodel.abstractfactory;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2019-11-01 10:36
 */
public class TwoFramer implements Framer {

    @Override
    public Animal getAnimal() {
        System.out.println("生成一个猫");
        return new Cat();
    }

    @Override
    public Vegetable getVegetable() {
        System.out.println("生成一个白菜");
        return new BaiCai();
    }
}
