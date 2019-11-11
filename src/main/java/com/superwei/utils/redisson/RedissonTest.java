package com.superwei.utils.redisson;

import org.redisson.api.RAtomicDouble;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.*;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2019-09-19 17:20
 */
@RestController
public class RedissonTest {

    static final String KEY = "LOCK_KEY";

    @Autowired
    private RedissonClient redissonClient;

    @GetMapping("/test")
    public Object test(){
        //加锁
        LockerUtil.lock(KEY);
        try {
            //TODO 处理业务
            System.out.println(" 处理业务。。。");
            TimeUnit.SECONDS.sleep(100);
        } catch (Exception e) {
            //异常处理
        }finally{
            //释放锁
            LockerUtil.unlock(KEY);
        }

        return "SUCCESS";
    }
    @GetMapping("/test2")
    public Object test2(){

        boolean b = LockerUtil.tryLock(KEY);
        if (b){
            //加锁
            LockerUtil.lock(KEY);
            try {
                //TODO 处理业务
                System.out.println(" 处理业务。。。");
            } catch (Exception e) {
                //异常处理
            }finally{
                //释放锁
                LockerUtil.unlock(KEY);
            }
            return "SUCCESS";
        }else{
            return "FAIL";
        }
    }
    @GetMapping("/test3")
    public void test3(){
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (int i=0;i<4;i++){
            executorService.submit(new Runnable() {
                @Override
                public void run() {

                    RAtomicDouble redissonClientAtomicDouble = redissonClient.getAtomicDouble("redisson_test_key");
                    for (int i=0;i<250;i++){
                        double andDecrement = redissonClientAtomicDouble.getAndDecrement();
                        System.out.println("线程名 "+Thread.currentThread().getName()+"  减一修改之后的值 "+andDecrement);
                    }
                }
            });
        }
    }





}
