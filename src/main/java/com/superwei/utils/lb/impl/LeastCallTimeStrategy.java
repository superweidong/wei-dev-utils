package com.superwei.utils.lb.impl;

import com.superwei.utils.lb.LoadBalanceStrategy;
import com.superwei.utils.lb.ServiceProvider;

import java.util.Comparator;
import java.util.List;

/**
 * 最少调用时间算法
 * @author weidongge
 * @date 2022-03-15 14:35
 */
public class LeastCallTimeStrategy implements LoadBalanceStrategy {

    @Override
    public ServiceProvider select(List<ServiceProvider> configs, Object object) {
        return configs.stream().min(Comparator.comparing(ServiceProvider::getCallTime)).orElse(null);
    }
}
