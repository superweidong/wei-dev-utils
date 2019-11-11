package com.superwei.utils.designmodel.adapter.demo;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2019-11-06 14:01
 */
public interface AC {

    int getAc();


    public static void main(String[] args) {
        AC5V ac5V = new ChinaAdapter(new ChinaAc());
        System.out.println(ac5V.AC5V());
        AC5V ac5V2 = new JapanAdapter(new JapanAc());

        System.out.println(ac5V2.AC5V());

    }
}
