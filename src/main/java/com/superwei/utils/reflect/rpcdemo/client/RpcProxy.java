package com.superwei.utils.reflect.rpcdemo.client;

import com.superwei.utils.reflect.rpcdemo.server.RpcObject;
import lombok.extern.slf4j.Slf4j;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Objects;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2019-11-11 11:28
 */
@Slf4j
public class RpcProxy implements InvocationHandler, Serializable {

    private String ip;
    private int port;
    private Class<?> aClass;

    public RpcProxy(String ip, int port, Class aClass) {
        this.ip = ip;
        this.port = port;
        this.aClass = aClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Object obj = null;
        Socket socket = new Socket(ip,port);

        RpcObject rpcObject = new RpcObject(aClass, method.getName(), args);

        ObjectInputStream is = null;
        ObjectOutputStream os = null;

        try{
            os = new ObjectOutputStream(socket.getOutputStream());
            os.writeObject(rpcObject);
            os.flush();
            is = new ObjectInputStream(socket.getInputStream());
            obj = is.readObject();
        }catch (Exception e){
            log.error("rpc proxy error", e);
        }finally {
            if (Objects.nonNull(is)){
                is.close();
            }
            if (Objects.nonNull(os)){
                os.close();
            }
        }
        return obj;
    }
}
