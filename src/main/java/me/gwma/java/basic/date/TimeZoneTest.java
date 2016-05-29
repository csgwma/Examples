/*
 * Copyright 2016 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package me.gwma.java.basic.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

/**
 * 类TempTest.java的实现描述：TODO 类实现描述
 * 
 * @author Ace Jan 25, 2016 2:06:04 PM
 */
public class TimeZoneTest {

    /** 日期格式 */
    public static final String FORMAT_DATE     = "yyyy-MM-dd";
    public static final String FORMAT_TIME     = "HH:mm:ss";
    public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";

    /**
     * @param args
     */
    public static void main(String[] args) {
        // testDate();
        testSimpleDateFormate();
        // testTimeZone();
        // testCalendar();
        testDaylightSavingTime();
        // testUseDST(TimeZone.getDefault());
    }

    public static void testDate() {
        System.out.println("====> testDate()");
        Date now = new Date();
        System.out.println("now -date: year=" + now.getYear() + ", month=" + now.getMonth() + ", day=" + now.getDay());
        System.out.println("now -time: hour=" + now.getHours() + ",minute=" + now.getMinutes() + ", sec="
                           + now.getSeconds());
        System.out.println("the number of milliseconds=" + now.getTime());
        System.out.println(" Default dateTime String = " + now.toString());
        System.out.println(" GMT dateTime String  = " + now.toGMTString());
        System.out.println(" Local dateTime String  = " + now.toLocaleString());
    }

