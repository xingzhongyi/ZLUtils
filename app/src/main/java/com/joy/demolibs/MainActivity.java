package com.joy.demolibs;

import android.os.Bundle;

import com.example.mylibrary.activity.BaseActivity;
import com.example.mylibrary.utils.DeviceHelper;
import com.example.mylibrary.utils.L;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean openNetwork = DeviceHelper.isOpenNetwork();
        L.e("xxx", openNetwork + "");
        showPd();

    }
}
