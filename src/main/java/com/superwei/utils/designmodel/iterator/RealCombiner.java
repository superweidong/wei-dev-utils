package com.superwei.utils.designmodel.iterator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2019-11-08 15:06
 */
public class RealCombiner implements Combiner {
    private List<Object> combiners = new ArrayList<>();

    @Override
    public void add(Object o) {
        combiners.add(o);
    }

    @Override
    public void remove(Object o) {
        combiners.remove(o);
    }

    @Override
    public Iterator getIterator() {

        return new RealIterator(combiners);
    }


    public static void main(String[] args) {

        Combiner combiner = new RealCombiner();
        combiner.add("1");
        combiner.add("2");
        combiner.add("3");
        Iterator iterator = combiner.getIterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
}
