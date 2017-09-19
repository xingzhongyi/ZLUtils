package com.example.mylibrary.utils;


import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

    private static String TIME_DATE_TIME_STRING_FORMAT_FILE_NAME = "yyyy-MM-dd_HH-mm-ss";// 由于文件名不能包含冒号，所以时间用-隔开
    private static String TIME_DATE_TIME_STRING_FORMAT_ = "yyyy-MM-dd_HH:mm:ss";
    private static String TIME_DATE_STRING_FORMAT = "yyyy-MM-dd";//
    private static String TIME_STRING_FORMAT = "HH:mm";//

    public static final int TIME_NOW_NIGHT = 1;// 晚上
    public static final int TIME_NOW_MOON = 2;// 中午

    public static long getCurrentTimeMillis() {
        return (System.currentTimeMillis() / 1000) * 1000;
    }


    public static long getCurrentTimeMillisInner() {
        return System.currentTimeMillis();
    }

    public static long dateTimeStringToLong(String dateTime) {
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_DATE_TIME_STRING_FORMAT_FILE_NAME);
        Date dt2;
        try {
            dt2 = sdf.parse(dateTime);
            return dt2.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static String longToDateTimeFileNameString(long dateTimeMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_DATE_TIME_STRING_FORMAT_FILE_NAME);
        Date dt = new Date(dateTimeMillis);
        return sdf.format(dt);
    }

    public static String longToDateTimeNormalString(long dateTimeMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_DATE_TIME_STRING_FORMAT_);
        Date dt = new Date(dateTimeMillis);
        return sdf.format(dt);
    }

    public static long dateStringToLong(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_DATE_STRING_FORMAT);
        Date dt2;
        try {
            dt2 = sdf.parse(date);
            return dt2.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static String getNowTime() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);
        return time;
    }

    public static String longToDateString(long dateMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_DATE_STRING_FORMAT);
        Date dt = new Date(dateMillis);
        return sdf.format(dt);
    }
    public static String longToDateStringYMD(long dateMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        Date dt = new Date(dateMillis);
        return sdf.format(dt);
    }


    public static String longToTime(long dateTimeMillis) {

        SimpleDateFormat sdf = new SimpleDateFormat(TIME_DATE_TIME_STRING_FORMAT_);
        Date dt = new Date(dateTimeMillis);
        String result = "";

        try {
            result = sdf.format(dt).split("_")[1];
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 在12:00--13:59
     * <p/>
     * 以及19:00之后
     *
     * @return
     * @Description:
     * @see:
     * @since:
     * @author: zhuanggy
     * @date:2012-11-22
     */
    public static int inTime() {
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (hour >= 18) {
            return TIME_NOW_NIGHT;
        } else if (hour == 12 || hour == 13) {
            return TIME_NOW_MOON;
        } else {
            return -1;
        }

    }

    /**
     * 将时间转为特定的格式 如: 1 转为 01
     *
     * @param mmm
     * @return
     */
    public static String getformatString(int mmm) {
        if (mmm < 10) {
            if (mmm == 0) {
                return "00";
            } else {
                return "0" + mmm;
            }
        } else {
            return "" + mmm;
        }
    }

    /**
     * 今天当天不显示日期，其余显示
     *
     * @param time
     * @return
     */
    public static String long2data(long time) {
        Calendar today = Calendar.getInstance();    //今天
        Calendar current = Calendar.getInstance();
        current.setTimeInMillis(time);
        Date date = current.getTime();
        SimpleDateFormat sf = new SimpleDateFormat("MM/dd HH:mm");
        if (today.get(Calendar.YEAR) == current.get(Calendar.YEAR)
                && today.get(Calendar.MONTH) == current.get(Calendar.MONTH)
                && today.get(Calendar.DAY_OF_MONTH) == current.get(Calendar.DAY_OF_MONTH)) {
            sf = new SimpleDateFormat("HH:mm");
            return sf.format(date);
        }
        return sf.format(date);
    }

    /**
     * @param time
     * @return
     */
    public static String long2data2(long time) {
        Calendar today = Calendar.getInstance();    //今天
        Calendar current = Calendar.getInstance();
        current.setTimeInMillis(time);
        Date date = current.getTime();
        if (today.get(Calendar.YEAR) == current.get(Calendar.YEAR)) {
            if (today.get(Calendar.MONTH) == current.get(Calendar.MONTH)) {
                if (today.get(Calendar.WEEK_OF_MONTH) == current.get(Calendar.WEEK_OF_MONTH)) {
                    if (today.get(Calendar.DAY_OF_WEEK_IN_MONTH) == current.get(Calendar.DAY_OF_WEEK_IN_MONTH)) {
                        return "今天";
                    } else {
                        int day = today.get(Calendar.DAY_OF_WEEK_IN_MONTH) - current.get(Calendar.DAY_OF_WEEK_IN_MONTH);
                        if (day > 0) {
                            return day + "天前";
                        } else {
                            return day + "天后";
                        }
                    }
                } else {
                    int week = today.get(Calendar.WEEK_OF_MONTH) - current.get(Calendar.WEEK_OF_MONTH);
                    if (week > 0) {
                        return week + "周前";
                    } else {
                        return week + "周后";
                    }
                }
            } else {
                int month = today.get(Calendar.MONTH) - current.get(Calendar.MONTH);
                if (month > 0) {
                    return month + "月前";
                } else {
                    return month + "月后";
                }
            }
        } else {
            int year = today.get(Calendar.YEAR) - current.get(Calendar.YEAR);
            if (year > 0) {
                return year + "年前";
            } else {
                return year + "年后";
            }
        }
    }

    /**
     * @param time
     * @return
     */
    public static String long2data3(long time) {
        Calendar today = Calendar.getInstance();    //今天
        Calendar current = Calendar.getInstance();
        current.setTimeInMillis(time);
        Date date = current.getTime();
        if (today.get(Calendar.YEAR) == current.get(Calendar.YEAR)) {
            if (today.get(Calendar.MONTH) == current.get(Calendar.MONTH)) {
                if (today.get(Calendar.WEEK_OF_MONTH) == current.get(Calendar.WEEK_OF_MONTH)) {
                    if (today.get(Calendar.DAY_OF_MONTH) == current.get(Calendar.DAY_OF_MONTH)) {
                        return "今天";
                    } else {
                        int day = today.get(Calendar.DAY_OF_MONTH) - current.get(Calendar.DAY_OF_MONTH);
                        if (day > 0) {
                            if (day == 1) {
                                return "昨天";
                            } else if (day > 1 && day < 3) {
                                return "2天前";
                            } else {
                                return "3天前";
                            }
                        } else {
                            return day + "天后";
                        }
                    }
                } else {
                    int week = today.get(Calendar.WEEK_OF_MONTH) - current.get(Calendar.WEEK_OF_MONTH);
                    if (week > 0) {
                        if (week < 3) {
                            return week + "周前";
                        } else {
                            return "2周前";
                        }
                    } else {
                        return week + "周后";
                    }
                }
            } else {
                int month = today.get(Calendar.MONTH) - current.get(Calendar.MONTH);
                if (month > 0) {
                    if (month == 1) {
                        return "1月前";
                    } else if (month >= 3 && month < 6) {
                        return "3月前";
                    } else {
                        return "半年前";
                    }
                } else {
                    return month + "月后";
                }
            }
        } else {
            int year = today.get(Calendar.YEAR) - current.get(Calendar.YEAR);
            if (year > 0) {
                return "1年前";
            } else {
                return year + "年后";
            }
        }
    }

    /**
     * 除了今天和昨天其余显示日期
     * @param time
     * @return
     */
    public static String long2data4(long time) {
        Calendar today = Calendar.getInstance();    //今天
        Calendar current = Calendar.getInstance();
        current.setTimeInMillis(time);
        SimpleDateFormat sf = new SimpleDateFormat("M/d");
        Date date = current.getTime();
        if (today.get(Calendar.YEAR) == current.get(Calendar.YEAR)) {
            int day = today.get(Calendar.DAY_OF_YEAR) - current.get(Calendar.DAY_OF_YEAR);
            if (day == 0) {
                return "今天";
            } else if (day == 1) {
                return "昨天";
            }
//            else if (day == 2) {
//                return "2天前";
//            } else if (day >= 3 && day < 7) {
//                return "3天前";
//            } else if (day >= 7 && day < 14) {
//                return "一周前";
//            } else if (day >= 14 && day < 30) {
//                return "2周前";
//            } else if (day >= 30 && day < 90) {
//                return "一月前";
//            } else if (day >= 90 && day < 180) {
//                return "3月前";
//            } else if (day >= 180 && day < 365) {
//                return "半年前";
//            }
            else {
                return sf.format(new Date(time));
            }
        } else {
            int year = today.get(Calendar.YEAR) - current.get(Calendar.YEAR);
            int newYear_day = today.get(Calendar.DAY_OF_YEAR);
            int oldYear_day = current.getActualMaximum(Calendar.DATE) - current.get(Calendar.DAY_OF_YEAR);
            if (year > 0) {
                int day = newYear_day + oldYear_day + 365 * (year - 1);
                if (day == 0) {
                    return "今天";
                } else if (day == 1) {
                    return "昨天";
                }
//                else if (day == 2) {
//                    return "2天前";
//                } else if (day >= 3 && day < 7) {
//                    return "3天前";
//                } else if (day >= 7 && day < 14) {
//                    return "一周前";
//                } else if (day >= 14 && day < 30) {
//                    return "2周前";
//                } else if (day >= 30 && day < 90) {
//                    return "一月前";
//                } else if (day >= 90 && day < 180) {
//                    return "3月前";
//                } else if (day >= 180 && day < 365) {
//                    return "半年前";
//                }
                else {
                    return sf.format(new Date(time));
                }
            } else {
                return sf.format(new Date(time));
            }
        }
    }

    /**
     * 任务简洁显示，比如6/08直接显示6/8
     * add by zijun
     *
     * @param time
     * @return
     */
    public static String tasktime(long time) {
        Calendar today = Calendar.getInstance();    //今天
        Calendar current = Calendar.getInstance();
        current.setTimeInMillis(time);
        Date date = current.getTime();
        SimpleDateFormat sf = new SimpleDateFormat("M/d HH:mm");
        if (today.get(Calendar.YEAR) == current.get(Calendar.YEAR)
                && today.get(Calendar.MONTH) == current.get(Calendar.MONTH)
                && today.get(Calendar.DAY_OF_MONTH) == current.get(Calendar.DAY_OF_MONTH)) {
            sf = new SimpleDateFormat("HH:mm");
            return sf.format(date);
        }

//        Calendar dateCalendar = Calendar.getInstance();
//        dateCalendar.setTime(date);
//        Date now = new Date();
//        Calendar targetCalendar = Calendar.getInstance();
//        targetCalendar.setTime(now);
//        targetCalendar.set(Calendar.HOUR_OF_DAY, 0);
//        targetCalendar.set(Calendar.MINUTE, 0);
//        targetCalendar.add(Calendar.DATE, 1);
//        if (dateCalendar.before(targetCalendar)) {
//            sf = new SimpleDateFormat("HH:mm");
//            return sf.format(date);
//        }

//        String timeStr = sf.format(date);
//        if (timeStr.charAt(0) == '0') {
//            timeStr = removeCharAt(timeStr, 0);
//            if (timeStr.charAt(2) == '0') {
//                timeStr = removeCharAt(timeStr, 2);
//            }
//        } else {
//            if (timeStr.charAt(3) == '0') {
//                timeStr = removeCharAt(timeStr, 3);
//            }
//        }
        return sf.format(date);
    }

    public static String removeCharAt(String s, int pos) {
        return s.substring(0, pos) + s.substring(pos + 1);
    }

    public static String removeZero(String time) {
        if (time.charAt(5) == '0') {
            time = removeCharAt(time, 5);
            if (time.charAt(7) == '0') {
                time = removeCharAt(time, 7);
            }
        } else {
            if (time.charAt(8) == '0') {
                time = removeCharAt(time, 8);
            }
        }
        return time;
    }

    public static long getTimestamp(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d HH:mm");
        Date dt2;
        try {
            dt2 = sdf.parse(date);
            return dt2.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static String long2DateString(long dateMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d HH:mm");
        Date dt = new Date(dateMillis);
        return sdf.format(dt);
    }

    public static String memoTime(long time) {
        SimpleDateFormat sf = new SimpleDateFormat("M/d HH:mm");
        Date dt = new Date(time);
//        String timeStr = sf.format(dt);
//        if (timeStr.charAt(0) == '0') {
//            timeStr = removeCharAt(timeStr, 0);
//            if (timeStr.charAt(2) == '0') {
//                timeStr = removeCharAt(timeStr, 2);
//            }
//        } else {
//            if (timeStr.charAt(3) == '0') {
//                timeStr = removeCharAt(timeStr, 3);
//            }
//        }
        return sf.format(dt);
    }


    private String getTime(Date date) {
        String todySDF = "今天 HH:mm";
        String yesterDaySDF = "昨天 HH:mm";
        String otherSDF = "M月d日 HH:mm";
        SimpleDateFormat sfd = null;
        String time = "";
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(date);
        Date now = new Date();
        Calendar targetCalendar = Calendar.getInstance();
        targetCalendar.setTime(now);
        targetCalendar.set(Calendar.HOUR_OF_DAY, 0);
        targetCalendar.set(Calendar.MINUTE, 0);
        if (dateCalendar.after(targetCalendar)) {
            sfd = new SimpleDateFormat(todySDF);
            time = sfd.format(date);
            return time;
        } else {
            targetCalendar.add(Calendar.DATE, -1);
            if (dateCalendar.after(targetCalendar)) {
                sfd = new SimpleDateFormat(yesterDaySDF);
                time = sfd.format(date);
                return time;
            }
        }
        sfd = new SimpleDateFormat(otherSDF);
        time = sfd.format(date);
        return time;
    }


    public static String getTime4task(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/M/d HH:mm");
        return format.format(date);
    }
    public static String date2String1(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yy/M");
        return format.format(date);
    }
    public static String date2String2(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        return format.format(date);
    }
}
