package com.superwei.utils.lb.impl;

import com.superwei.utils.lb.LoadBalanceStrategy;
import com.superwei.utils.lb.ServiceProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 加权随机算法
 * @author weidongge
 * @date 2022-03-14 16:46
 */
public class RandomWeightStrategy implements LoadBalanceStrategy {

    @Override
    public ServiceProvider select(List<ServiceProvider> configs, Object object) {
        List<ServiceProvider> newConfigs = new ArrayList<>();
        for (ServiceProvider config : configs) {
            for (int i = 0; i < config.getWeight(); i++) {
                newConfigs.add(config);
            }
        }
        return newConfigs.get(new Random().nextInt(newConfigs.size()));
    }
}
