package com.example.mylibrary.utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.mylibrary.R;


/**
 * @author zhudf 对话框工厂类
 */
public class DialogFactory {

    /**
     * 创建一个ProgressDialog，主要用于网络请求过程中
     *
     * @param context 上下文
     * @param text    显示的上下文
     * @return ProgressDialog
     */
    public static ProgressDialog createProgressDialog(Context context, String text, final DialogInterface.OnCancelListener callback) {
        if (context != null) {

            final ProgressDialog mpDialog = new ProgressDialog(context, ProgressDialog.STYLE_SPINNER);
            mpDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // 设置风格为圆形进度条
            if (!StringUtil.isEmpty(text)) {
                mpDialog.setMessage(text);
            }
            mpDialog.setIndeterminate(false); // 设置进度条是否为不明确显示进度
            mpDialog.setCancelable(true); // // 设置进度条是否可以按返回键取消
            mpDialog.setCanceledOnTouchOutside(false);
            mpDialog.setOnCancelListener(callback);
            return mpDialog;
        } else
            return null;
    }

    /**
     * 显示有同意和不同意按钮的AlertDialog
     *
     * @param context
     *            上下文
     * @param mContent
     *            显示内容
     * @param callback
     *            回调函数，用于同意和不同意按钮
     * @return AlertDialog
     */
//	@SuppressLint("NewApi")
//	public static AlertDialog createAlertDialog(Context context,
//												String mContent, final DialogChoiceCallback callback) {
//		final AlertDialog.Builder builder = new AlertDialog.Builder(context,
//				AlertDialog.THEME_HOLO_LIGHT);
//		builder.setInverseBackgroundForced(true);
//		// AlertDialog.resolveDialogTheme(this,AlertDialog.THEME_TRADITIONAL);
//		LayoutInflater factory = LayoutInflater.from(context);
//		// 自定义弹出框布局
//		View DialogView = factory.inflate(R.layout.dialog_layout_choice, null);
//		// 设置内容
//		TextView content = (TextView) DialogView.findViewById(R.id.textContent);
//		content.setText(mContent);
//		// 同意按钮以及点击事件
//		TextView agreeButton = (TextView) DialogView.findViewById(R.id.agreeButton);
//		// 不同意按钮以及点击事件
//		TextView disagreeButton = (TextView) DialogView
//				.findViewById(R.id.disagreeButton);
//		builder.setView(DialogView);
//
//		final AlertDialog dialog = builder.create();
//		// 设置dialog位置
//		Window dialogWindow = dialog.getWindow();
//		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//		dialogWindow.setAttributes(lp);
//		agreeButton.setOnClickListener(new View.OnClickListener() {// 同意按钮点击事件
//			@Override
//			public void onClick(View arg0) {
//				callback.onChoiceAgreebtn();
//				dialog.dismiss();
//			}
//		});
//
//		disagreeButton.setOnClickListener(new View.OnClickListener() {
//			// 不同意按钮点击事件
//			@Override
//			public void onClick(View arg0) {
//				callback.onSureBtn();
//				dialog.dismiss();
//			}
//		});
//		// 设置不可以按返回键取消Dialog
//		dialog.setCancelable(false);
//		// 设置不可以点击Dialog以外的地方取消Dialog
//		dialog.setCanceledOnTouchOutside(false);
//		return dialog;
//	}

}
