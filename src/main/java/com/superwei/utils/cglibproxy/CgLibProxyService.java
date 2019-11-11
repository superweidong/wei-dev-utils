package com.superwei.utils.cglibproxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author weidongge
 * @program design-model
 * @description
 * @create 2019-11-05 16:56
 */
public class CgLibProxyService implements MethodInterceptor {


    private Object object;

    private Object getInstance(Object object){
        this.object = object;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.object.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        before();
            Object invoke = methodProxy.invokeSuper(o, objects);
        after();
        return invoke;
    }

    private void before(){
        System.out.println("bfore");
    }
    private void after(){
        System.out.println("after");
    }


    public static void main(String[] args) {
        CgLibProxyService cgLibProxyService = new CgLibProxyService();
        TargetServiceImpl instance = (TargetServiceImpl)cgLibProxyService.getInstance(new TargetServiceImpl());
        instance.update();

    }
}
