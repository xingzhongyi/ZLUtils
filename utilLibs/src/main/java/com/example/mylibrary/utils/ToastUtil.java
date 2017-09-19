package com.example.mylibrary.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

/**
 * Toast工具类，支持自定义位置和布局
 * <p>
 * Created by Admin on 2016/4/15.
 */
public class ToastUtil {

    public static void show(Context context, String text) {
        show(context, text, null, Gravity.BOTTOM, 0, 0);
    }

    public static void show(Context context, String text, int gravity) {
        show(context, text, null, gravity, 0, 0);
    }

    public static void show(Context context, String text, int gravity, int xOffset, int yOffset) {
        show(context, text, null, gravity, xOffset, yOffset);
    }

    public static void show(Context context, View view) {
        show(context, null, view, Gravity.TOP, 0, 0);
    }

    public static void show(Context context, View view, int gravity) {
        show(context, null, view, gravity, 0, 0);
    }

    public static void show(Context context, View view, int gravity, int xOffset, int yOffset) {
        show(context, null, view, gravity, xOffset, yOffset);
    }

    public static void show(Context context, String text, View view, int gravity, int xOffset, int yOffset) {
        show(context,text,view,gravity,xOffset,yOffset,false);
    }

    public static void show(Context context, String text, View view, int gravity, int xOffset, int yOffset, boolean time) {
        try {
            Toast toast = new Toast(context);
            if (null != view) {
                toast.setView(view);
            } else {
                toast=Toast.makeText(context,text,Toast.LENGTH_SHORT);
            }
            if (time) {
                toast.setDuration(Toast.LENGTH_LONG);
            } else {
                toast.setDuration(Toast.LENGTH_SHORT);
            }
            toast.setGravity(gravity, xOffset, yOffset);
            toast.show();
        }catch (RuntimeException e){

        }

    }


}
