package me.gwma.java.basic.date;

import java.util.Locale;

/**
 * 类LocalTest.java的实现描述：TODO 类实现描述
 * 
 * @author Ace Jan 31, 2016 10:21:03 PM
 */
public class LocalTest {

	public static void main(String[] args) {
		// 操作系统默认local对象
		Locale myLocale = Locale.getDefault();
		System.out.println(myLocale.getCountry());
		System.out.println(myLocale.getLanguage());
		System.out.println(myLocale.getDisplayCountry());
		System.out.println(myLocale.getDisplayLanguage());

	}

}
