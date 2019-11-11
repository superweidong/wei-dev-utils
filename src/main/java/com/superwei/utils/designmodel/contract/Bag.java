package com.superwei.utils.designmodel.contract;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description 树枝：购物袋
 * @create 2019-11-07 15:43
 */
@Data
public class Bag implements Articles {

    private String name;

    public Bag(String name) {
        this.name = name;
    }

    private List<Articles> list = new ArrayList<>();

    @Override
    public void show() {

        for (Articles articles : list) {
            articles.show();
        }
    }

    @Override
    public float calculate() {

        float money = 0f;
        for (Articles articles : list) {
            money += articles.calculate();
        }
        return money;
    }


    public void add(Articles articles){
        list.add(articles);
    }

    public void remove(Articles articles){
        list.remove(articles);
    }

    public void getChild(int i){
        list.get(i);
    }
    


}
