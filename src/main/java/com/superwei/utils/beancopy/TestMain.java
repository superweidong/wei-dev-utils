package com.superwei.utils.beancopy;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * @author apple
 */
public class TestMain {

    public static void main(String[] args) {
        Source source = new Source("张三", 20);
        Target target1 = BeanConvertCopyUtil.convertTo(source, Target::new);
        System.out.println(target1);
        Target target2 = BeanConvertCopyUtil.convertTo(source, Target::new, (source1, target) -> target.setAddress(source1.getName()));
        System.out.println(target1);

        ArrayList<Source> sources = Lists.newArrayList(new Source("李四", 10), new Source("王五", 27));
        Collection<Target> targets = BeanConvertCopyUtil.convertToCollection(sources, Target::new, ArrayList::new);
        System.out.println(targets);
        Collection<Target> targets2 = BeanConvertCopyUtil.convertToCollection(sources, Target::new, HashSet::new, (source1, target) -> target.setAddress(source1.getName()));
        System.out.println(targets2);
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
