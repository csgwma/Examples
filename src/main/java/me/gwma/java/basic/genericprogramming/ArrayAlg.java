/*
 * Copyright 2015 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package me.gwma.java.basic.genericprogramming;

/**
 * 类ArrayAlg.java的实现描述：TODO 类实现描述
 * 
 * @author guowei.magw 2015年8月19日 下午3:29:38
 */
public class ArrayAlg {

    /**
     * 一个泛型方法，获取数组的中间元素
     * 
     * @param a
     * @return
     */
    public static <T> T getMiddle(T[] a) {
        return a[a.length / 2];
    }

    /**
     * 带类型变量限定的泛型方法，获取数组中的最小值
     * 
     * @param a
     * @return
     */
    public static <T extends Comparable> T min(T[] a) {
        if (a == null || a.length == 0) {
            return null;
        }
        T smallest = a[0];
        for (int i = 0; i < a.length; ++i) {
            if (smallest.compareTo(a[i]) > 0) {
                smallest = a[i];
            }
        }
        return smallest;
    }

    /**
     * 带类型限定的泛型方法，获取数组中的最小最大值
     * 
     * @param a
     * @return
     */
    public static <T extends Comparable<T>> Pair2<T, T> minmax(T[] a) {
        if (a == null || a.length == 0) {
            return null;
        }
        T min = a[0];
        T max = a[0];
        for (int i = 0; i < a.length; ++i) {
            if (min.compareTo(a[i]) > 0) {
                min = a[i];
            }
            if (max.compareTo(a[i]) < 0) {
                max = a[i];
            }
        }
        return new Pair2<T, T>(min, max);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

    }

}
