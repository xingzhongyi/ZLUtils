package com.example.mylibrary.application;

import android.app.Application;
import android.content.Context;

import com.example.mylibrary.utils.L;

import org.xutils.BuildConfig;
import org.xutils.x;

/**
 * 所有需要初始化的操作，都放在 init()中
 * Created by xingzy on 2016/4/13.
 */
public class ApplicationHelper {
    private static ApplicationHelper mApplicationHelper;
    private Application mApp;

    private ApplicationHelper() {

    }

    public static ApplicationHelper getInstance() {
        if (null == mApplicationHelper) {
            mApplicationHelper = new ApplicationHelper();
        }
        return mApplicationHelper;
    }

    //如果使用该library必须执行
    public void init(Application application) {
        mApp = application;
        // TODO: 2017/1/4  上线后关闭
        L.isDebug = true;
        //xutils3相关初始化工作
        x.Ext.init(mApp);
        x.Ext.setDebug(BuildConfig.DEBUG);
        //极光推送相关初始化工作
//        JPushInterface.setDebugMode(true);
//        JPushInterface.init(mApp);
    }

    public Context getApplicationContext() {
        if (null != mApp) {
            return mApp.getApplicationContext();
        }
        return null;
    }

}