    /**
     * SimpleDateFormate类测试
     */
    public static void testSimpleDateFormate() {
        System.out.println("====> testSimpleDateFormate()");
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_DATETIME);
        formatter.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        Date now = new Date();
        System.out.println("        now  :" + formatter.format(now));
        Date halfYearAgo = new Date(now.getTime() - 1000L * 3600 * 24 * 180);
        System.out.println("half year ago:" + formatter.format(halfYearAgo));
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATETIME);
        sdf.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        SimpleDateFormat sdf2 = new SimpleDateFormat(FORMAT_DATETIME);
        sdf2.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        try {
            String dateStr = "1970-01-01 00:00:00";
            System.out.println("Local America/New_York = " + sdf.parse(dateStr));
            System.out.println("                    TS = " + sdf.parse(dateStr).getTime());
            System.out.println("Local Asia/Shanghai    = " + sdf2.parse(dateStr));
            System.out.println("                    TS = " + sdf2.parse(dateStr).getTime());
            System.out.println(sdf2.format(now));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("====> 同一时间经过转换，结果不同");
            Date date = new Date(1359641834000L);// 2013-1-31 22:17:14
            String dateStr = "2013-1-31 22:17:14";
            SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_DATETIME);
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            // 字符串转日期
            System.out.println("字符串转日期，转换为本地时区显示");
            Date dateTmp = dateFormat.parse(dateStr);
            System.out.println("本地时区显示 parse result = " + dateTmp.toString());
            System.out.println("        // TimeInMills = " + dateTmp.getTime() + " ");
            System.out.println("        // rawOffset   = " + (dateTmp.getTime() - date.getTime()) / 3600000
                               + " h (单位毫秒转换为小时)");
            System.out.println("GMT时区显示 parse result = " + dateFormat.format(dateTmp));
            System.out.println("        // TimeInMills = " + dateTmp.getTime());
            System.out.println("        // rawOffset   = " + (dateTmp.getTime() - date.getTime()) / 3600000
                               + " h (单位毫秒转换为小时)");

            // 日期转字符串
            System.out.println("日期转字符串，转换为设置的时区显示");
            String dateStrTmp = dateFormat.format(date);
            System.out.println("format result = " + dateStrTmp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * TimeZone类使用测试
     */
    public static void testTimeZone() {
        System.out.println("====> testTimeZone()");
        // 输出TimeZone支持的时区ID
        System.out.println(Arrays.asList(TimeZone.getAvailableIDs()));

        System.out.println("==== 测试0 - 基本使用测试");
        // Gets the default TimeZone for this host.
        System.out.println("Default TimeZone    ID : " + TimeZone.getDefault().getID());
        System.out.println("Default TimeZone ZoneId: " + TimeZone.getDefault().toZoneId());
        System.out.println("Default TimeZone Display Name: " + TimeZone.getDefault().getDisplayName());
        System.out.println("Default TimeZone getDSTSavings: " + TimeZone.getDefault().getDSTSavings());
        System.out.println("Default raw offset: " + TimeZone.getDefault().getRawOffset());

        // Create TimeZone by the time zone ID
        System.out.println("getId    : " + TimeZone.getTimeZone("Asia/Shanghai").getID());
        System.out.println("toZoneId : " + TimeZone.getTimeZone("Asia/Shanghai").toZoneId());
        System.out.println("toString : " + TimeZone.getTimeZone("America/New_York").toString());

        // Create TimeZone by the specified custom time zone ID
        System.out.println("getId    : " + TimeZone.getTimeZone("GMT+0800").getID());
        System.out.println("toZoneId : " + TimeZone.getTimeZone("GMT+0800").toZoneId());
        System.out.println("toString : " + TimeZone.getTimeZone("GMT+0800").toString());
        System.out.println("raw offset: " + TimeZone.getTimeZone("GMT+0800").getRawOffset());

        System.out.println("==== 测试1 - 夏时令问题");
        System.out.println("====> testSimpleTimeZone()");
        int timeZoneOffset = +2;
        TimeZone timeZone = new SimpleTimeZone(timeZoneOffset * 60 * 60 * 1000, "Asia/Shanghai");
        System.out.println(timeZone.getID());
        System.out.println("SimpleTimeZone is a concrete subclass of TimeZone that represents a time zone for use with a Gregorian calendar. ");
    }

    /**
     * Calendar类使用测试
     */
    public static void testCalendar() {
        System.out.println("====> testCalendar()");

        System.out.println("==== 测试0 - TimeInMills值的意义");
        long begin = 0;
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Pairs"));
        calendar.setTimeInMillis(begin);
        System.out.println("calendar  TimeInMillis= " + calendar.getTimeInMillis());
        System.out.println("          Local Time= " + calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH)
                           + "-" + calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.HOUR_OF_DAY) + ":"
                           + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND));
        System.out.println("            get Time= " + calendar.getTime()); // 设置完时区后，我们不能用calendar.getTime()来直接获取Date日期

        calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Shanghai"));
        calendar.setTimeInMillis(begin);
        System.out.println("calendar  TimeInMillis= " + calendar.getTimeInMillis());
        System.out.println("          Local Time= " + calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH)
                           + "-" + calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.HOUR_OF_DAY) + ":"
                           + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND));
        System.out.println("            get Time= " + calendar.getTime());// 设置完时区后，我们不能用calendar.getTime()来直接获取Date日期
        System.out.println("Conclusion 0: TimeInMills是世界当前时刻相对January 1, 1970, 00:00:00 GMT（Greenwich Mean Time）的毫秒数（可正可负）。");
        System.out.println("-----------------------------");

        System.out.println("==== 测试1 - TimeInMills值相同，但在各个时区的本地时间不一样 ");
        Date date = new Date();

        System.out.println("long : ms = " + date.getTime());
        begin = date.getTime();
        calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Pairs"));
        calendar.setTimeInMillis(begin); // 这个函数很耗时
        System.out.println("calendar  TimeInMillis= " + calendar.getTimeInMillis());
        System.out.println("          Local Time= " + calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH)
                           + "-" + calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.HOUR_OF_DAY) + ":"
                           + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND));
        System.out.println("            get Time= " + calendar.getTime());

        calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Shanghai"));
        calendar.setTimeInMillis(begin);
        System.out.println("calendar  TimeInMillis= " + calendar.getTimeInMillis());
        System.out.println("          Local Time= " + calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH)
                           + "-" + calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.HOUR_OF_DAY) + ":"
                           + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND));
        System.out.println("            get Time= " + calendar.getTime());

        // 转换
        calendar.setTimeZone(TimeZone.getTimeZone("Europe/Pairs"));
        System.out.println("change calendar TimeZone=" + calendar.getTimeZone().getID());
        System.out.println("calendar  TimeInMillis= " + calendar.getTimeInMillis());
        System.out.println("          Local Time= " + calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH)
                           + "-" + calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.HOUR_OF_DAY) + ":"
                           + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND));
        System.out.println("Conclusion 1: 同一个calendar对象，TimeInMillis一样，但set不同的TimeZone，输出的本地时间不一样。");
        System.out.println("-----------------------------");

        System.out.println("==== 测试2 - calendar对象对日期的灵活操作 ");
        calendar.add(Calendar.DAY_OF_MONTH, 2);
        System.out.println("          Local Time= " + calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH)
                           + "-" + calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.HOUR_OF_DAY) + ":"
                           + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND));
        calendar.add(Calendar.MONTH, 2);
        System.out.println("          Local Time= " + calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH)
                           + "-" + calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.HOUR_OF_DAY) + ":"
                           + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND));
    }

    /**
     * 夏时令测试
     */
    public static void testDaylightSavingTime() {
        try {
            System.out.println("====> 夏时令 (Daylight Saving Time)");
            SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_DATETIME);
            // dateFormat.setTimeZone(TimeZone.getTimeZone("America/New_York"));

            String dateStr = "1986-05-06 22:17:14";
            Date tmpDate = dateFormat.parse(dateStr);
            System.out.println("tmpDate = " + tmpDate);
            // 夏令时会导致某一天多出一个小时，或者少出一个小时。
            System.out.println("==== 测试1 - 1986年5月4号0点不见了");
            Date d = dateFormat.parse("1986-5-4 0:0:0");
            System.out.println(d + " //中国在当天还在使用夏令时，时间被拨快了1个小时（1986年5月4号0点不见了）");

            System.out.println("==== 测试2 - 预期的0点也没有了");
            String sTime = "1991-04-07 00:00:00";
            Date time = dateFormat.parse(sTime);
            Calendar cd = Calendar.getInstance();
            cd.setTime(time);
            cd.add(Calendar.DATE, 7);
            time = cd.getTime();
            System.out.println(dateFormat.format(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /*
     * 判断一个时区从1970年开始是否使用过夏时令
     */
    public static void testUseDST(TimeZone timeZone) {
        System.out.println("====> testUseDST 判断一个时区ID是否使用过夏时令");
        System.out.println("Time Zone is : " + timeZone.getDisplayName() + " : " + timeZone.getID());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar start = Calendar.getInstance(timeZone);
        start.setTime(new Date(0)); // UTC 1970-01-01
        System.out.println("start=" + df.format(start.getTime())); // will print: start=1970-01-01 08:00:00
        Calendar end = Calendar.getInstance(timeZone);
        // end.add(Calendar.YEAR, 1);
        System.out.println("end=" + df.format(end.getTime()));
        boolean find = false;
        for (long i = start.getTimeInMillis(); i < end.getTimeInMillis(); i = start.getTimeInMillis()) {
            start.add(Calendar.DATE, 1); // add one day
            if ((start.getTimeInMillis() - i) % (24 * 3600 * 1000L) != 0) { // 是否能被24整除
                find = true;
                System.out.println("from " + df.format(new Date(i)) + " to " + df.format(start.getTime()) + " has "
                                   + (start.getTimeInMillis() - i) + "ms" + "[" + (start.getTimeInMillis() - i)
                                   / (3600 * 1000L) + "hours]");
            }

        }
        if (!find) {
            System.out.println("Every day is ok.");
        }
    }
}
