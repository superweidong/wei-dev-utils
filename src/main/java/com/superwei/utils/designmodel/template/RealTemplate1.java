package com.superwei.utils.designmodel.template;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2019-11-08 11:17
 */
public class RealTemplate1 extends AbstractTemplate {

    @Override
    void abstractMethod1() {
        System.out.println("实现抽象模板方法1");
    }
    @Override
    void abstractMethod2() {
        System.out.println("实现抽象模板方法2");
    }


    public static void main(String[] args) {
        AbstractTemplate abstractTemplate = new RealTemplate1();
        abstractTemplate.templateMethod();
    }
}
