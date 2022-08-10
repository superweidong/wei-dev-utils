package com.superwei.utils.javassist;

import javassist.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class JavassistClassGenerator {

    public Class<?> createClass(String proxyClassPckName, String className, Class<?> interClazz) throws Exception {
        // ClassPool：CtClass对象的容器
        ClassPool pool = ClassPool.getDefault();
        // 通过ClassPool生成一个public新类
        CtClass ctClass = pool.makeClass(proxyClassPckName + "." + className);
        // 为类 添加接口
        ctClass.addInterface(pool.get(interClazz.getName()));
        // 添加 InvocationHandler 成员变量 => private InvocationHandler invocationHandler;
        String invocationHandlerName = InvocationHandler.class.getName();
        CtField enameField = new CtField(pool.getCtClass(invocationHandlerName), "invocationHandler", ctClass);
        enameField.setModifiers(Modifier.PRIVATE);
        ctClass.addField(enameField);

        // 添加构造函数 => this.invocationHandler = invocationHandler;
        CtConstructor cons = new CtConstructor(new CtClass[] { pool.get(invocationHandlerName) }, ctClass);
        // $0=this / $1,$2,$3... 代表方法参数
        cons.setBody("{$0.invocationHandler = $1;}");// this.invocationHandler = invocationHandler;
        ctClass.addConstructor(cons);
        // 添加方法 ,有多个，还包括 toStrng ，hashcode等Object的方法。
        for (Method method : interClazz.getDeclaredMethods()) {
            // 返回值
            CtClass returnType = pool.get(method.getReturnType().getName());
            if (hasObjectMethod(method)) {
                // 如果是Object里面的方法，就直接返回调用Object类的
                continue;
            }
            // 构造方法参数（根据接口method的参数来）
            CtClass[] parameters = convertParameters(pool, method.getParameterTypes());
            CtMethod ctMethod = new CtMethod(returnType, method.getName(), parameters, ctClass);
            // 添加方法的 代码
            ctMethod.setBody(getMethodBody(method, interClazz));
            // 添加方法
            ctClass.addMethod(ctMethod);
        }
        System.out.println("生成的class文件路径：" + this.getClass().getResource("").getPath());
        ctClass.writeFile(this.getClass().getResource("").getPath());
        return ctClass.toClass();
    }

    private String getMethodBody(Method method, Class<?> interClazz) {
        StringBuilder sb = new StringBuilder();
        sb.append("{").append("\r\n");
        // 获取客户端接口的 class
        sb.append("    java.lang.Class clazz = Class.forName(\"" + interClazz.getName() + "\");").append("\r\n");
        // 获取对应方法
        // java.lang.reflect.Method curMethod = interClazz.getDeclaredMethod("",new Class[] {});
        // 获取参数
        String typeNames = getParameterTypeNames(method.getParameterTypes());
        if(typeNames.equals("")) {
            //将方法参数 (int a,String b) 变成 int.class,String.class
            sb.append("    java.lang.reflect.Method curMethod = clazz.getDeclaredMethod(\"" + method.getName()
                    + "\", null);").append("\r\n");
        }else {
            sb.append("    java.lang.reflect.Method curMethod = clazz.getDeclaredMethod(\"" + method.getName()
                    + "\", new Class[] {" + typeNames + "});").append("\r\n");
        }
        sb.append("    invocationHandler.invoke(this,curMethod, $args);").append("\r\n");
        sb.append("}");
        System.out.println("生成的" + method.getName() +" 方法体如下:");
        System.out.println(sb.toString());
        return sb.toString();
    }

    // 将方法参数 (int a,String b) 变成 int.class,String.class
    private String getParameterTypeNames(Class<?>[] jdkParameters) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < jdkParameters.length; i++) {
            sb.append(jdkParameters[i].getName()).append(",");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1); // 删除最后一个字符 ,
        }
        return sb.toString();
    }

    // 将jdk的参数class数组。转成 Javassist的CtClass数组
    private CtClass[] convertParameters(ClassPool pool, Class<?>[] jdkParameters) throws NotFoundException {
        if (jdkParameters == null || jdkParameters.length == 0) {
            return null;
        }
        CtClass[] pCtClasses = new CtClass[jdkParameters.length];
        for (int i = 0; i < jdkParameters.length; i++) {
            pCtClasses[i] = pool.get(jdkParameters[i].getName());
        }
        return pCtClasses;
    }

    // 是不是Object里面的方法 :toStrng ，hashcode等
    private boolean hasObjectMethod(Method method) {
        for (Method objMethd : Object.class.getDeclaredMethods()) {
            if (method.getName().equals(objMethd.getName())) {
                return true;
            }
        }
        return false;
    }
}
