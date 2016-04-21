package com.example.utils;

import com.example.constans.Constans;

import android.util.Log;

/**
 * 简单的log工具类
 * @author Administrator
 *
 */
public class L {
	
	/**
	 * 不可实例化
	 */
	private L() {
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	public static boolean isDebug=Constans.DEBUG;// 是否需要打印bug
	private static final String TAG = "lin";

	// 下面四个是默认tag的函数
	public static void i(String msg) {
		if (isDebug)
			Log.i(TAG, msg);
	}

	public static void d(String msg) {
		if (isDebug)
			Log.d(TAG, msg);
	}

	public static void e(String msg) {
		if (isDebug)
			Log.e(TAG, msg);
	}

	public static void v(String msg) {
		if (isDebug)
			Log.v(TAG, msg);
	}

	// 下面是传入自定义tag的函数
	public static void i(String tag, String msg) {
		if (isDebug)
			Log.i(tag, msg);
	}

	public static void d(String tag, String msg) {
		if (isDebug)
			Log.i(tag, msg);
	}

	public static void e(String tag, String msg) {
		if (isDebug)
			Log.i(tag, msg);
	}

	public static void v(String tag, String msg) {
		if (isDebug)
			Log.i(tag, msg);
	}
}
