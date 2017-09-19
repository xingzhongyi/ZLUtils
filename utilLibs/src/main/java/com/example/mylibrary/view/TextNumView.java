package com.example.mylibrary.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mylibrary.R;

import org.xutils.common.util.DensityUtil;

/**
 * Created by Administrator on 2016/11/30.
 */
public class TextNumView extends RelativeLayout {
    private TextView bar_num;
    private TextView bar_text;
    private Model model = Model.dot;
    private int number;

    public TextNumView(Context context) {
        this(context, null);
    }

    public TextNumView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextNumView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextNumView);
        int textColor = typedArray.getColor(R.styleable.TextNumView_text_color, getResources().getColor(R.color.colorWhite));
        int numColor = typedArray.getColor(R.styleable.TextNumView_num_color, getResources().getColor(R.color.colorWhite));
        String textString = typedArray.getString(R.styleable.TextNumView_text_text);
        float textSize = typedArray.getInt(R.styleable.TextNumView_text_size, 12);
        float numSize = typedArray.getInt(R.styleable.TextNumView_num_size, 8);
        boolean type = typedArray.getBoolean(R.styleable.TextNumView_type, false);
        typedArray.recycle();
        RelativeLayout rl = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.textnumview_layout, this, true);
        bar_num = (TextView) rl.findViewById(R.id.bar_num);
        bar_text = (TextView) rl.findViewById(R.id.bar_tv);
        bar_text.setTextColor(textColor);
        bar_text.setTextSize(textSize);
        bar_text.setText(textString);
        if (type) {
            bar_text.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.blank), null, getResources().getDrawable(R.mipmap.drop_down_arrow), null);
        }
//        bar_num.setTextColor(numColor);
//        bar_num.setTextSize(numSize);
    }

    public void setNumber(int number) {
        this.number = number;
        if (bar_num != null) {
            if (number > 0) {
                bar_num.setVisibility(VISIBLE);
//                LayoutParams layoutParams=new LayoutParams();
                switch (model) {
                    case dot:
                        bar_num.setWidth(DensityUtil.dip2px(6));
                        bar_num.setHeight(DensityUtil.dip2px(6));
                        bar_num.setText("");
                        break;
                    case number:
                        bar_num.setWidth(DensityUtil.dip2px(15));
                        bar_num.setHeight(DensityUtil.dip2px(15));
                        bar_num.setText(number + "");
                        break;
                }
            } else {
                bar_num.setVisibility(GONE);
            }
        }
    }

    public void setText(String s) {
        if (bar_text != null) {
            bar_text.setText(s);
        }
    }

    public void setModel(Model model) {
        this.model = model;
        setNumber(number);
    }

    public enum Model {
        number,
        dot;
    }
}