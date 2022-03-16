package com.superwei.utils.lb;

import java.util.List;

/**
 * 负载均衡策略类接口
 * @author weidongge
 * @date 2022-03-14 16:31
 */
public interface LoadBalanceStrategy {

    /**
     * 负载均衡接口方法
     *
     * @param configs configs
     * @param object  object
     * @return com.superwei.utils.lb.ProviderConfig
     * @author apple
     * @date 2022/3/14
     */
    ServiceProvider select(List<ServiceProvider> configs, Object object);
}
