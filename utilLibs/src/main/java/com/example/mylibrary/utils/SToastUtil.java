package com.example.mylibrary.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/6/2.
 */
public class SToastUtil {


    public static Toast toast;

    public static void toast(Context context, String msg){
        if(toast==null){
            toast=Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        }else{
            toast.setText(msg);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    public static void toastLong(Context context,String msg){
        if(toast==null){
            toast=Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        }else{
            toast.setText(msg);
            toast.setDuration(Toast.LENGTH_LONG);
        }
        toast.show();
    }



}
