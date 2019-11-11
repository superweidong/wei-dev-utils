package com.superwei.utils.designmodel.abstractfactory;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2019-11-01 10:41
 */
public class AbstraceFactoryTest {


    public static void main(String[] args) throws Exception{
        Class<?> oneFramer = Class.forName("com.superwei.utils.designmodel.abstractfactory.OneFramer");
        Framer o = (Framer) oneFramer.newInstance();
        o.getAnimal();
        o.getVegetable();
    }
}
