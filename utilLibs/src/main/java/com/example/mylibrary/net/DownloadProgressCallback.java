package com.example.mylibrary.net;

/**
 * Created by Admin on 2016/5/9.
 */
public interface DownloadProgressCallback {
    public void onStart(int fileSize);
    public void onLoading(int currentSize,int fileSize );
    public void onError(int currentSize);
    public void onFinish();
}
