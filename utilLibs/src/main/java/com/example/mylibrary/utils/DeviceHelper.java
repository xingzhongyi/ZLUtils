package com.example.mylibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import com.example.mylibrary.application.ApplicationHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 获取设备信息的类
 * <p/>
 * Created by xingzy on 2016/4/15.
 */
public class DeviceHelper {
    /**
     * 检查网络连接，移动网络 or WIFI or Ethernet
     *
     * @return
     */
    public static boolean isOpenNetwork() {
        return (isMobileNetworkConnected() || isWifiConnected()||isEthernetConnected() );
    }


    /**
     * 检查WIFI是否连接
     *
     * @return 如果连接了返回true，否则返回false
     */
    public static boolean isWifiConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (null == wifiInfo) {
            return false;
        }
        return wifiInfo.isConnected();
    }

    /**
     * 检查网线是否连接
     *
     * @return 如果连接了返回true，否则返回false
     */
    public static boolean isEthernetConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (null == wifiInfo) {
            return false;
        }
        return wifiInfo.isConnected();
    }

    /**
     * 检查手机网络(4G/3G/2G)是否连接
     *
     * @return 如果连接了返回true，否则返回false
     */
    public static boolean isMobileNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobileNetworkInfo = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (null == mobileNetworkInfo) {
            return false;
        }
        return mobileNetworkInfo.isConnected();
    }

    /**
     * 判断SD卡是否存在
     *
     * @return true表示SD卡存在, false表示不存在
     */
    public static boolean hasSDCard() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取设备的电话号码
     *
     * @return 设备的电话号码
     */
    public static String getTelNO() {
        TelephonyManager telephoneManager = (TelephonyManager) getContext()
                .getSystemService(Context.TELEPHONY_SERVICE);
        String telNo = telephoneManager.getLine1Number();
        return telNo;
    }

    /**
     * 获取设备IMEI
     *
     * @return 设备IMEI号
     */
    public static String getMchId() {
        TelephonyManager telephoneManager = (TelephonyManager) getContext()
                .getSystemService(Context.TELEPHONY_SERVICE);
        String mchId = telephoneManager.getDeviceId();
        return mchId;
    }

    /**
     * 获取设备唯一标识码
     * <p>
     * 适用于Android系统2.3版本以上
     *
     * @return 设备唯一标识码
     */
    public static String getUniqueCode() {
        String macAddress = null;
        WifiManager wifiManager =
                (WifiManager) getContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = (null == wifiManager ? null : wifiManager.getConnectionInfo());

        if (!wifiManager.isWifiEnabled()) {
            //必须先打开，才能获取到MAC地址
            wifiManager.setWifiEnabled(true);
            wifiManager.setWifiEnabled(false);
        }
        if (null != info) {
            macAddress = info.getMacAddress();
        }
        return macAddress;
    }

    /**
     * 获得设备的分辨率,返回值为宽度和高度拼成的point类型值
     *
     * @param activity
     * @return Point(width, height)
     */
    public static Point getResolution(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Point currentDpi = new Point(dm.widthPixels, dm.heightPixels);
        return currentDpi;
    }

    /**
     * 获得程序的上下文
     *
     * @return Context 程序的上下文
     */
    private static Context getContext() {
        return ApplicationHelper.getInstance().getApplicationContext();
    }

    /**
     * 获得系统的版本号
     */
    public static String getOSVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return
     */
    public static String getPhoneModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 运营商名称
     *
     * @param context
     * @return
     */
    public static String getNetworkOperatorName(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm == null) {
            return "";
        }
        return "" + tm.getNetworkOperatorName();
    }

    /**
     * 获取当前手机品牌
     *
     * @param context
     * @return
     */
    public static String getPhoneProduct(Context context) {
        Build bd = new Build();
        return bd.PRODUCT;
    }

    /**
     * 获取当前时区
     *
     * @param context
     * @return
     */
    public static String getTimeZone(Context context) {
        TimeZone tz = TimeZone.getDefault();
        String s = tz.getID();
        System.out.println(s);
        return s;
    }

    /**
     * 获取当前日期时间
     *
     * @param context
     * @return
     */
    public static String getDateAndTime(Context context) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 获取网络类型
     */
    public static String getNetWorkType(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            String type = networkInfo.getTypeName();
            if (type.equalsIgnoreCase("ETHERNET")) {
                return "ETHERNET";
            } else if (type.equalsIgnoreCase("WIFI")) {
                return "WIFI";
            } else if (type.equalsIgnoreCase("MOBILE")) {
                NetworkInfo mobileInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                if (mobileInfo != null) {
                    switch (mobileInfo.getType()) {
                        case ConnectivityManager.TYPE_MOBILE:// 手机网络
                            switch (mobileInfo.getSubtype()) {
                                case TelephonyManager.NETWORK_TYPE_UMTS:
                                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                                case TelephonyManager.NETWORK_TYPE_HSDPA:
                                case TelephonyManager.NETWORK_TYPE_HSUPA:
                                case TelephonyManager.NETWORK_TYPE_HSPA:
                                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                                case TelephonyManager.NETWORK_TYPE_EHRPD:
                                case TelephonyManager.NETWORK_TYPE_HSPAP:
                                    return "3G";
                                case TelephonyManager.NETWORK_TYPE_CDMA:
                                case TelephonyManager.NETWORK_TYPE_GPRS:
                                case TelephonyManager.NETWORK_TYPE_EDGE:
                                case TelephonyManager.NETWORK_TYPE_1xRTT:
                                case TelephonyManager.NETWORK_TYPE_IDEN:
                                    return "2G";
                                case TelephonyManager.NETWORK_TYPE_LTE:
                                    return "4G";
                                default:
                                    return "NETTYPE_NONE";
                            }
                    }
                }
            }
        }

        return "NETTYPE_NONE";
    }

    // 获取手机MAC地址
    public static String getMacAddress(Context context) {
        String result = "";
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        result = wifiInfo.getMacAddress();
        // Log.i(TAG, "macAdd:" + result);
        return result;
    }
}
