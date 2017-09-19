package com.example.mylibrary.thread;

import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 自定义 无返回值线程池
 * Created by Administrator on 2016/7/19.
 */
public class ThreadPoolExeManager {
    private static ThreadPoolExeManager manager;
    private BlockingQueue<Runnable> queue;//等待队列
    private ThreadPoolExecutor executor;//线程池

    private ThreadPoolExeManager() {
        queue = new ArrayBlockingQueue<Runnable>(20);
        executor = new ThreadPoolExecutor(3, 10, 60, TimeUnit.MILLISECONDS, queue, new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                Log.e("ThreadPoolExeManager", executor.toString());
            }
        });
    }

    public static ThreadPoolExeManager getInstance() {
        if (null == manager) {
            manager = new ThreadPoolExeManager();
        }
        return manager;
    }

    public void execute(Runnable runnable) {
        if (null != runnable) {
            executor.execute(runnable);
        }
    }
}
