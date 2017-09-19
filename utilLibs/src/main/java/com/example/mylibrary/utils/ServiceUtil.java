package com.example.mylibrary.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * Created by Admin on 2016/5/13.
 */
public class ServiceUtil {
    /**
     * 通过Service的类名来判断是否启动某个服务
     *
     * @param context：service完整路径（带包名）
     * @param serviceName
     * @return
     */
    public static boolean isServiceStarted(Context context, String serviceName) {

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> mServiceList = activityManager.getRunningServices(100);

        for (int i = 0; i < mServiceList.size(); i++) {
            if (serviceName.equals(mServiceList.get(i).service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
