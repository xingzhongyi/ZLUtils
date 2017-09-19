package com.example.mylibrary.utils;

/**
 * @author zhudf 对话框的点击回调函数
 */
public interface DialogChoiceCallback {
	/**
	 * 对话框同意按钮的点击事件
	 */
	public void onCancelBtn();

	/**
	 * 对话框不同意按钮的点击事件
	 */
	public void onSureBtn();
}
