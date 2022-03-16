package com.superwei.utils.lb.impl;

import com.superwei.utils.lb.LoadBalanceStrategy;
import com.superwei.utils.lb.ServiceProvider;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 加权轮询算法
 * @author weidongge
 * @date 2022-03-14 16:33
 */
public class PollingWeightV2Strategy implements LoadBalanceStrategy {


    @Override
    public ServiceProvider select(List<ServiceProvider> configs, Object object) {
        ServiceProvider maxNode = getProvider(configs);
        printLog(configs, maxNode);
        return maxNode ;
    }

    private ServiceProvider getProvider(List<ServiceProvider> configs) {
        int totalWeight = 0 ;
        ServiceProvider maxNode = null ;
        int maxWeight = 0 ;
        for (int i = 0; i < configs.size(); i++) {
            ServiceProvider n = configs.get(i);
            totalWeight += n.getWeight() ;
            // 每个节点的当前权重要加上原始的权重
            n.setCurrentWeight(n.getCurrentWeight() + n.getWeight());
            // 保存当前权重最大的节点
            if (maxNode == null || maxWeight < n.getCurrentWeight() ) {
                maxNode = n ;
                maxWeight = n.getCurrentWeight() ;
            }
        }
        // 被选中的节点权重减掉总权重
        maxNode.setCurrentWeight(maxNode.getCurrentWeight() - totalWeight);
        return maxNode;
    }

    private void printLog(List<ServiceProvider> configs, ServiceProvider maxNode){
        for (ServiceProvider config : configs) {
            System.out.println("host: " + config.getHost() + ":" + config.getPort()+ " 权重：" + config.getWeight() + " 当前权重：" + config.getCurrentWeight());
        }
        System.out.println("选择host: " + maxNode.getHost() + ":" + maxNode.getPort()+ " " + "权重：" + maxNode.getWeight() + " 当前权重：" + maxNode.getCurrentWeight());
    }

}
