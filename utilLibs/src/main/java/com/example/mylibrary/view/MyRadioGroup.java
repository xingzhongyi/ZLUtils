package com.example.mylibrary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RadioGroup;

import com.example.mylibrary.R;


/**
 * Created by Administrator on 2016/6/27.
 */
public class MyRadioGroup extends RadioGroup {
    //是否允许用户切换状态
    private boolean enedit = true;

    public MyRadioGroup(Context context) {
        super(context);
    }

    public MyRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.MyRadioGroup);
        enedit=typedArray.getBoolean(R.styleable.MyRadioGroup_enEdit,true);
        typedArray.recycle();
    }
    public void setEnedit(boolean enedit) {
        this.enedit = enedit;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(!enedit){
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
