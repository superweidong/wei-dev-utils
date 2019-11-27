package com.superwei.utils.reflect.rpcdemo.server;

import lombok.Data;

import java.io.Serializable;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
* @create 2019-11-11 10:39
 */

@Data
public class RpcObject implements Serializable {

     private Class<?> c;
     private String methodName;
     private Object[] args;

     public RpcObject(Class<?> c, String methodName, Object[] args) {
         this.c = c;
         this.methodName = methodName;
         this.args = args;
     }
}
