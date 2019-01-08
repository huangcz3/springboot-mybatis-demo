package com.neo.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static final String FORMAT_YYYY_MM = "yyyy-MM-dd";
    public static final String FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static String date2Str(Date date) {
        return date2Str(date, FORMAT_YYYY_MM_DD_HH_MM_SS);
    }

    public static String date2Str(Date date, String format) {
        if ((format == null) || format.equals("")) {
            format = FORMAT_YYYY_MM_DD_HH_MM_SS;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if (date != null) {
            return sdf.format(date);
        }
        return "";
    }

    public static Date dateFormate(Date date) {
        if (date != null) {
            return str2datetime(date2Str(date));
        } else {
            return str2datetime(date2Str(new Date()));
        }
    }

    public static Date str2date(String str) {
        DateFormat format = new SimpleDateFormat(FORMAT_YYYY_MM);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (Exception e) {
            date = null;
        }

        return date;
    }

    public static Date str2datetime(String str) {
        DateFormat format = new SimpleDateFormat(FORMAT_YYYY_MM_DD_HH_MM_SS);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (Exception e) {
            date = null;
        }

        return date;
    }

    public static String getSeqString() {
        SimpleDateFormat fm = new SimpleDateFormat("yyyyMMddHHmmsszzz"); // "yyyyMMdd G
        return fm.format(new Date());
    }
}
