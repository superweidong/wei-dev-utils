package com.superwei.utils.cache;

import com.superwei.utils.redis.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

/**
 * RedisCacheConfiguration.
 * @author zhaoyameng
 * @Date 2019年06月18日
 */

//@Configuration
public class StartRedisCacheConfiguration {
    /**
     * RedisCacheConfiguration.
     *
     * @author weidongge
     */
    private Logger logger = LoggerFactory.getLogger(StartRedisCacheConfiguration.class);

    /**
     * host.
     */
    @Value("${redis.host}")
    private String host;
    /**
     * host.
     */
    @Value("${redis.port}")
    private int port;

    @Value("${redis.timeout}")
    private int timeout;

    /**
     * host.
     */
    @Value("${redis.maxIdle}")
    private int maxIdle;
    /**
     * host.
     */
    @Value("${redis.database}")
    private int database;

    @Value("${redis.maxTotal}")
    private int redisMaxTotal;

    @Value("${redis.minIdle}")
    private int redisMinIdle;

    @Value("${redis.maxWaitMillis}")
    private int maxWaitMillis;

    @Value("${redis.testOnBorrow}")
    private Boolean redisTestOnBorrow;

    @Value("${redis.testOnReturn}")
    private Boolean testOnReturn;

    @Value("${redis.testWhileIdle}")
    private Boolean testWhileIdle;

    @Value("${redis.password}")
    private String password;


    public JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(redisMaxTotal);
        jedisPoolConfig.setMaxIdle(redisMinIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        jedisPoolConfig.setTestOnBorrow(redisTestOnBorrow);
        jedisPoolConfig.setTestOnReturn(testOnReturn);
        jedisPoolConfig.setTestWhileIdle(testWhileIdle);
        return jedisPoolConfig;
    }

    @Bean(name = "jedisPool")
    public JedisPool jedisPool(){
        password = StringUtils.isNotEmpty(password)?password:null;
        JedisPool jedisPool = new JedisPool(this.jedisPoolConfig(), host, port, timeout, password, database);
        logger.debug("password:{},host:{},timeout:{},database:{}",password,host,timeout,database);
        return jedisPool;

    }

    @Bean("redisConnectionFactory")
    public RedisConnectionFactory connectionFactory() {
        JedisPoolConfig poolConfig = jedisPoolConfig();
        JedisClientConfiguration clientConfig = JedisClientConfiguration.builder()
                .usePooling().poolConfig(poolConfig).and().readTimeout(Duration.ofMillis(timeout)).build();

        // 单点redis
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        // 哨兵redis
        // RedisSentinelConfiguration redisConfig = new RedisSentinelConfiguration();
        // 集群redis
        // RedisClusterConfiguration redisConfig = new RedisClusterConfiguration();
        redisConfig.setHostName(host);
        //redisConfig.setPassword(RedisPassword.of(redisAuth));
        redisConfig.setPort(port);
        redisConfig.setDatabase(database);

        return new JedisConnectionFactory(redisConfig,clientConfig);
    }



    /**
     * RedisUtil.
     * @return redisUtil redisUtil.
     */
    @Bean
    public RedisUtil redisUtil(@Qualifier("jedisPool") JedisPool jedisPool) {
        RedisUtil redisUtil = new RedisUtil();
        redisUtil.setJedisPool(jedisPool);
        logger.info(">>>>>>>>>>>>>>>>>>JedisPool注入RedisUtil成功！！");
        return redisUtil;
    }
}
