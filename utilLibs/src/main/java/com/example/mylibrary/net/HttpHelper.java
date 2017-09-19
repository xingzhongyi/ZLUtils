package com.example.mylibrary.net;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.mylibrary.R;
import com.example.mylibrary.utils.DeviceHelper;
import com.example.mylibrary.utils.ProgressFactory;
import com.example.mylibrary.utils.StringUtil;
import com.example.mylibrary.utils.ToastUtil;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 网络请求工具类
 * Created by xingzy on 2016/4/15.
 */
public class HttpHelper {
    private static HttpHelper helper;
    private Context mContext;

    private HttpHelper(Context context) {
        mContext = context;
    }

    public static HttpHelper getInstance(Context context) {
        if (null == helper) {
            helper = new HttpHelper(context);
        }
        helper.mContext = context;//防止第一次构建这个单例的activity销毁掉
        return helper;
    }
    public void okHttp(String url, String method, Map<String, String> head, String json, final HttpCallback callback) {
        if (!DeviceHelper.isOpenNetwork()) {
            ToastUtil.show(mContext, mContext.getString(R.string.network_noConnected));
        } else {
            OkHttpClient client = new OkHttpClient();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, json);
            Request request;
            if (head != null) {
                Headers headers = Headers.of(head);
                request = new Request.Builder().url(url).headers(headers).method(method, body).build();
            } else {
                request = new Request.Builder().url(url).method(method, body).build();
            }
            client.newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onFailed(e);
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    callback.onSuccess(response.body().string());
                }
            });
        }
    }

    public void query_json(String url, HttpCallback callback) {
        query_json_callback(HttpMethod.POST, url, null, null, callback);
    }

    public void query_json(HttpMethod method, String url, HttpCallback callback) {
        query_json_callback(method, url, null, null, callback);
    }

    public void query_json(HttpMethod method, String url, Map<String, String> head, HttpCallback callback) {
        query_json_callback(method, url, head, null, callback);
    }

    public void query_json(HttpMethod method, String url, String jsonString, HttpCallback callback) {
        query_json_callback(method, url, null, jsonString, callback);
    }

    /**
     * 不带菊花，无返回值的网络请求
     *
     * @param method
     * @param url
     * @param head
     * @param jsonString
     * @param callback
     */
    public void query_json(HttpMethod method, String url, Map<String, String> head, String jsonString, final HttpCallback callback) {
        if (!DeviceHelper.isOpenNetwork()) {
            ToastUtil.show(mContext, mContext.getString(R.string.network_noConnected));
        } else {
            RequestParams params = new RequestParams(url);
//            params.setConnectTimeout(150*1000);
            //如果head不为空，添加head
            if (null != head && !head.isEmpty()) {
                Iterator<Map.Entry<String, String>> entries = head.entrySet().iterator();
                while (entries.hasNext()) {
                    Map.Entry<String, String> entry = entries.next();
                    params.addHeader(entry.getKey(), entry.getValue());
                }
            }
            //如果是有参请求，添加参数
            if (!StringUtil.isEmpty(jsonString)) {
                params.setBodyContent(jsonString);
            }
            x.http().request(method, params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    callback.onSuccess(result);
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    callback.onFailed(ex);
                    Log.e("Http error!", ex.toString());
                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });
        }
    }

    /**
     * 不带菊花，带返回值的网络请求
     *
     * @param method
     * @param url
     * @param head
     * @param jsonString
     * @param callback
     */
    public boolean query_json_callback(HttpMethod method, String url, Map<String, String> head, String jsonString, final HttpCallback callback) {
        if (!DeviceHelper.isOpenNetwork()) {
            ToastUtil.show(mContext, mContext.getString(R.string.network_noConnected));
            return false;
        } else {
            RequestParams params = new RequestParams(url);
            //如果head不为空，添加head
            if (null != head && !head.isEmpty()) {
                Iterator<Map.Entry<String, String>> entries = head.entrySet().iterator();
                while (entries.hasNext()) {
                    Map.Entry<String, String> entry = entries.next();
                    params.addHeader(entry.getKey(), entry.getValue());
                }
            }
            params.setAsJsonContent(true);
            //如果是有参请求，添加参数
            if (!StringUtil.isEmpty(jsonString)) {
                params.setBodyContent(jsonString);
            }
            x.http().request(method, params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    callback.onSuccess(result);
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    callback.onFailed(ex);
                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });
            return true;
        }
    }

    /**
     * @param method：网络请求类型
     * @param url：地址
     * @param head：网络请求头
     * @param body：参数
     * @param callback：回调
     */
    public void query_Map(HttpMethod method, String url, Map<String, String> head, Map<String, String> body, final HttpCallback callback) {
        if (!DeviceHelper.isOpenNetwork()) {
            ToastUtil.show(mContext, mContext.getString(R.string.network_noConnected));
        } else {
            RequestParams params = new RequestParams(url);
            //如果head不为空，添加head
            if (head != null && !head.isEmpty()) {
                Iterator<Map.Entry<String, String>> entries = head.entrySet().iterator();
                while (entries.hasNext()) {
                    Map.Entry<String, String> entry = entries.next();
                    params.addHeader(entry.getKey(), entry.getValue());
                }
            }
            //如果是有参请求，添加参数
            if (body != null && !body.isEmpty()) {
                Iterator<Map.Entry<String, String>> entries = body.entrySet().iterator();
                while (entries.hasNext()) {
                    Map.Entry<String, String> entry = entries.next();
                    params.addBodyParameter(entry.getKey(), entry.getValue());
                }
            }
            x.http().request(method, params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    callback.onSuccess(result);
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    callback.onFailed(ex);
                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });
        }
    }

    /**
     * 流文件下载，支持重定向，不支持暂停，没有进度回调
     *
     * @param url：文件流下载地址
     * @param filePath：存储文件的文件绝对路径
     */
    public void loadFileStream(final String url, final String filePath) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL Url = new URL(url);
                    HttpURLConnection conn = (HttpURLConnection) Url.openConnection();//基于HTTP协议的连接对象
                    conn.setConnectTimeout(5000);//请求超时时间 5s
                    conn.setRequestMethod("GET");//请求方式
                    conn.setInstanceFollowRedirects(true);//是成员函数，仅作用于当前函数,设置这个连接是否可以被重定向
                    conn.connect();
                    Log.i("ResponseCode", conn.getResponseCode() + "");
                    if (conn.getResponseCode() == 307) {
                        URL url = conn.getURL();
                        conn = (HttpURLConnection) url.openConnection();
                        conn.setConnectTimeout(5000);//请求超时时间 5s
                        conn.setRequestMethod("GET");//请求方式
                        conn.setInstanceFollowRedirects(true);//是成员函数，仅作用于当前函数,设置这个连接是否可以被重定向
                        conn.connect();
                        Log.i("ResponseCode", conn.getResponseCode() + "");
                    }
                    if (conn.getResponseCode() == 200) {//响应码==200 请求成功
                        InputStream is = conn.getInputStream();
                        int fileSize = conn.getContentLength();// 根据响应获取文件大小
                        if (fileSize <= 0) {
                            throw new RuntimeException("无法获知文件大小");
                        }
                        if (is == null) { // 没有下载流
                            throw new RuntimeException("无法获取文件");
                        }
                        // 创建写入文件内存流,通过此流向目标写文件
                        FileOutputStream FOS = new FileOutputStream(filePath);
                        int numread;
                        byte buf[] = new byte[1024];
                        while ((numread = is.read(buf)) != -1) {
                            FOS.write(buf, 0, numread);
                        }
                        try {
                            is.close();
                        } catch (Exception ex) {
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    File file = new File(filePath);
                    if (file.exists()) {
                        file.delete();
                    }
                }
            }
        }).start();
    }

    /**
     * 带菊花的网络请求
     *
     * @param context
     * @param showProgress
     * @param method
     * @param url
     * @param head
     * @param jsonString
     * @param callback
     */
    public void query_json(final Context context, boolean showProgress, HttpMethod method, String url, Map<String, String> head, String jsonString, final HttpCallback callback) {
        if (!DeviceHelper.isOpenNetwork()) {
            ToastUtil.show(mContext, mContext.getString(R.string.network_noConnected));
        } else {
            if (showProgress) {
                try {
                    ProgressFactory.instance(context).createDefaultProgress().show();
                } catch (Exception e) {

                }
            }
            RequestParams params = new RequestParams(url);
            //如果head不为空，添加head
            if (null != head && !head.isEmpty()) {
                Iterator<Map.Entry<String, String>> entries = head.entrySet().iterator();
                while (entries.hasNext()) {
                    Map.Entry<String, String> entry = entries.next();
                    params.addHeader(entry.getKey(), entry.getValue());
                }
            }
            //如果是有参请求，添加参数
            if (!StringUtil.isEmpty(jsonString)) {
                params.setBodyContent(jsonString);
            }
            x.http().request(method, params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    callback.onSuccess(result);
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    callback.onFailed(ex);
                }

                @Override
                public void onCancelled(CancelledException cex) {
                }

                @Override
                public void onFinished() {
                    try {
                        ProgressFactory.instance(context).createDefaultProgress().dismiss();
                    } catch (Exception e) {

                    }
                }
            });
        }
    }

    /**
     * 带菊花的网络请求
     *
     * @param context
     * @param showProgress
     * @param method
     * @param url
     * @param head
     * @param jsonString
     * @param callback
     */
    public boolean query_json_callBack(final Context context, boolean showProgress, HttpMethod method, String url, Map<String, String> head, String jsonString, final HttpCallback callback) {
        if (!DeviceHelper.isOpenNetwork()) {
            ToastUtil.show(mContext, mContext.getString(R.string.network_noConnected));
            return false;
        } else {
            if (showProgress) {
                try {
                    ProgressFactory.instance(context).createDefaultProgress().show();
                } catch (Exception e) {

                }
            }
            RequestParams params = new RequestParams(url);
            //如果head不为空，添加head
            if (null != head && !head.isEmpty()) {
                Iterator<Map.Entry<String, String>> entries = head.entrySet().iterator();
                while (entries.hasNext()) {
                    Map.Entry<String, String> entry = entries.next();
                    params.addHeader(entry.getKey(), entry.getValue());
                }
            }
            //如果是有参请求，添加参数
            if (!StringUtil.isEmpty(jsonString)) {
                params.setBodyContent(jsonString);
            }
            x.http().request(method, params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    callback.onSuccess(result);
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    callback.onFailed(ex);
                }

                @Override
                public void onCancelled(CancelledException cex) {
                }

                @Override
                public void onFinished() {
                    try {
                        ProgressFactory.instance(context).createDefaultProgress().dismiss();
                    } catch (Exception e) {

                    }
                }
            });
        }
        return true;
    }

    private Handler handler;
    private int fileSize;
    private byte buf[] = new byte[1024];
    private int downLoadFilePosition = 0;
    private int numread;

    private final int Down_ERROR = 0;
    private final int Down_START = 1;
    private final int Down_LOADING = 2;
    private final int Down_FINISH = 3;

    private boolean isDownLoaded = false;
    private boolean isStop = false;

    /**
     * 流文件下载，支持重定向，支持暂停，进度回调
     *
     * @param url
     * @param filePath
     */
    public void loadFileStream(final String url, final String filePath, final DownloadProgressCallback callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL Url = new URL(url);
                    HttpURLConnection conn = (HttpURLConnection) Url.openConnection();//基于HTTP协议的连接对象
                    conn.setConnectTimeout(5000);//请求超时时间 5s
                    conn.setRequestMethod("GET");//请求方式
                    conn.setInstanceFollowRedirects(true);//是成员函数，仅作用于当前函数,设置这个连接是否可以被重定向
                    conn.connect();
                    Log.i("ResponseCode", conn.getResponseCode() + "");
                    if (conn.getResponseCode() == 307) {
                        URL url = conn.getURL();
                        conn = (HttpURLConnection) url.openConnection();
                        conn.setConnectTimeout(5000);//请求超时时间 5s
                        conn.setRequestMethod("GET");//请求方式
                        conn.setInstanceFollowRedirects(true);//是成员函数，仅作用于当前函数,设置这个连接是否可以被重定向
                        conn.connect();
                        Log.i("ResponseCode", conn.getResponseCode() + "");
                    }
                    if (conn.getResponseCode() == 200) {//响应码==200 请求成功
                        InputStream is = conn.getInputStream();
                        fileSize = conn.getContentLength();// 根据响应获取文件大小
                        if (fileSize <= 0) {
                            handler.sendEmptyMessage(Down_ERROR);
                            throw new RuntimeException("无法获知文件大小");
                        }
                        if (is == null) { // 没有下载流
                            handler.sendEmptyMessage(Down_ERROR);
                            throw new RuntimeException("无法获取文件");
                        }
                        handler.sendEmptyMessage(Down_START);
                        // 创建写入文件内存流,通过此流向目标写文件
                        FileOutputStream FOS = new FileOutputStream(filePath);
                        int numread;
                        byte buf[] = new byte[1024];
                        while ((numread = is.read(buf)) != -1) {
                            while (isStop) {
                                Log.e("xzy", "暂停ing");
                            }
                            FOS.write(buf, 0, numread);
                            downLoadFilePosition += numread;
                            handler.sendEmptyMessage(Down_LOADING);
                        }
                        try {
                            is.close();
                            handler.sendEmptyMessage(Down_FINISH);
                        } catch (Exception ex) {
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    File file = new File(filePath);
                    if (file.exists()) {
                        file.delete();
                    }
                }
            }
        }).start();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case Down_START:
                        Log.e("xzy", "Down_START\tfileSize:" + fileSize);
                        callBack.onStart(fileSize);
                        break;
                    case Down_LOADING:
                        Log.e("xzy", "Down_LOADING\tdownLoadFilePosition:" + downLoadFilePosition);
                        callBack.onLoading(downLoadFilePosition, fileSize);
                        break;
                    case Down_FINISH:
                        isDownLoaded = true;
                        Log.e("xzy", "Down_FINISH\tdownLoadFilePosition:" + downLoadFilePosition);
                        callBack.onFinish();
                        break;
                    case Down_ERROR:
                        Log.e("xzy", "Down_ERROR\tdownLoadFilePosition:" + downLoadFilePosition);
                        callBack.onError(downLoadFilePosition);
                        break;
                }
                super.handleMessage(msg);
            }
        };
    }

    /**
     * @param method：网络请求类型
     * @param url：地址
     * @param head：网络请求头
     * @param path：参数
     * @param callback：回调
     */
    public void uploadFile(HttpMethod method, String url, Map<String, String> head, String path, String fileName, final HttpCallback callback) {
        if (null != path) {
            if (!DeviceHelper.isOpenNetwork()) {
                ToastUtil.show(mContext, mContext.getString(R.string.network_noConnected));
            } else {
                RequestParams params = new RequestParams(url);
                //如果head不为空，添加head
                if (!head.isEmpty()) {
                    Iterator<Map.Entry<String, String>> entries = head.entrySet().iterator();
                    while (entries.hasNext()) {
                        Map.Entry<String, String> entry = entries.next();
                        params.addHeader(entry.getKey(), entry.getValue());
                    }
                }
                params.setMultipart(true);
                try {
                    params.addBodyParameter("file", new FileInputStream(new File(path)), "image/*", fileName);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                x.http().request(method, params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        callback.onSuccess(result);
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        callback.onFailed(ex);
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                    }

                    @Override
                    public void onFinished() {
                    }
                });
            }
        }
    }


}