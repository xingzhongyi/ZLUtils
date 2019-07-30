package com.example.mylibrary.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.mylibrary.R;

/**
 * Created by Administrator on 2016/7/2.
 */
public class AlertDialogFactory {
    public static void creatAlertDialog(Context context, String title, String content, final DialogChoiceCallback callback) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle(title)
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
                });
        show(context,builder);
    }

    public static void creatAlertDialog(Context context, String title, String content, String sure, String cancle, final DialogChoiceCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle(title)
                .setMessage(content)
                .setCancelable(false)
                .setPositiveButton(sure, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.onSureBtn();

                    }
                }).setNegativeButton(cancle, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.onCancelBtn();
                    }
                });
        show(context,builder);
    }

    public static void creatAlertDialog(Context context, String title, String content, final DialogSureCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle(title)
                .setMessage(content)
                .setCancelable(false)
                .setPositiveButton(context.getString(R.string.dialog_confirm_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.sure();
                    }
                });
        show(context,builder);
    }

    private static void show(Context c, AlertDialog.Builder builder) {
        AlertDialog alertDialog = builder.create();
        Activity activity = alertDialog.getOwnerActivity();
        if (activity == null || activity.isDestroyed() || activity.isFinishing()) {
            return;
        }
        alertDialog.show();
        ;
    }

}
