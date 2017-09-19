package com.example.mylibrary.utils;

import java.text.NumberFormat;

/**
 * 数字格式化工具类
 * Created by Administrator on 2016/7/27.
 */
public class NumberFormatUtil {
    /**
     * 浮点数转化百分比，仅保留整数位
     * @param value：浮点数
     * @return
     */
    public static String formatPercent(double value) {
        return formatPercent(value, 0);
    }

    /**
     * 浮点数转化百分比，
     *
     * @param value：浮点数
     * @param number：保留的小数位
     * @return
     */
    public static String formatPercent(double value, int number) {
        NumberFormat percentInstance = NumberFormat.getPercentInstance();
        if (number == 0) {
            percentInstance.setParseIntegerOnly(true);
        } else {
            percentInstance.setMaximumFractionDigits(number);
        }
        return percentInstance.format(value);
    }
}
