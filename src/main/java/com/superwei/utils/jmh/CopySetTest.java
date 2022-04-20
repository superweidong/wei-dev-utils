package com.superwei.utils.jmh;

import com.superwei.utils.javassist.Test;
import lombok.Builder;
import lombok.Data;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author weidongge
 * @date 2022-04-20 15:55
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class CopySetTest {
    private static List<TestVo> sourceList;
    private static Integer count = 10000;
    private static Integer loopCount = 1;
    static {
        sourceList = new ArrayList<>();
        for (int i = 0; i <= count; i++) {
            sourceList.add(TestVo.builder().id(i).name(i+"name").build());
        }
    }


    @Benchmark
    public void copy() {
        List<TestVo> targetList = new ArrayList<>();
        for (int i = 0; i < loopCount; i++) {
            for (TestVo testVo : sourceList) {
                TestVo vo = TestVo.builder().build();
                BeanUtils.copyProperties(vo, testVo);
                targetList.add(vo);
            }
        }
        System.out.println(targetList.size());
    }

    @Benchmark
    public void set() {
        List<TestVo> targetList = new ArrayList<>();
        for (int i = 0; i < loopCount; i++) {
            for (TestVo testVo : sourceList) {
                TestVo vo = TestVo.builder().build();
                vo.setId(testVo.getId());
                vo.setName(testVo.getName());
                targetList.add(vo);
            }
        }
        System.out.println(targetList.size());
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(CopySetTest.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();
    }

    @Data
    @Builder
    static class TestVo{
        private Integer id;
        private String name;
    }


}
