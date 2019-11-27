package com.superwei.utils.multidatasource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2019-11-27 18:36
 */
@Slf4j
public class DynamicDataSourceHolder {

    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal();

    public static void setDataSourceType(String name){
        CONTEXT_HOLDER.set(name);
    }

    public static String getDataSourceType(){
        return CONTEXT_HOLDER.get();
    }

    public static void removeDataSourceType(){
        CONTEXT_HOLDER.remove();
    }


}
