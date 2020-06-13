package com.superwei.utils.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2020-03-20 11:58
 */
public class Test {

    static ConnectionPool pool = new ConnectionPool(10);
    static CountDownLatch start = new CountDownLatch(1);
    static CountDownLatch end;

    public static void main(String[] args) throws InterruptedException, IOException {


        int threadSize = 20;
        end = new CountDownLatch(threadSize);

        int count = 400;
        AtomicInteger getNum = new AtomicInteger();
        AtomicInteger notGetNum = new AtomicInteger();

        for (int i = 0; i < threadSize; i++) {
            new Thread(new PoolRunner(count, getNum, notGetNum),"testConnection"+i).start();
        }
        start.countDown();
        end.await();
        System.out.println("total get connection "+ threadSize * count);
        System.out.println("success get connection "+ getNum);
        System.out.println("fail get connection "+ notGetNum);


    }

    static class PoolRunner implements Runnable{

        private int count;
        private AtomicInteger getNum;
        private AtomicInteger notGetNum;

        public PoolRunner(int count, AtomicInteger getNum, AtomicInteger notGetNum) {
            this.count = count;
            this.getNum = getNum;
            this.notGetNum = notGetNum;
        }

        @Override
        public void run() {

            try {
                start.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (count > 0){
                Connection connection;
                try {
                    connection = pool.fetchConnection(1000);
                    if (Objects.nonNull(connection)){
                        try {
                            connection.createStatement();
                            connection.commit();
                        }finally {

                            pool.releaseConnection(connection);
                            getNum.incrementAndGet();
                        }

                    }else {
                        notGetNum.incrementAndGet();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    count--;
                }

            }
            end.countDown();
        }
    }


}

