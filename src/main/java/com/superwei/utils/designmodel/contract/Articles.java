package com.superwei.utils.designmodel.contract;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description 抽象组合构件
 * @create 2019-11-07 15:42
 */
public interface Articles {

    void show();
    float calculate();

    public static void main(String[] args) {

        Bag BigBag,mediumBag,smallRedBag,smallWhiteBag;
        Goods sp;
        BigBag=new Bag("大袋子");
        mediumBag=new Bag("中袋子");
        smallRedBag=new Bag("红色小袋子");
        smallWhiteBag=new Bag("白色小袋子");
        sp=new Goods("婺源特产",2,7.9f);
        smallRedBag.add(sp);
        sp=new Goods("婺源地图",1,9.9f);
        smallRedBag.add(sp);
        sp=new Goods("韶关香菇",2,68);
        smallWhiteBag.add(sp);
        sp=new Goods("韶关红茶",3,180);
        smallWhiteBag.add(sp);
        sp=new Goods("景德镇瓷器",1,380);
        mediumBag.add(sp);
        mediumBag.add(smallRedBag);
        sp=new Goods("李宁牌运动鞋",1,198);
        BigBag.add(sp);
        BigBag.add(smallWhiteBag);
        BigBag.add(mediumBag);
        System.out.println("您选购的小号红色袋商品有：");
        smallRedBag.show();
        System.out.println("您选购的小号白色袋商品有：");
        smallWhiteBag.show();
        System.out.println("您选购的中号袋商品有：");
        mediumBag.show();
        System.out.println("您选购的所有商品有：");
        BigBag.show();
        float s=BigBag.calculate();
        System.out.println("要支付的总价是："+s+"元");
    }
}
