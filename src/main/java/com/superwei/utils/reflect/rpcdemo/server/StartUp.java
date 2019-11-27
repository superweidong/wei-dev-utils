package com.superwei.utils.reflect.rpcdemo.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2019-11-11 11:22
 */
public class StartUp {

    private static int port = 9001;

    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(port);

        while (true){
            Socket accept = serverSocket.accept();
            if (Objects.nonNull(accept)){
                new RpcThread(accept).start();
            }
        }
    }


}
