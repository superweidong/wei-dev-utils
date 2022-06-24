package com.superwei.utils.beancopy;

import org.springframework.beans.BeanUtils;

import java.util.*;
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
        if (Objects.isNull(source) || Objects.isNull(targetSupplier)) {
            return null;
        }
        T target = targetSupplier.get();
        try {
            copyProperties(source, target);
        } catch (Exception e) {
            throw new BusinessException("复制属性异常", e);
        }
        if (Objects.nonNull(callBack)) {
            callBack.callBack(source, target);
        }
        return target;
    }

    /**
     * 转换集合对象,返回指定类型集合
     *
     * @param sourceCollection   源对象集合
     * @param targetSupplier     目标对象
     * @param targetCollSupplier 目标对象存储集合<ArrayList::new> OR <HashSet::new> etc
     * @param callBack           回调函数
     * @return java.util.Collection<T>
     * @author weidongge
     * @date 2022/6/23 15:02
     **/
    private static <S, T> Collection<T> convertToCollection(Collection<S> sourceCollection, Supplier<T> targetSupplier, Supplier<Collection<T>> targetCollSupplier, ConvertCallBack<S, T> callBack) {
        return sourceCollection.stream().map(source -> convertTo(source, targetSupplier, callBack)).collect(Collectors.toCollection(targetCollSupplier));
    }

    /**
     * 转换集合对象,返回List
     *
     * @param sourceList     源对象List集合
     * @param targetSupplier 目标对象
     * @return java.util.Collection<T>
     * @author weidongge
     * @date 2022/6/23 15:02
     **/
    public static <S, T> List<T> convertToList(Collection<S> sourceList, Supplier<T> targetSupplier) {
        return (ArrayList<T>) convertToCollection(sourceList, targetSupplier, ArrayList::new, null);
    }

    /**
     * 转换集合对象,返回List
     *
     * @param sourceList     源对象集合
     * @param targetSupplier 目标对象
     * @param callBack       回调函数
     * @return java.util.Collection<T>
     * @author weidongge
     * @date 2022/6/23 15:02
     **/
    public static <S, T> List<T> convertToList(Collection<S> sourceList, Supplier<T> targetSupplier, ConvertCallBack<S, T> callBack) {
        return (ArrayList<T>) convertToCollection(sourceList, targetSupplier, ArrayList::new, callBack);
    }

    /**
     * 转换集合对象,返回Set
     *
     * @param sourceSet      源对象集合
     * @param targetSupplier 目标对象
     * @return java.util.Collection<T>
     * @author weidongge
     * @date 2022/6/23 15:02
     **/
    public static <S, T> Set<T> convertToSet(Collection<S> sourceSet, Supplier<T> targetSupplier) {
        return (Set<T>) convertToCollection(sourceSet, targetSupplier, HashSet::new, null);
    }

    /**
     * 转换集合对象,返回Set
     *
     * @param sourceSet      源对象集合
     * @param targetSupplier 目标对象
     * @param callBack       回调函数
     * @return java.util.Collection<T>
     * @author weidongge
     * @date 2022/6/23 15:02
     **/
    public static <S, T> Set<T> convertToSet(Collection<S> sourceSet, Supplier<T> targetSupplier, ConvertCallBack<S, T> callBack) {
        return (Set<T>) convertToCollection(sourceSet, targetSupplier, HashSet::new, callBack);
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
