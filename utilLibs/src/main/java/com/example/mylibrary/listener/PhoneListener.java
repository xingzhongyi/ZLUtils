package com.example.mylibrary.listener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.mylibrary.utils.FileUtil;
import com.example.mylibrary.utils.TimeUtil;

import java.io.File;

/**
 * 当电话的呼叫状态发生变化的时候调用的方法
 * Created by Admin on 2016/5/13.
 */
public class PhoneListener extends PhoneStateListener {
    private String TAG = getClass().getSimpleName();
    private String income;
    private MediaRecorder mediaRecorder;//声明录音机
    private int type = 0;
    private String starttime = "";
    private String endtime = "";
    private String FileName = "";

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        super.onCallStateChanged(state, incomingNumber);
        Log.e(TAG, "电话号码：" + incomingNumber + "\t状态：" + state);
        try {
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE://空闲状态。
                    if (mediaRecorder != null) {
                        //8.停止捕获
                        mediaRecorder.stop();
                        //9.释放资源
                        mediaRecorder.release();
                        mediaRecorder = null;
                        Log.e(TAG, "挂断" + income);
                        endtime = TimeUtil.getNowTime() + "";
                        if (type == 0) {
                            Log.i(TAG, "type=2" + "去电挂断" + income + "开始" + starttime + "结束时间" + endtime);
//                            send(FileName, income, 2, starttime, endtime);
//                            sendFile(FileName,FileName);
                        } else if (type == 4) {
                            Log.i(TAG, "type=1来电挂断" + income + income + "开始" + starttime + "结束时间" + endtime);
//                            send(FileName, income, 1, starttime, endtime);
//                            sendFile(FileName,FileName);
                        }
                        FileName = "";
                        type = 0;
                        starttime = "";
                        endtime = "";
                        //TODO 这个地方你可以将录制完毕的音频文件上传到服务器，这样就可以监听了
                        Log.i(TAG, "音频文件录制完毕，可以在后台上传到服务器");
                    } else {
                        if (starttime != null && !starttime.equals("")) {
                            Log.e(TAG, incomingNumber + "电话来了,我不接" + income);
                            endtime = TimeUtil.getNowTime() + "";
                            String File = "123";
//                            send(File, income, 1, starttime, endtime);
                            type = 0;
                            starttime = "";
                            endtime = "";
                        }

                    }
                    income = "";
                    break;
                case TelephonyManager.CALL_STATE_RINGING://零响状态。
                    Log.e(TAG, "电话来了" + incomingNumber);
                    starttime = TimeUtil.getNowTime() + "";
                    type = 4;//来电
                    income = incomingNumber;
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK://通话状态
                    Log.e(TAG, "通话中" + incomingNumber);
                    if (type == 0) {
                        //去电
                        starttime = TimeUtil.getNowTime() + "";
                    } else if (type == 4) {
                        //来电接了
                        starttime = TimeUtil.getNowTime() + "";
                    }
                    //开始录音
                    //1.实例化一个录音机
                    mediaRecorder = new MediaRecorder();
                    //2.指定录音机的声音源
                    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    // 设置音频的编码格式
                    mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    //3.设置录制的文件输出的格式
                    //mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
                    //4.指定录音文件的名称
                    FileName = System.currentTimeMillis() + ".amr";
                        /*MP3Recorder mRecorder = new MP3Recorder(new File(Environment.getExternalStorageDirectory(),System.currentTimeMillis()+".mp3"));
						mRecorder.start();*/
                    File file = new File(FileUtil.getExternalCacheFileRootdir(), FileName);
                    mediaRecorder.setOutputFile(file.getAbsolutePath());
                    //5.设置音频的编码
                    //mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
                    //6.准备开始录音
                    mediaRecorder.prepare();
                    //7.开始录音
                    mediaRecorder.start();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "报错了");
        }
    }

    private class PhoneReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
                String phoneNumber = intent
                        .getStringExtra(Intent.EXTRA_PHONE_NUMBER);
                Log.e(TAG, "打出去的号码 call OUT:" + phoneNumber);
                income = phoneNumber;
            }
        }
    }
}