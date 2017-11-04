/*
 * Copyright 2015 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package me.gwma.java.basic.string;

/**
 * 类StringOperation.java的实现描述：TODO 类实现描述
 * 
 * @author guowei.magw 2015年8月18日 下午8:34:58
 */
public class StringOperation {

    public static void main(String[] args) {
        String str = "<property nam$e=\"dbGroupKey\" value=\"DA_ALINK_ANIMA_GROUP\" />";
        // String str2 = str.replaceAll("\"DA_ALINK_ANIMA_GROUP\"", "${dt.anima.tddl.dbgroupkey}");
        String str2 = str.replaceAll("\"DA_ALINK_ANIMA_GROUP\"", "\"\\${dt.anima.tddl.dbgroupkey}\"");
        System.out.println(str);
        System.out.println(str2);

    }
}
