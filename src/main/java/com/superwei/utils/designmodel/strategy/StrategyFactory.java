package com.superwei.utils.designmodel.strategy;

import java.util.HashMap;
import java.util.Map;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2019-11-08 11:30
 */
public class StrategyFactory {

    public static Map<String, AbstractStrategy> strategyMap = new HashMap<>();

    static {
        strategyMap.put("one", new RealStrategy1());
        strategyMap.put("two", new RealStrategy2());
    }


    public static void main(String[] args) {
        StrategyFactory.strategyMap.get("one").out();
    }
}
