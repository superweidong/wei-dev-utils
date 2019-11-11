package com.superwei.utils.designmodel.prototype;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2019-10-31 17:26
 */
public class PrototypeTest {

    public static void main(String[] args) throws CloneNotSupportedException {

        PrototypeManage.initType();
        Shape type = PrototypeManage.getType("001");
        type.draw();
        Shape type2 = PrototypeManage.getType("002");
        type2.draw();
    }




    @Data
    static abstract class Shape implements Cloneable{
        private String id;
        protected String name;

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }


        abstract void draw();

    }

    static class Circle extends Shape{
        public Circle() {
            name = "Circle";
        }

        @Override
        void draw() {
            System.out.println("draw a Circle");
        }
    }
    static class Rectangle extends Shape{
        public Rectangle() {
            name = "Rectangle";
        }

        @Override
        void draw() {
            System.out.println("draw a Rectangle");
        }

    }

    static class PrototypeManage{

        private static Map<String, Shape> cacheType = new HashMap<>();

        public static void initType(){
            Circle circle = new Circle();
            circle.setId("001");
            cacheType.put(circle.getId(), circle);

            Rectangle rectangle = new Rectangle();
            rectangle.setId("002");
            cacheType.put(rectangle.getId(), rectangle);
        }

        public static Shape getType(String id) throws CloneNotSupportedException {
            return (Shape) cacheType.get(id).clone();
        }
    }
}
