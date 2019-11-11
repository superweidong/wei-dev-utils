package com.superwei.utils.designmodel.adapter.demo;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2019-11-06 14:03
 */
public class ChinaAdapter implements AC5V {
    private ChinaAc chinaAc;

    public ChinaAdapter(ChinaAc chinaAc) {
        this.chinaAc = chinaAc;
    }

    @Override
    public int AC5V() {
        return chinaAc.getAc()/22;
    }
}
