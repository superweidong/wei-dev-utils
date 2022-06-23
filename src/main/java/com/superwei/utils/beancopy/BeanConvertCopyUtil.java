package com.superwei.utils.beancopy;

import org.junit.Assert;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author weidongge
 */
public class BeanConvertCopyUtil extends BeanUtils {

    private BeanConvertCopyUtil() {
    }


    /**
     * 复制对象
     *
     * @param source         源对象
     * @param targetSupplier 目标supplier
     * @return T
     * @author weidongge
     * @date 2022/6/23 14:24
     **/
    public static <S, T> T convertTo(S source, Supplier<T> targetSupplier) {
        return convertTo(source, targetSupplier, null);
    }

    /**
     * 复制对象
     *
     * @param source         源对象
     * @param targetSupplier 目标supplier
     * @param callBack       回调方法
     * @return T
     * @author weidongge
     * @date 2022/6/23 14:24
     **/
    public static <S, T> T convertTo(S source, Supplier<T> targetSupplier, ConvertCallBack<S, T> callBack) {
        Assert.assertNotNull("源对象不可为空", source);
        Assert.assertNotNull("目标对象为Supplier不可为空", targetSupplier);
        T target = targetSupplier.get();
        try {
            copyProperties(source, target);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (Objects.nonNull(callBack)) {
            callBack.callBack(source, target);
        }
        return target;
    }


    /**
     * 转换集合对象
     *
     * @param sourceCollection   源对象集合
     * @param targetSupplier     目标对象
     * @param targetCollSupplier 目标对象存储集合
     * @return java.util.Collection<T>
     * @author weidongge
     * @date 2022/6/23 15:02
     **/
    public static <S, T> Collection<T> convertToCollection(Collection<S> sourceCollection, Supplier<T> targetSupplier, Supplier<Collection<T>> targetCollSupplier) {
        return convertToCollection(sourceCollection, targetSupplier, targetCollSupplier, null);
    }


    /**
     * 转换集合对象
     *
     * @param sourceCollection   源对象集合
     * @param targetSupplier     目标对象
     * @param targetCollSupplier 目标对象存储集合<ArrayList::new> OR <HashSet::new> etc
     * @return java.util.Collection<T>
     * @author weidongge
     * @date 2022/6/23 15:02
     **/
    public static <S, T> Collection<T> convertToCollection(Collection<S> sourceCollection, Supplier<T> targetSupplier, Supplier<Collection<T>> targetCollSupplier, ConvertCallBack<S, T> callBack) {
        return sourceCollection.stream()
                .map(source -> convertTo(source, targetSupplier, callBack))
                .collect(Collectors.toCollection(Objects.isNull(targetCollSupplier) ? ArrayList::new : targetCollSupplier));
    }

    /**
     * 回调接口
     *
     * @param <S> 源对象类型
     * @param <T> 目标对象类型
     */
    @FunctionalInterface
    public interface ConvertCallBack<S, T> {
        /**
         * 方法回调
         *
         * @param source 目标对象
         * @param target 源对象
         * @author weidongge
         * @date 2022/6/23 14:25
         **/
        void callBack(S source, T target);
    }
}
