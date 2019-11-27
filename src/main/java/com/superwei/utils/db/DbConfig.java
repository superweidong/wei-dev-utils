package com.superwei.utils.db;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2019-11-21 14:50
 */
@Configuration
public class DbConfig {


    @ConfigurationProperties(prefix="spring.datasource")
    @Bean("druidDataSource")
    public DruidDataSource druidDataSource() {
        return new DruidDataSource();
    }

    @Bean(name = "jdbcTemplate")
    public JdbcTemplate primaryJdbcTemplate(
            @Qualifier("druidDataSource") DruidDataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
