package com.example.mylibrary.utils;

import android.util.Log;

import com.example.mylibrary.application.ApplicationHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xingzy on 2016/4/13.
 */
public class StringUtil {


    public static String stringarraytostring(String[] strs) {
        StringBuilder ids = new StringBuilder();
        ids.append("[");
        for (int i = 0; i < strs.length; i++) {
            ids.append(strs[i]).append(",");
        }
        ids.deleteCharAt(ids.length() - 1);
        ids.append("]");
        return new String(ids);
    }

    /**
     * 判断字符串是否为空
     *
     * @param s
     * @return true表示字符串为null或者"" false表示非空
     */
    public static boolean isEmpty(String s) {
        boolean isEmpty = false;
        if (null == s || "".equals(s.trim())) {
            isEmpty = true;
        }
        return isEmpty;
    }

    /**
     * 判断两字符串是否相同
     *
     * @param s 字符串1
     * @param t 字符串2
     * @return true/false
     */
    public static boolean isSame(String s, String t) {
        boolean isSame = false;
        if (s.equals(t) && !isEmpty(s) && !isEmpty(t)) {
            isSame = true;
        }
        return isSame;
    }

    /**
     * 将字符串形式的数字转换为int型
     *
     * @param msg
     * @return 正确则返回数字，否则返回-1
     */
    public static int getNum(String msg) {
        Pattern p = Pattern.compile("[0-9\\.]+");
        Matcher m = p.matcher(msg);
        if (m.find()) {
            return Integer.parseInt(m.group());
        } else {
            return -1;
        }
    }

    /**
     * 极光推送设置alias
     *
     * @param userId
     * @return
     */
    public static String userId2Alias(String userId) {
        String alias = userId.replaceAll("-", "_").replaceFirst("user", "usr");
        return alias;
    }

    /**
     * 判断字符串是否为合法的手机号
     *
     * @param mobiles
     * @return true/false
     */
    public static boolean isMobileNO(String mobiles) {
        if (null != mobiles) {
            Pattern p = Pattern.compile("^\\d{11}$");
            Matcher m = p.matcher(mobiles);
            return m.matches();
        } else {
            Log.e("TAG", "手机号码为空");
            ToastUtil.show(ApplicationHelper.getInstance().getApplicationContext(), "手机号码为空");
            return false;
        }
    }

    /**
     * 判断字符串是否为合法的电子邮箱
     *
     * @param email
     * @return true/false
     */
    public static boolean isEmail(String email) {
        String str = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 获取文件后缀
     *
     * @param fileName
     * @return
     */
    public static String getFileSuffix(String fileName) {
        if (!isEmpty(fileName)) {
            return fileName.substring(fileName.lastIndexOf("."));
        }
        return null;
    }

    /**
     * 获取某字符串首字母
     *
     * @param s
     * @return
     */
    public static String getInitial(String s) {
        String initial = "";
        if (!isEmpty(s)) {
            initial = s.substring(0, 1);
        }
        return initial;
    }

    /**
     * 获取lastname首字母+.
     *
     * @param s
     * @return
     */
    public static String getLastName(String s) {
        return " " + getInitial(s) + ".";
    }
}
