package com.superwei.utils.designmodel.template;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2019-11-08 11:22
 */
public class AbstractHook {

    void templateMethod(){
        abstractMethod1();
        hookMethod1();
        if (hookMethod2()){
            specialMethod();
        }
        abstractMethod2();
    };

    void specialMethod(){
        System.out.println("抽象方法中的具体方法");
    }

    void hookMethod1(){};
    boolean hookMethod2(){
        return true;
    };

    void abstractMethod1(){};
    void abstractMethod2(){};
}
