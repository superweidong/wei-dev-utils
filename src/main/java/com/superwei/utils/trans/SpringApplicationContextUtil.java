package com.superwei.utils.trans;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2021-11-02 10:28
 */
public class SpringApplicationContextUtil {

    private static ApplicationContext context = null;

    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        context = applicationContext;
    }

    public static ApplicationContext getContext() {
        return context;
    }
}
