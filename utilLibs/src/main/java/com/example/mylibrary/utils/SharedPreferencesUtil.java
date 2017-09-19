package com.example.mylibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mylibrary.application.ApplicationHelper;

/**
 * SharedPreferences工具类
 * 缓存名字为包名，权限为私有
 * Created by Admin on 2016/5/16.
 */
public class SharedPreferencesUtil {
    private static SharedPreferencesUtil preferencesUtil;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private SharedPreferencesUtil() {
    }

    public static SharedPreferencesUtil getInstance() {
        if (null == preferencesUtil) {
            preferencesUtil = new SharedPreferencesUtil();
        }
        return preferencesUtil;
    }

    /**
     * 获取preferences对象
     *
     * @return
     */
    public SharedPreferences getPreferences() {
        if (null == preferences) {
            Context context = ApplicationHelper.getInstance().getApplicationContext();
            preferences = context.getSharedPreferences(AppUtil.getPackageName(context), 0);
        }
        return preferences;
    }

    /**
     * 获取editor对象
     * @return
     */
    public SharedPreferences.Editor getEditor(){
        if(null==editor){
            editor=preferences.edit();
        }
        return editor;
    }
}
