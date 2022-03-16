package com.superwei.utils.lb.impl;

import com.superwei.utils.lb.LoadBalanceStrategy;
import com.superwei.utils.lb.ServiceProvider;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 轮询算法
 * @author weidongge
 * @date 2022-03-14 16:33
 */
public class PollingWeightStrategy implements LoadBalanceStrategy {

    private static Map<String, Integer> map = new ConcurrentHashMap<>();


    @Override
    public ServiceProvider select(List<ServiceProvider> configs, Object object) {
        return normalPollWeight(configs);
    }

    private ServiceProvider normalPollWeight(List<ServiceProvider> configs) {
        Integer index = map.get(getKey(configs.get(0)));
        if (Objects.isNull(index)) {
            map.put(getKey(configs.get(0)), 0);
            System.out.println("选中服务：" + configs.get(0).getHost() + ":" + configs.get(0).getPort());
            return configs.get(0);
        }else {
            index++;
            List<ServiceProvider> newList = new ArrayList<>();
            for (ServiceProvider config : configs) {
                for (int i = 0; i < config.getWeight(); i++) {
                    newList.add(config);
                }
            }
            if (index >= newList.size()) {
                index = 0;
            }
            map.put(getKey(newList.get(0)), index);
            System.out.println("选中服务：" + newList.get(index).getHost() + ":" + newList.get(index).getPort());
            return newList.get(index);
        }
    }

    private String getKey(ServiceProvider config){
        return config.getInterfaceName();
    }
}
