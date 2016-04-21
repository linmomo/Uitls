package com.example.utils;

import java.util.LinkedList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

/**
 * activity管理类
 *
 */
public class ActivityManager {
	
	private static LinkedList<Activity> activityStack;
	
	 /**
     * 不能实例化此类
     */
	private ActivityManager() {
	   throw new Error("Do not need instantiate!");
	}
	
	/**
	 * 跳转界面
	 * 
	 * @param clazz
	 *            跳转的目标Activity，带数据
	 */
	public static void openActivity(Context context, Class<?> clazz) {
		Intent intent = new Intent(context, clazz);
		context.startActivity(intent);
	}

	/**
	 * 获得当前栈顶Activity
	 *
	 * @return
	 */
	public static Activity currentActivity() {
		Activity activity = null;
		if (activityStack != null && !activityStack.isEmpty())
			activity = activityStack.get(activityStack.size() - 1);
		return activity;
	}

	/**
	 * 将当前Activity推入栈中
	 *
	 * @param activity
	 */
	public static void pushActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new LinkedList<Activity>();
		}
		activityStack.add(activity);
	}

	/**
	 * 将Activity退出栈
	 *
	 * @param activity
	 */
	public static void popActivity(Activity activity) {
		activityStack.remove(activity);
	}

	/**
	 * 主动退出Activity,关闭界面
	 *
	 * @param activity
	 */
	public static void closeActivity(Activity activity) {
		if (activity != null) {
			activityStack.remove(activity);
			activity.finish();
		}
	}
	
	/**
	 * 主动退出所有Activity
	 */
	public static void closeAllActivity() {
		while (true) {
			Activity activity = currentActivity();
			if (null == activity) {
				break;
			}
			closeActivity(activity);
		}
	}

	/**
	 * 退出指定名字的activity
	 */
	public static void closeActivityByName(String name) {
		while (true) {
			Activity activity = currentActivity();
			if (null == activity) {
				break;
			}

			String activityName = activity.getComponentName().getClassName().toString();
			if (TextUtils.equals(name, activityName)) {
				continue;
			}
			popActivity(activity);
		}
	}

	/**
	 * 获得当前ACTIVITY 名字
	 */
	public static String getCurrentActivityName() {
		Activity activity = currentActivity();
		String name = "";
		if (activity != null) {
			name = activity.getComponentName().getClassName().toString();
		}
		return name;
	}

}
