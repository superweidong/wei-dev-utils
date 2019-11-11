package com.superwei.utils.observer;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2019-11-08 14:46
 */
public class JapanCompany implements Company {
    @Override
    public void response(int rate) {

        System.out.println("日本公司相应");
    }
}
