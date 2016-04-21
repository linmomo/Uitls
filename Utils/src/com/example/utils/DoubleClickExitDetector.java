package com.example.utils;

import java.util.Locale;

import android.content.Context;
import android.widget.Toast;

/**
 * 双击退出程序
 * 用法：
 *	public void onBackPressed() {
 *		if(doubleClickExitDetector.onClick()){
 *			super.onBackPressed();
 *		}
 *	}
 *
 */
public class DoubleClickExitDetector {
	public static final String DEFAULT_HINT_MESSAGE = "再按一次退出程序";
	public static final String DEFAULT_HINT_MESSAGE_OTHER = "Press again to exit the program";
	public static final int DEFAULT_SPACE_TIME = 2000;
	private long spaceTime; // 有效的间隔时间，单位毫秒
	private long lastClickTime; // 上次点击时间
	private String hintMessage; // 提示消息
	private Context context;

	/**
	 * 创建一个双击退出识别器，中国环境下默认提示消息为“再按一次退出程序”，其它环境下默认提示消息为“Press again to exit the program”；有效间隔时间默认为2000毫秒
	 * @param context Androdi上下文
	 */
	public DoubleClickExitDetector(Context context) {
		this(context,Locale.CHINA.equals(Locale.getDefault()) ? DEFAULT_HINT_MESSAGE : DEFAULT_HINT_MESSAGE_OTHER, 2000);
	}

	/**
	 * 创建一个双击退出识别器，有效间隔时间默认为2000毫秒
	 * @param context Android上下文
	 * @param hintMessage 提示消息
	 */
	public DoubleClickExitDetector(Context context, String hintMessage) {
		this(context, hintMessage, DEFAULT_SPACE_TIME);
	}

	/**
	 * 创建一个双击退出识别器
	 * @param context Android上下文
	 * @param hintMessage 提示消息
	 * @param space_time 有效间隔时间
	 */
	public DoubleClickExitDetector(Context context, String hintMessage,
			long space_time) {
		this.context = context;
		this.hintMessage = hintMessage;
		this.spaceTime = space_time;
	}

	/**
	 * 点击，你需要重写Activity的onBackPressed()方法，
	 * 先调用此方法，如果返回true就执行父类的onBackPressed()方法来关闭Activity否则不执行
	 * @return 当两次点击时间间隔小于有效间隔时间时就会返回true，否则返回false
	 */
	public boolean onClick() {
		long currentTime = System.currentTimeMillis();
		boolean result = (currentTime - lastClickTime) < spaceTime;
		lastClickTime = currentTime;
		if (!result) {
			Toast.makeText(context, hintMessage, Toast.LENGTH_SHORT).show();
		}
		return result;
	}

	/**
	 * 设置有效间隔时间，单位毫秒
	 * @param spaceTime 效间隔时间，单位毫秒。当两次点击时间间隔小于有效间隔时间click()方法就会返回true
	 */
	public void setSpaceTime(long spaceTime) {
		this.spaceTime = spaceTime;
	}

	/**
	 * 设置提示消息
	 * @param hintMessage 当前点击同上次点击时间间隔大于有效间隔时间click()方法就会返回false，并且显示提示消息
	 */
	public void setHintMessage(String hintMessage) {
	        this.hintMessage = hintMessage;
	    }
}
