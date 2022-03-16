package com.superwei.utils.lb.impl;

import com.superwei.utils.lb.LoadBalanceStrategy;
import com.superwei.utils.lb.ServiceProvider;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 一致性hash算法
 * @author weidongge
 * @date 2022-03-15 14:35
 */
public class UniformityHashStrategy implements LoadBalanceStrategy {

    private static final int VIRTUAL_NUM = 5;

    @Override
    public ServiceProvider select(List<ServiceProvider> configs, Object object) {

        SortedMap<Integer, ServiceProvider> sortedMap = new TreeMap<>();
        for (ServiceProvider config : configs) {
            for (int i = 0; i < VIRTUAL_NUM; i++) {
                sortedMap.put(calculateHash(getKey(config.getHost(), config.getPort(), "&node" + i)),config);
            }
        }
        int hash = calculateHash((String) object);
        SortedMap<Integer, ServiceProvider> subMap = sortedMap.subMap(hash, Integer.MAX_VALUE);

        ServiceProvider providerConfig = subMap.size() == 0 ? subMap.get(0) : subMap.get(subMap.firstKey());
        printLog(sortedMap, providerConfig, hash);
        return providerConfig;
    }

    private void printLog(SortedMap<Integer, ServiceProvider> sortedMap, ServiceProvider serviceProvider, int hash){
        sortedMap.forEach((key, config) -> System.out.println("hash: " + key + " ip: "+config.getHost() + ":" + config.getPort()));
        System.out.println("请求hash" + hash +" 选择：" + serviceProvider.getHost() + ":" + serviceProvider.getPort());
    }

    private String getKey(String host,int port,String node){
        return host + ":" + port + node;
    }

    private int calculateHash(String str){

//        int hashCode =  str.hashCode();
//        hashCode = (hashCode<0)?(-hashCode):hashCode;
//        return hashCode;
        final int p = 16777619;
        int hash = (int)2166136261L;
        for (int i = 0; i < str.length(); i++) {
            hash = (hash ^ str.charAt(i)) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;

        // 如果算出来的值为负数则取其绝对值
        if (hash < 0) {
            hash = Math.abs(hash);
        }
        return hash;

    }
}
