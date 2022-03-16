package com.superwei.utils.lb.impl;

import com.superwei.utils.lb.LoadBalanceStrategy;
import com.superwei.utils.lb.ServiceProvider;

import java.util.List;
import java.util.Random;

/**
 * 随机算法
 * @author weidongge
 * @date 2022-03-14 16:33
 */
public class RandomStrategy implements LoadBalanceStrategy {

    @Override
    public ServiceProvider select(List<ServiceProvider> configs, Object object) {
        int i = new Random().nextInt(configs.size());
        return configs.get(i);
    }
}
