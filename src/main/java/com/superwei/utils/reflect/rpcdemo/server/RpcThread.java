package com.superwei.utils.reflect.rpcdemo.server;

import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2019-11-11 11:04
 */
@Slf4j
public class RpcThread extends Thread {


    private Socket socket;

    public RpcThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        ObjectInputStream objectInputStream;
        ObjectOutputStream os;

        try {
            InputStream inputStream = socket.getInputStream();
            objectInputStream = new ObjectInputStream(inputStream);
            RpcObject o = (RpcObject) objectInputStream.readObject();
            System.out.println("method name is "+o.getMethodName());

            Object object = getObject(o.getC());
            System.out.println("class is "+o.getC());

            Object o1 = execMethod(o, o.getMethodName(), o.getArgs());

            os = new ObjectOutputStream(socket.getOutputStream());
            os.writeObject(o1);
            os.flush();

        }catch (Exception e){
            log.error("执行请求异常",e);
        }
    }

    private Object getObject(Class<?> clazz){
        Object o = null;
        try {
             System.out.println("名称 "+clazz.getName());
             System.out.println("注册工厂 "+ConfMonitor.configMap);
             o = ConfMonitor.configMap.get(clazz.getName()).newInstance();

        }catch (Exception e){
            log.error("获取对象异常",e);
        }
        return o;
    }

    private Object execMethod(Object object, String methodName, Object[] args){

        Class<?>[] aClass = new Class[args.length];

        for (int i = 0; i < args.length; i++) {
            aClass[i] = args[i].getClass();
        }

        Object invoke = null;
        try {
            Method method = object.getClass().getMethod(methodName, aClass);
            invoke = method.invoke(object, aClass);
        }catch (Exception e){
            log.error("执行方法异常",e);
        }

        return invoke;
    }
}
