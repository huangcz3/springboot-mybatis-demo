package com.neo.test;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Huangcz
 * @date 2018-11-28 14:23
 * @desc xxx
 */
public class Main {

	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public static Date todayFirstDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Date todayLastDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}


	public static void main(String[] args) {

		String str1 = "a";
		String str2 = "b";
		String str3 = "ab";
		String str4 = str1 + str2;
		String str5 = new String("ab");
		String str6 = new String("ab");


		System.out.println(str5 == str3);//堆内存比较字符串池
		//intern如果常量池有当前String的值,就返回这个值,没有就加进去,返回这个值的引用
		System.out.println(str5.intern() == str3);//引用的是同一个字符串池里的
		System.out.println(str5.intern() == str4);//变量相加给一个新值，所以str4引用的是个新的
		System.out.println(str4 == str3);//变量相加给一个新值，所以str4引用的是个新的


		System.out.println(str5 == str6);

	}

	public static void test(String type) {
		switch (type) {
			case "a":
				System.out.println("aa");
				break;
			case "b":
				System.out.println("bb");
				break;
			default:
				System.out.println("default");
				break;
		}
	}
}
