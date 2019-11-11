package com.superwei.utils.template;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2019-11-08 11:25
 */
public class RealHook extends AbstractHook {

    @Override
    void abstractMethod1() {
        System.out.println("real 1");
    }

    @Override
    void abstractMethod2() {
        System.out.println("real 2");
    }

    @Override
    boolean hookMethod2() {
        return true;
    }

    @Override
    void hookMethod1() {
        System.out.println("hook1");
    }

    public static void main(String[] args) {
        AbstractHook abstractHook = new RealHook();
        abstractHook.templateMethod();
    }
}
