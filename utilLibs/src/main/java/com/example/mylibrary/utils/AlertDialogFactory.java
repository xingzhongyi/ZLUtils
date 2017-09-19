package com.example.mylibrary.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.mylibrary.R;

/**
 * Created by Administrator on 2016/7/2.
 */
public class AlertDialogFactory {
    public static void creatAlertDialog(Context context, String title, String content, final DialogChoiceCallback callback){
        new AlertDialog.Builder(context).setTitle(title)
                .setMessage(content)
                .setCancelable(false)
                .setPositiveButton(context.getString(R.string.dialog_confirm_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.onSureBtn();

                    }
                }).setNegativeButton(context.getString(R.string.dialog_cancel_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.onCancelBtn();
                    }
                }).show();
    }
    public static void creatAlertDialog(Context context,String title, String content, final DialogSureCallback callback){
        new AlertDialog.Builder(context).setTitle(title)
                .setMessage(content)
                .setCancelable(false)
                .setPositiveButton(context.getString(R.string.dialog_confirm_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.sure();
                    }
                }).show();
    }
}
