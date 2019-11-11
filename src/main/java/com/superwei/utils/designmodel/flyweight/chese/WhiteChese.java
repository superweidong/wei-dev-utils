package com.superwei.utils.designmodel.flyweight.chese;

import java.awt.*;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2019-11-06 13:27
 */
public class WhiteChese implements Chese {

    @Override
    public void down() {
        System.out.println(Color.WHITE);
    }
}
