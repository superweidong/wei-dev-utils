package com.superwei.utils.designmodel.adapter.demo;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2019-11-06 14:04
 */
public class JapanAdapter implements AC5V {
    private JapanAc japanAc;

    public JapanAdapter(JapanAc japanAc) {
        this.japanAc = japanAc;
    }

    @Override
    public int AC5V() {
        return japanAc.getAc()/11;
    }
}
