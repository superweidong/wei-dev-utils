package com.superwei.utils.lock;

import com.google.common.collect.*;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2020-02-05 10:26
 */
public class TwinLock implements Lock {

    @Override
    public void lock() {

    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long l, TimeUnit timeUnit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {

    }

    @Override
    public Condition newCondition() {
        return null;
    }


    public static void main(String[] args) {

        Map<Byte, List<String>> finalResult = new HashMap<>();
        Map<Byte, List<String>> map2 = new HashMap<>();
        map2.put(Byte.parseByte("1"),Lists.newArrayList("2", "3", "4"));
        map2.put(Byte.parseByte("2"),Lists.newArrayList("5", "3", "4"));

        map2.forEach((key, value) -> finalResult.merge(key, value, (List<String> newList, List<String> oldList)->{
           newList.addAll(oldList);
           return newList;
       }));

        System.out.println(finalResult);
        System.out.println(map2);


        Map<Byte, List<String>> map1 = new HashMap<>();
        map1.put(Byte.parseByte("1"),Lists.newArrayList("5", "3", "4"));
        map1.put(Byte.parseByte("2"),Lists.newArrayList("5", "6", "4"));
        map1.forEach((key, value) -> finalResult.merge(key, value, (List<String> newList, List<String> oldList)->{
            newList.addAll(oldList);
            return newList;
        }));
        System.out.println(finalResult);
        System.out.println(map1);







//        Map<Byte, List<String>> result = new HashMap<>();
//        result = Stream.of(result, map2)
//                .flatMap(map -> map.entrySet().stream())
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (List<String> newList, List<String> oldList) -> {
//                    newList.addAll(oldList);
//                    return newList;
//                }));
//
//        System.out.println(result);
//
//        Map<Byte, List<String>> map1 = new HashMap<>();
//        map1.put(Byte.parseByte("1"),Lists.newArrayList("5", "3", "4"));
//        map1.put(Byte.parseByte("2"),Lists.newArrayList("5", "6", "4"));
//
//        result = Stream.of(result, map1)
//                .flatMap(map -> map.entrySet().stream())
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (List<String> newList, List<String> oldList) -> {
//                    newList.addAll(oldList);
//                    return newList;
//                }));
//
//
//
//        System.out.println(result);
    }
}
