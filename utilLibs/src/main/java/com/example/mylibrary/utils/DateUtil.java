package com.example.mylibrary.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/12/1.
 */
public class DateUtil {
    static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static SimpleDateFormat format_HM = new SimpleDateFormat("yyyy/MM/dd HH:mm");

    /**
     * 获取几天后日期
     *
     * @param day
     * @return
     */
    public static Date getAfterDate(int day) {
        long result;
        Calendar current = Calendar.getInstance();
        result = current.getTimeInMillis() + day * 24 * 60 * 60 * 1000;
        return new Date(result);
    }
    /**
     * 获取几小时后日期
     *
     * @param hour
     * @return
     */
    public static Date getAfterHour(Date date,int hour) {
        long result;
        Calendar current = Calendar.getInstance();
        current.setTime(date);
        result = current.getTimeInMillis() + hour * 60 * 60 * 1000;
        return new Date(result);
    }

    public static String getTime(Date date) {
        if (date == null) {
            return "";
        }
        return format.format(date);
    }
    public static String getTime_HM(Date date) {
        if (date == null) {
            return "";
        }
        return format_HM.format(date);
    }

    public static String getTime(long date) {
        if (date == 0) {
            return "";
        }
        return format.format(new Date(date));
    }

    public static String getTime_HM(long date) {
        if (date == 0) {
            return "";
        }
        return format_HM.format(new Date(date));
    }

    public static String getTime(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            return "";
        }
        return format.format(startDate) + " - " + format.format(endDate);
    }
    public static String getTime(long start, long end) {
        Date StartDate = new Date(start);
        Date endDate = new Date(end);
        return format.format(StartDate) + " - " + format.format(endDate);
    }
}
