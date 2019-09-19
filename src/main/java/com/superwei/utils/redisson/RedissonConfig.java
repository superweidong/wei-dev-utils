package com.superwei.utils.redisson;

import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2019-09-19 17:11
 */
@Configuration
public class RedissonConfig {

    @Bean
    public RedissonLocker redissonLocker(RedissonClient redissonClient){

        RedissonLocker redissonLocker = new RedissonLocker(redissonClient);
        LockerUtil.setLocker(redissonLocker);

        return redissonLocker;
    }
}
