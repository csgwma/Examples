/*
 * Copyright 2015 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package me.gwma.java.basic.genericprogramming;

/**
 * 类Pair.java的实现描述：Pair泛型类
 * 
 * @author guowei.magw 2015年8月19日 下午3:18:56
 */
public class Pair2<T, U> {

    private T first;
    private U second;

    public Pair2(){
        first = null;
        second = null;
    }

    /**
     * @param first
     * @param second
     */
    public Pair2(T first, U second){
        super();
        this.first = first;
        this.second = second;
    }

    /**
     * @return the first
     */
    public T getFirst() {
        return first;
    }

    /**
     * @param first the first to set
     */
    public void setFirst(T first) {
        this.first = first;
    }

    /**
     * @return the second
     */
    public U getSecond() {
        return second;
    }

    /**
     * @param second the second to set
     */
    public void setSecond(U second) {
        this.second = second;
    }

    public String toString(){
        return "[" + first.toString() + ", "+second.toString() + "]";
    }
    
    public static void main(String[] args){
        Pair2<String, Integer> pair = new Pair2<String, Integer>();
        pair.first = "ma";
        pair.second = 18;
        System.out.println(pair.toString());
    }
}
