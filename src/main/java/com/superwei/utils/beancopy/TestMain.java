package com.superwei.utils.beancopy;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;

/**
 * @author apple
 */
public class TestMain {

    public static void main(String[] args) {
        Source source = new Source("张三", 20);
        Target target1 = BeanConvertCopyUtil.convertTo(source, Target::new);
        System.out.println(target1);
        Target target2 = BeanConvertCopyUtil.convertTo(source, Target::new, (source1, target) -> target.setAddress(source1.getName()));
        System.out.println(target2);

        ArrayList<Source> sources = Lists.newArrayList(new Source("李四", 10), new Source("王五", 27));
        List<Target> targets = BeanConvertCopyUtil.convertToList(sources, Target::new);
        System.out.println(targets);
        List<Target> targets2 = BeanConvertCopyUtil.convertToList(sources, Target::new, (source1, target) -> target.setAddress(source1.getName()));
        System.out.println(targets2);

        ArrayList<Source> sources3 = Lists.newArrayList(new Source("李四", 10), new Source("王五", 27));
        Set<Target> targets3 = BeanConvertCopyUtil.convertToSet(sources3, Target::new);
        System.out.println(targets3);
        Set<Target> targets4 = BeanConvertCopyUtil.convertToSet(sources3, Target::new, (source1, target) -> target.setAddress(source1.getName()));
        System.out.println(targets4);
    }

    @Data
    @AllArgsConstructor
    static class Source{
        private String name;
        private Integer age;
    }

    @Data
    static class Target{
        private String name;
        private Integer age;
        private String address;
    }
}
