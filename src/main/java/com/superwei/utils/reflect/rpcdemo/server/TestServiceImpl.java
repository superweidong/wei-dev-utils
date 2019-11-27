package com.superwei.utils.reflect.rpcdemo.server;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2019-11-11 10:43
 */
public class TestServiceImpl implements ITestService {
    @Override
    public String sayHello(String name) {

        System.out.println("hello "+name);
        return "hello "+name;
    }
}
