package com.superwei.utils.designmodel.flyweight.chese;

import java.util.ArrayList;
import java.util.List;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2019-11-06 13:28
 */
public class CheseFactory {

    private List<Chese> list;

    public CheseFactory() {
        list = new ArrayList<>();
        list.add(new BlackChese());
        list.add(new WhiteChese());
    }

    public Chese getChese(String color){
        Chese chese ;
        if (color.equals("w")){
            chese = list.get(1);
        }else{
            chese = list.get(0);
        }
        return chese;
    }

    public static void main(String[] args) {
        CheseFactory factory = new CheseFactory();
        Chese w = factory.getChese("w");

        w.down();
        Chese b = factory.getChese("b");
        b.down();

        System.out.println(w);
        System.out.println(b);
    }
}
