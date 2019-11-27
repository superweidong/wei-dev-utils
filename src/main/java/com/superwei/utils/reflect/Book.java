package com.superwei.utils.reflect;

import lombok.Data;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2019-11-11 10:08
 */
@Data
public class Book  {

    private String TAG = "VTAG";

    private String name;
    private String author;

    public Book(String name, String author) {
        this.name = name;
        this.author = author;
    }

    public Book() {
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                '}';
    }

    private int getNum(int num){
        return 1;
    }

    public String getString(){
        return "hello";
    }
}
