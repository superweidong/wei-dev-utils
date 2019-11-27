package com.superwei.utils.reflect.rpcdemo.client;

import com.superwei.utils.reflect.rpcdemo.server.ITestService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2019-11-11 11:36
 */
public class ProxyFactory {

    public static <T> T create(Class<T> clazz, String ip, int port){

        InvocationHandler handler = new RpcProxy(ip, port, clazz);

        return (T)Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, handler);
    }

    public static void main(String[] args) {
        ITestService localhost = ProxyFactory.create(ITestService.class, "localhost", 9001);
        String result = localhost.sayHello("小明");
        System.out.println(result);
    }

}
