package com.superwei.utils.reflect.rpcdemo.server;

import java.util.HashMap;
import java.util.Map;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description 模拟配置，实际的框架中大部分都是使用xml进行配置的，比如Hessian是配置在web.xml的servlet属性里的
 * @create 2019-11-11 10:41
 */
public class ConfMonitor {
    public static Map<String, Class> configMap = new HashMap();

    static {
        configMap.put("com.superwei.utils.reflect.rpcdemo.server.ITestService", TestServiceImpl.class);
    }
}
