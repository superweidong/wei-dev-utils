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
    // 保存权重
    private volatile List<ServiceProvider> nodeList = new ArrayList<>() ;

    @Override
    public ServiceProvider select(List<ServiceProvider> configs, Object object) {
        for (ServiceProvider node : configs) {
            nodeList.add(node) ;
        }
        return normalPollWeight();
    }

    private ServiceProvider normalPollWeight() {
        int totalWeight = 0 ;
        ServiceProvider maxNode = null ;
        int maxWeight = 0 ;

        for (int i = 0; i < nodeList.size(); i++) {
            ServiceProvider n = nodeList.get(i);
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
}
