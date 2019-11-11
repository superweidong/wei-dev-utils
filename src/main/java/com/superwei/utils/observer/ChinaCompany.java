package com.superwei.utils.observer;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2019-11-08 14:46
 */
public class ChinaCompany implements Company {
    @Override
    public void response(int rate) {

        System.out.println("中国公司相应");
    }
}
