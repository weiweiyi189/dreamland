package com.example.dreamland.ui.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class dateUtil {

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String getTimeBeforeAccurate(Date date) {
        Date now = new Date();
        long l = now.getTime() - date.getTime();
        long hourBefore = l / (60 * 60 * 1000);
        if (hourBefore < 24) {
            return hourBefore + "小时前";
        }

        long dayBefore = l / (24 * 60 * 60 * 1000);
        if (dayBefore < 7) {
            return hourBefore + "天前";
        }

        return format(date, DATE_TIME_PATTERN);
    }

    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }
}
