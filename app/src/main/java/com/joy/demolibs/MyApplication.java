package com.joy.demolibs;

import android.app.Application;

import com.example.mylibrary.application.ApplicationHelper;
import com.example.mylibrary.utils.L;

/**
 * Created by name on 2017/12/11.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationHelper.getInstance().init(this);
        L.startOpenLogPrintThread();//开启打印日志到文件线程
    }
}
