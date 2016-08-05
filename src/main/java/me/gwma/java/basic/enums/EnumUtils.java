package me.gwma.java.basic.enums;

import java.util.Random;

/**
 * 枚举使用类
 * 
 * @author Ace Mar 26, 2016 10:50:35 AM
 */
public class EnumUtils {

    private static Random rand = new Random();

    /**
     * 随机生成枚举类的一个实例值
     * 
     * @param en
     * @return
     */
    public static <T extends Enum<T>> T random(Class<T> en) {
        return random(en.getEnumConstants());
    }

    /**
     * 随机返回一个数组中的元素
     * 
     * @param values
     * @return
     */
    public static <T> T random(T[] values) {
        return values[rand.nextInt(values.length)];
    }

}
