package com.superwei.utils.redisson;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2019-09-19 17:20
 */
@RestController
public class RedissonTest {

    static final String KEY = "LOCK_KEY";

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



}
