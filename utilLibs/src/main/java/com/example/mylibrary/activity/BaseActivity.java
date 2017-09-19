package com.example.mylibrary.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.mylibrary.R;
import com.example.mylibrary.view.CustomDialog;

import org.xutils.x;

/**
 * Created by xingzy on 2016/4/13.
 */
public class BaseActivity extends FragmentActivity {

    protected String TAG=getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    public CustomDialog pd;

    public void showPd() {
        if (pd == null) {
//            pd = new ProgressDialog(this);
            pd = new CustomDialog(this, R.style.CustomDialog);
            pd.setCancelable1(false);
            pd.setCanceledOnTouchOutside(false);
            pd.show();
        } else if (!pd.isShowing()) {
            pd.show();
        }
    }

    public void hidePd() {
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
    }
}
