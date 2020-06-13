package com.superwei.utils.controller;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.Objects;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2020-04-05 17:52
 */
public class ConnectionPool {

    private LinkedList<Connection> pool = new LinkedList<>();

    public ConnectionPool(int poolSize) {
        if (poolSize > 0){
            for (int i = 0; i < poolSize; i++) {
                pool.addLast(ConnectionDriver.createConnection());
            }
        }
    }

    public Connection fetchConnection(long time) throws InterruptedException {

        synchronized (pool){
            if (time <= 0){
                while (pool.size() <= 0){
                    pool.wait();
                }
                return pool.removeFirst();
            }else {
                long lastTime = System.currentTimeMillis() + time;
                long waitTime = time;
                while (pool.size() <= 0 && waitTime > 0){
                    pool.wait(waitTime);
                    waitTime = lastTime - System.currentTimeMillis();
                }
                Connection connection = null;
                if (!pool.isEmpty()){
                    connection = pool.removeFirst();
                }
                return connection;
            }
        }
    }

    public void releaseConnection(Connection connection){
        if (Objects.nonNull(connection)){

            synchronized (pool){
                pool.addLast(connection);
                pool.notifyAll();
            }
        }

    }
}
