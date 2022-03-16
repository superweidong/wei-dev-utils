package com.superwei.utils.lb;

import lombok.Data;

/**
 * @author weidongge
 * @date 2022-03-14 16:31
 */
@Data
public class ServiceProvider {

    private String host;
    private Integer port;
    private String interfaceName;
    private String[] methods;
    private String application;
    private int weight;
    private int currentWeight;
    private int callTime;
}
