package com.example.mylibrary.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

/**
 * Created by xingzy on 2016/4/13.
 */
public class AppUtil {
    /**
     * 获取正在运行的应用的ComponentName对象
     * @param context
     * @return 如果有正在前台运行的application，返回componentName，else return null
     */
    private static ComponentName getComponentName(Context context){
        ComponentName componentName=null;
        ActivityManager activityManager =
                (ActivityManager) (context.getSystemService(android.content.Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            componentName = tasks.get(0).topActivity;
        }
        return componentName;
    }
    /**
     * 获取当前应用包名
     *
     * @param context
     * @return
     */
    public static String getPackageName(Context context) {
        return context.getPackageName();
    }

    /**
     * 获取显示在最顶端的activity名称
     *
     * @param context
     * @return
     */
    public static String getTopActivityName(Context context) {
        String topActivityClassName = null;
        ComponentName topActivity = getComponentName(context);
        if (null!=topActivity) {
            topActivityClassName = topActivity.getClassName();
        }
        return topActivityClassName;
    }

    /**
     * 判断当前应用程序处于前台还是后台
     *
     * @param context 上下文
     * @return true：在后台，
     */
    public static boolean isAppBroughtToBackground(final Context context) {
        ComponentName topActivity = getComponentName(context);
        if (null!=topActivity) {
            if (!topActivity.getPackageName().equals(getPackageName(context))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取当前版本号
     * @param context
     * @return
     */
    public static String getCurrentVersion(Context context){
        String versionName = "";
        String versioncode="";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            versioncode = pi.versionCode+"";
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }
    /**
     * 判断是否在主进程
     *
     * @param context
     * @return
     */
    public static boolean inMainProcess(Context context) {
        String packageName = context.getPackageName();
        String processName = AppUtil.getProcessName(context);
        return packageName.equals(processName);
    }

    /**
     * 获取当前进程名
     *
     * @param context
     * @return 进程名
     */
    public static final String getProcessName(Context context) {
        String processName = null;

        // ActivityManager
        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));

        while (true) {
            for (ActivityManager.RunningAppProcessInfo info : am.getRunningAppProcesses()) {
                if (info.pid == android.os.Process.myPid()) {
                    processName = info.processName;

                    break;
                }
            }

            // go home
            if (!TextUtils.isEmpty(processName)) {
                return processName;
            }

            // take a rest and again
            try {
                Thread.sleep(100L);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
