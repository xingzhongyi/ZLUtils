package com.example.mylibrary.utils;

import android.util.Log;

import com.example.mylibrary.thread.ThreadPoolExeManager;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Administrator on 2016/5/31.
 */
public class L {

    public static boolean isDebug = true;// 是否需要打印bug，可以在application的onCreate函数里面初始化
    private static final String TAG = "cloudPos";

    /**
     * 日志队列，用于输出到文件
     */
    private static BlockingQueue<String> logMessageQueue = new ArrayBlockingQueue(50);

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        putLog(TAG, msg);
        if (isDebug)
            Log.i(TAG, msg);
    }

    public static void d(String msg) {
        if (isDebug)
            Log.d(TAG, msg);
    }

    public static void e(String msg) {
        putLog(TAG, msg);
        if (isDebug)
            Log.e(TAG, msg);
    }

    public static void v(String msg) {
        if (isDebug)
            Log.v(TAG, msg);
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        putLog(tag, msg);
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            Log.d(tag, msg);
    }

    public static void e(String tag, String msg) {
        putLog(tag, msg);
        if (isDebug)
            Log.e(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            Log.v(tag, msg);
    }

    /**
     * 储存日志信息到队列
     */
    public static void putLog(String tag, String log) {
        try {
            String creattime = DateUtil.getTime(System.currentTimeMillis());
            logMessageQueue.put(creattime + ": " + tag + ": " + log);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从队列取出日志信息
     */
    public static String getLog() {
        String log = null;
        try {
            log = logMessageQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return log;
    }

    static Runnable runnable = new Runnable() {
        @Override
        public void run() {
            String rootdir = FileUtil.getLogFileDir();
            while (true) {
                String ymd = DateUtil.getTime_YMD(System.currentTimeMillis())+".txt";
                FileUtil.save(rootdir, ymd, getLog());
            }
        }
    };

    public static void startOpenLogPrintThread() {
        ThreadPoolExeManager.getInstance().execute(runnable);
    }

}
