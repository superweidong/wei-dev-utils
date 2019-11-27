package com.superwei.utils.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2019-11-11 10:10
 */
public class TestReflect {

    public static void main(String[] args) throws Exception{
        //getContruct();
        //getContruct2();
       // getField();
        getMethod();
    }

    static void getContruct() throws Exception{
        Class<?> clazz = Class.forName("com.superwei.utils.reflect.Book");

        Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
        for (Constructor<?> declaredConstructor : declaredConstructors) {
            Class<?>[] parameterTypes = declaredConstructor.getParameterTypes();
            for (Class<?> parameterType : parameterTypes) {
                System.out.println(parameterType);
            }
        }

    }

    static void getContruct2() throws Exception{
        Class<?> clazz = Class.forName("com.superwei.utils.reflect.Book");

        Constructor<?> declaredConstructor = clazz.getDeclaredConstructor(String.class, String.class);
        Object o = declaredConstructor.newInstance("java", "wei");
        Book book = (Book)o;
        System.out.println(book.toString());
    }
    static void getField() throws Exception{
        Class<?> clazz = Class.forName("com.superwei.utils.reflect.Book");

        Object o = clazz.newInstance();

        Field tag = clazz.getDeclaredField("TAG");
        tag.setAccessible(true);
        String name = tag.getName();
        System.out.println(name);

        String o1 = (String)tag.get(o);
        System.out.println(o1);
    }
    static void getMethod() throws Exception{
        Class<?> clazz = Class.forName("com.superwei.utils.reflect.Book");

        Object o = clazz.newInstance();

        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            System.out.println(declaredMethod.getName());
        }


        Method getNum = clazz.getDeclaredMethod("getNum", int.class);
        getNum.setAccessible(true);
        Object invoke = getNum.invoke(o, 1);
        System.out.println(invoke);
    }
}
