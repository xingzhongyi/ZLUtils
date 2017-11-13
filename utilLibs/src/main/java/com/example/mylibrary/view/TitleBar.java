package com.example.mylibrary.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mylibrary.R;


/**
 * Created by mengw on 2017/5/24.
 */

public class TitleBar extends RelativeLayout {
    private Context context;
    public View rootView;
    public RelativeLayout rl_left_head;
    public ImageView title_left_image;
    public ImageView title_right_image;
    public ImageView title_left2_image;
    public TextView title_center_text;
    public ImageView title_right2_image;
    public TextView title_right_text;
    private int color;

    public TitleBar(Context context) {
        super(context);
        this.context = context;
        initView(context);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView(context);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(context);
    }

    private void initView(final Context context) {
        rootView = LayoutInflater.from(context).inflate(R.layout.include_for_titlebar, this);
        rootView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite));
        rl_left_head = (RelativeLayout) rootView.findViewById(R.id.rl_left_head);
        title_left_image = (ImageView) rootView.findViewById(R.id.title_left_image);
        title_right_image = (ImageView) rootView.findViewById(R.id.title_right_image);
        title_left2_image = (ImageView) rootView.findViewById(R.id.title_left2_image);
        title_center_text = (TextView) rootView.findViewById(R.id.title_center_text);
        title_right2_image = (ImageView) rootView.findViewById(R.id.title_right2_image);
        title_right_text = (TextView) rootView.findViewById(R.id.title_right_text);
        rl_left_head.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) context).finish();
            }
        });
    }

    /**
     * 为左边第二个图片设置背景图片
     *
     * @param resId
     * @return
     */
    public TitleBar setTitleLeft2ImageResource(int resId) {
        title_left2_image.setImageResource(resId);
        return this;
    }

    /**
     * 左边第二个图片的点击事件
     *
     * @param onClickListener
     * @return
     */
    public TitleBar setTitleLeft2ImageClickListener(OnClickListener onClickListener) {
        title_left2_image.setOnClickListener(onClickListener);
        return this;
    }

    /**
     * 为中间标题设置内容
     *
     * @param text
     * @return
     */
    public TitleBar setTitleCenterText(String text) {
        title_center_text.setText(text);
        return this;
    }

    /**
     * 为右边第二个图片设置背景图片
     *
     * @param resId
     * @return
     */
    public TitleBar setTitleRight2ImageResource(int resId) {
        title_right2_image.setImageResource(resId);
        return this;
    }

    /**
     * 右边第二个图片的点击事件
     *
     * @param onClickListener
     * @return
     */
    public TitleBar setTitleRight2ImageOnClickListener(OnClickListener onClickListener) {
        title_right2_image.setOnClickListener(onClickListener);
        return this;
    }

    /**
     * 为右边文本设置内容
     *
     * @param text
     * @return
     */
    public TitleBar setTitleRightText(String text) {
        title_right_text.setText(text);
        return this;
    }

    /**
     * 为右边文本设置点击事件
     *
     * @param onClickListener
     * @return
     */
    public TitleBar setTitleRightTextOnClickListener(OnClickListener onClickListener) {
        title_right_text.setOnClickListener(onClickListener);
        return this;
    }

    /**
     * 设置左边的图片隐藏或显示（依然为占位状态）
     *
     * @param b
     * @return
     */
    public TitleBar hideTitleLeftImage(boolean b) {
        if (b) {
            rl_left_head.setVisibility(GONE);
        } else {
            rl_left_head.setVisibility(VISIBLE);
        }
        return this;
    }

    /**
     * 给TitleBar设置背景颜色
     *
     * @param color
     * @return
     */
    public TitleBar setBackgroundColors(@ColorRes int color) {
        rootView.setBackground(ContextCompat.getDrawable(context, color));
        return this;
    }
}
