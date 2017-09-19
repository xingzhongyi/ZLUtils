package com.example.mylibrary.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;


/**
 * Author zhudf
 * Date 2015/6/16
 */
public class ProgressFactory {
    private ProgressDialog progressDialog;
    private static ProgressFactory progressFactory;
    private Context context;

    private ProgressFactory(Context context) {
        this.context=context;
    }

    public static ProgressFactory instance(Context context) {
        if (null == progressFactory) {
        progressFactory=new ProgressFactory(context);
        }
        return progressFactory;
    }

    public  ProgressDialog createProgress( String loadText) {
        if(null==progressDialog){
            progressDialog=DialogFactory.createProgressDialog(context, loadText, new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {

                    dialog.dismiss();
                }
            });
        }
        return progressDialog;
    }

    public  ProgressDialog createDefaultProgress() {
        return createProgress(null);
    }

}
