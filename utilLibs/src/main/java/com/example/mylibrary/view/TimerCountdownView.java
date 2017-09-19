package com.example.mylibrary.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/10/26.
 */
public class TimerCountdownView extends View {
    int mMaxSeconds = 0;
    float mRateAngle = 0;
    private float mMaxAngle = 0;

    /**
     * 外圈相关
     **/
    private float mOutCircleWidth = 6;
    private int mOutCircleColor = 0xff2f82f2;
    //起始角度
    private float mOutStartAngle = 0;
    //扫描角度
    private float mOutSweepAngle = 360;


    /**
     * 内圈相关
     **/
    private float mInCircleWidth = 6;
    private int mInCircleColor = 0xffF5F5F5;
    //起始角度
    private float mInStartAngle = 0;
    //扫描角度
    private float mInSweepAngle = 360;

    /**
     * 外圈与内圈的距离
     **/
    private float mOutAndInPadding = 0; //外援环和小圆环虹之间的间隔

    //发起重回命令
    private int mActionHeartbeat = 1;

    //间隔时间
    private int mDelayTime = 1 * 1000;

    private CountdownTimerListener mListener;

    public TimerCountdownView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    private Handler mHandler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            if (msg.what == mActionHeartbeat) {
                mMaxAngle = mMaxAngle - mRateAngle;
                mMaxSeconds = mMaxSeconds - 1;
                if (mMaxSeconds >= 0) {
                    invalidate();
                    mHandler.sendEmptyMessageDelayed(mActionHeartbeat, mDelayTime);
                    if (mListener != null) {
                        mListener.onCountDown(showTheTimer(mMaxSeconds));
                        mListener.onTimeArrive(false);
                    }
//					Log.d("", "剩余"+mMaxSeconds+"秒" +"  剩余角度："+mMaxAngle);
                } else {
                    mListener.onTimeArrive(true);
                }
            }
        }


    };
    Runnable myRunnable = new Runnable() {
        public void run() {
            //Some interesting task
            mMaxAngle = mMaxAngle - mRateAngle;
            mMaxSeconds = mMaxSeconds - 1;
            if (mMaxSeconds >= 0) {
                invalidate();
//                mHandler.sendEmptyMessageDelayed(mActionHeartbeat, mDelayTime);
                mHandler.postDelayed(myRunnable, mDelayTime);
                if (mListener != null) {
                    mListener.onCountDown(showTheTimer(mMaxSeconds));
                    mListener.onTimeArrive(false);
                }
//					Log.d("", "剩余"+mMaxSeconds+"秒" +"  剩余角度："+mMaxAngle);
            } else {
                mListener.onTimeArrive(true);
            }
        }
    };

    public void updateView() {
        mHandler.sendEmptyMessage(mActionHeartbeat);
//        mHandler.postDelayed(myRunnable, 0);
    }

    public void destroy() {
        mHandler.removeMessages(mActionHeartbeat);
//        mHandler.removeCallbacks(myRunnable);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawInCircle(canvas);
        drawOutCircle(canvas);
    }

    public void drawInCircle(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(mInCircleColor);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(mInCircleWidth);
        float left = mOutAndInPadding + mOutCircleWidth;
        float top = left;
        float right = getWidth() - left;
        float bottom = getHeight() - top;
        RectF oval = new RectF(left, top, right, bottom);
        canvas.drawArc(oval, mInStartAngle, mInSweepAngle, false, paint);

    }


    public void drawOutCircle(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(mOutCircleColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(mOutCircleWidth);

//        float left = 1;
//        float top = left;
//        float right = getWidth() - left;
//        float bottom = getHeight() - top;
//        RectF oval = new RectF(left + mOutCircleWidth, top + mOutCircleWidth, right - mOutCircleWidth, bottom - mOutCircleWidth);

//        canvas.drawArc(oval, mOutStartAngle, mMaxAngle, false, paint);
        float left = mOutAndInPadding + mOutCircleWidth;
        float top = left;
        float right = getWidth() - left;
        float bottom = getHeight() - top;
        RectF oval = new RectF(left, top, right, bottom);
        canvas.drawArc(oval, mOutStartAngle, mMaxAngle, false, paint);

    }


    /**
     * 设置初始最大时间
     *
     * @param minute 单位分
     */
    public void setMaxTime(int minute) {
        mMaxAngle = mOutSweepAngle;
        mMaxSeconds = minute * 60;
        mRateAngle = mMaxAngle / mMaxSeconds;
    }

    public void setOutCicleColor(int color) {
        mOutCircleColor = color;
    }

    public void setOutCicleWidth(int width) {
        mOutCircleWidth = width;
    }

    public void setInCicleColor(int color) {
        mInCircleColor = color;
    }

    public void setInCicleWidth(int width) {
        mInCircleWidth = width;
    }

    public void setOuterAndInerPadding(int padding) {
        mOutAndInPadding = padding;
    }

    public String showTheTimer(int seconds) {
        String timer = "";
        String sminute = "";
        String ssecond = "";
        if (seconds >= 0) {
            int minute = seconds / 60;
            int second = seconds % 60;
//            if (minute == 0) {
//
//            } else if (minute < 10) {
//                sminute = "0" + minute + ":";
//            } else {
//                sminute = minute + ":";
//            }
//            if (second < 10) {
//                ssecond = "0" + second;
//            } else {
//            ssecond = second + "";
//            }
//            timer = sminute + ssecond;
            ssecond = second + "";
            timer = ssecond;//只显示秒
        } else {
            timer = "0";
        }
        return timer;
    }

    public interface CountdownTimerListener {

        //当前倒计时计算的文本  格式 mm-ss
        public void onCountDown(String time);

        //倒计时是否到达
        public void onTimeArrive(boolean isArrive);
    }

    public void addCountdownTimerListener(CountdownTimerListener listener) {
        mListener = listener;
    }
}
