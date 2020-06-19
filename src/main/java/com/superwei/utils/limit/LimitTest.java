package com.superwei.utils.limit;

import com.superwei.utils.limit.dislimit.AccessLimit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2020-06-19 16:13
 */
@RestController
@Slf4j
public class LimitTest {


    private final Semaphore permit = new Semaphore(10, true);

    /**
     * 使用信号量限流
     * 最高10个请求处理  其他的等待
     */
    @RequestMapping(value = "testSemaphore", method = RequestMethod.POST)
    public void testSemaphore() {
        try {
            permit.acquire();
            log.info("接收请求。。。。。。。 开始");
            TimeUnit.SECONDS.sleep(3);
            log.info("接收请求。。。。。。。  处理完成");
        } catch (InterruptedException e) {
            log.error("异常", e);
        }finally {
            log.info("接收请求。。。。。。。   释放");
            permit.release();
        }
    }


    @ResponseBody
    @RequestMapping("/seckill")
    @AccessLimit(limit = 4,sec = 10)  //加上自定义注解即可
    public String test (HttpServletRequest request, @RequestParam(value = "username",required = false) String userName){
        //TODO somethings……
        return   "hello world !";
    }


}
