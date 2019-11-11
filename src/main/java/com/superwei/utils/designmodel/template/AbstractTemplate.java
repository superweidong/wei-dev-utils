package com.superwei.utils.designmodel.template;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2019-11-08 11:15
 */
abstract class AbstractTemplate {

    void templateMethod(){

        realMethod();
        abstractMethod1();
        abstractMethod2();
    };

    void realMethod(){
        System.out.println("抽象方法中的真实方法");
    };

    void abstractMethod1(){};
    void abstractMethod2(){};
}
