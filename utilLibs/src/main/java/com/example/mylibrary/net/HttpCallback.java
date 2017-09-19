package com.example.mylibrary.net;

/**
 * Created by Admin on 2016/4/15.
 */
public interface HttpCallback {
    void onSuccess(String result);

    void onFailed(Throwable ex);
}
