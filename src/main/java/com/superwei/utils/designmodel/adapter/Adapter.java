package com.superwei.utils.designmodel.adapter;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2019-11-06 13:48
 */
public class Adapter extends Adaptee implements Target  {

    @Override
    public void request() {

        System.out.println("类适配器开始");
        super.currentRequest();
        System.out.println("类适配器结束");

    }

    public static void main(String[] args) {
        Target adapter = new Adapter();
        adapter.request();
    }
}
