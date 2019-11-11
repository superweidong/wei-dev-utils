package com.superwei.utils.designmodel.adapter;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description 对象适配器
 * @create 2019-11-06 13:51
 */
public class ObjectAdapter implements Target {

    private Adaptee adaptee;
    public ObjectAdapter(Adaptee adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void request() {

        System.out.println("对象适配器前");
        adaptee.currentRequest();
        System.out.println("对象适配器后");

    }

    public static void main(String[] args) {
        Adaptee adaptee = new Adaptee();
        Target target = new ObjectAdapter(adaptee);
        target.request();
    }
}
