package com.superwei.utils.lb.impl;

import com.superwei.utils.lb.LoadBalanceStrategy;
import com.superwei.utils.lb.ServiceProvider;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 轮询算法
 * @author weidongge
 * @date 2022-03-14 16:33
 */
public class PollingStrategy implements LoadBalanceStrategy {

    private static Map<String, Integer> map = new ConcurrentHashMap<>();

    private static AtomicInteger index = new AtomicInteger(0);

    @Override
    public ServiceProvider select(List<ServiceProvider> configs, Object object) {
        //return mapSelect(configs);
        return listSelect(configs);
    }

    /**
     * 外部AtomicInteger计数器记录当前服务下标
     * @param configs configs
     * @return com.superwei.utils.lb.ServiceProvider
     * @author apple
     * @date 2022/3/15
     */
    private ServiceProvider listSelect(List<ServiceProvider> configs) {
        if (index.get() >= configs.size()) {
            index.set(0);
        }
        return configs.get(index.getAndIncrement());
    }

    /**
     * 使用外部map存储当前服务下标
     * @param configs configs
     * @return com.superwei.utils.lb.ServiceProvider
     * @author apple
     * @date 2022/3/15
     */ 
    private ServiceProvider mapSelect(List<ServiceProvider> configs) {
        Integer index = map.get(getKey(configs.get(0)));
        if (Objects.isNull(index)) {
            map.put(getKey(configs.get(0)), 0);
            System.out.println(configs.get(0));
            return configs.get(0);
        }else {
            index++;
            if (index >= configs.size()) {
                index = 0;
            }
            map.put(getKey(configs.get(0)), index);
            System.out.println(configs.get(index));
            return configs.get(index);
        }
    }

    private String getKey(ServiceProvider config){
        return config.getInterfaceName();
    }
}
