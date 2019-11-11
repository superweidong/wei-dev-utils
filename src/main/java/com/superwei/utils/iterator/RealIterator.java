package com.superwei.utils.iterator;

import java.util.List;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2019-11-08 15:08
 */
public class RealIterator implements Iterator {

    private List<Object> objects;

    private int i=-1;

    public RealIterator(List<Object> objects) {
        this.objects = objects;
    }

    @Override
    public Object next() {
        Object o = null;
        if (hasNext()){
            o = objects.get(++i);
        }
        return o;
    }

    @Override
    public boolean hasNext() {
        if ( i<objects.size()-1){
            return true;
        }else{
            return false;
        }
    }
}
