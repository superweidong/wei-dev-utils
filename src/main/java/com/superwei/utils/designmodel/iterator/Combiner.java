package com.superwei.utils.designmodel.iterator;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2019-11-08 15:03
 */
public interface Combiner {

    void add(Object o);
    void remove(Object o);
    Iterator getIterator();
}
