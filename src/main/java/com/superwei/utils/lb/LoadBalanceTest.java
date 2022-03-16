package com.superwei.utils.lb;

import com.superwei.utils.lb.impl.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author weidongge
 * @date 2022-03-14 16:34
 */
public class LoadBalanceTest {

    @Test
    public void testRandom(){
        RandomStrategy randomStrategy = new RandomStrategy();
        loadBalance(randomStrategy, 10, 1000);
    }

    @Test
    public void testWeightRandom(){
        RandomWeightStrategy strategy = new RandomWeightStrategy();
        loadBalance(strategy, 10, 1000);
    }

    @Test
    public void testPolling(){
        PollingStrategy strategy = new PollingStrategy();
        loadBalance(strategy, 10, 20);
    }

    @Test
    public void testPollingWeight(){
        PollingWeightStrategy strategy = new PollingWeightStrategy();
        loadBalance(strategy, 10, 50);
    }

    @Test
    public void testPollingWeightV2(){
        PollingWeightV2Strategy strategy = new PollingWeightV2Strategy();
        loadBalanceV3(strategy, 3, 9);
    }


    @Test
    public void testLeastCallTime(){
        LeastCallTimeStrategy strategy = new LeastCallTimeStrategy();
        loadBalance(strategy, 10, 10);
    }

    @Test
    public void testUniformityHash(){
        UniformityHashStrategy strategy = new UniformityHashStrategy();
        loadBalance(strategy, 10, 1);
    }

    /**
     * 测试
     *
     * @param strategy  strategy
     * @param configNum configNum
     * @param testCount testCount
     * @author apple
     * @date 2022/3/14
     */
    public void loadBalanceV3(LoadBalanceStrategy strategy ,int configNum,int testCount ) {
        List<ServiceProvider> configs = new ArrayList<>();
        int[] counts = new int[configNum];
        int[] weightArr = new int[]{4,3,2};
        for (int i = 0; i < configNum; i++) {
            ServiceProvider config = new ServiceProvider();
            config.setInterfaceName("com.serviceImpl" + i);
            config.setHost("127.0.0.1");
            config.setPort(i);
            config.setWeight(weightArr[i]);
            config.setCurrentWeight(weightArr[i]);
            configs.add(config);
        }
        for (int i = 0; i < testCount; i++) {
            ServiceProvider config = strategy.select(configs, "127.0.0.1:1234");
            int count = counts[config.getPort()];
            counts[config.getPort()] = ++count;
        }
        for (int i = 0; i < configNum; i++) {
            System.out.println("序号:" + i + " 权重：" + configs.get(i).getWeight() + " 调用时间：" + configs.get(i).getCallTime() + "--次数：" + counts[i]);
        }
    }

    /**
     * 测试
     *
     * @param strategy  strategy
     * @param configNum configNum
     * @param testCount testCount
     * @author apple
     * @date 2022/3/14
     */
    public void loadBalanceV2(LoadBalanceStrategy strategy ,int configNum,int testCount ) {

        List<ServiceProvider> configs = new ArrayList<>();
        int[] counts = new int[configNum];
        for (int i = 0; i < configNum; i++) {
            ServiceProvider config = new ServiceProvider();
            config.setInterfaceName("com.serviceImpl" + i);
            config.setHost("127.0.0.1");
            config.setPort(i);
            int weight = new Random().nextInt(100);
            config.setWeight(weight);
            config.setCurrentWeight(weight);
            configs.add(config);
        }
        for (int i = 0; i < testCount; i++) {
            ServiceProvider config = strategy.select(configs, "127.0.0.1:1234");
            int count = counts[config.getPort()];
            counts[config.getPort()] = ++count;
        }
        for (int i = 0; i < configNum; i++) {
            System.out.println("序号:" + i + " 权重：" + configs.get(i).getWeight() + " 调用时间：" + configs.get(i).getCallTime() + "--次数：" + counts[i]);
        }
    }


    /**
     * 测试
     *
     * @param strategy  strategy
     * @param configNum configNum
     * @param testCount testCount
     * @author apple
     * @date 2022/3/14
     */
    public void loadBalance(LoadBalanceStrategy strategy ,int configNum,int testCount ){
        List<ServiceProvider> configs = new ArrayList<>();
        int[] counts = new int[configNum];

        for(int i = 0; i< configNum; i++){
            ServiceProvider config = new ServiceProvider();
            config.setInterfaceName("com.serviceImpl" + i);
            config.setHost("127.0.0.1");
            config.setPort(i);
            int weight = new Random().nextInt(100);
            config.setWeight(weight);
            config.setCurrentWeight(weight);
            config.setCallTime(new Random().nextInt(100));
            configs.add(config);
        }
        for(int i = 0; i< testCount ; i++){
            ServiceProvider config = strategy.select(configs,"127.0.0.1:1234");
            int count = counts[config.getPort()];
            counts[config.getPort()] = ++count;
        }
        for(int i = 0; i< configNum; i++){
            ServiceProvider provider = configs.get(i);
            System.out.println("序号:" + i +
                    " 服务:" + provider.getHost() + ":" + provider.getPort() + "&" + provider.getInterfaceName()+
                    " 权重:" + provider.getWeight() + " " +
                    "  调用时间:" + provider.getCallTime() +
                    "  被调用次数:" + counts[i]);
        }
    }
}
