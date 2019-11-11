package com.superwei.utils.designmodel.observer;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2019-11-08 14:48
 */
public class RMBRate extends Rate {

    @Override
    void change(int rate) {
        for (Company company : companies) {
            company.response(rate);
        }
    }
}
