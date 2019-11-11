package com.superwei.utils.designmodel.contract;

import lombok.Data;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description 叶子：商品
 * @create 2019-11-07 15:42
 */
@Data
public class Goods implements Articles {
    private String name;
    private int  num;
    private float price;


    public Goods(String name, int num, float price) {
        this.name = name;
        this.num = num;
        this.price = price;
    }

    @Override
    public void show() {
        System.out.println("商品名称: "+ name +" 价格："+price+" 数量: "+num);
    }

    @Override
    public float calculate() {
        return price * num;
    }
}
