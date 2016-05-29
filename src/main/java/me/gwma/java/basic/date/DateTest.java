/*
 * Copyright 2015 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package me.gwma.java.basic.date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 类DateTest.java的实现描述：TODO 类实现描述 
 * @author guowei.magw 2015年8月18日 下午8:32:54
 */
public class DateTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        //HH表示24小时制，hh表示12小时制
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        System.out.println(formatter.format(date));
        
        
    }

}
