package com.example.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 系统显示相关工具类
 * 
 * @author Administrator
 *
 */
public class DisplayUtils {

	private static Context mApplicationContent;

	/**
	 * 在application中初始化
	 */
	public static void initDisplayUtils(Application app) {
		mApplicationContent = app.getApplicationContext();
	}

	/**
	 * 不可实例化
	 */
	private DisplayUtils() {
		throw new Error("Do not need instantiate!");
	}

	/**
	 * 把dp转换成px
	 * */
	public static int Dp2Px(float dp) {
		final float scale = mApplicationContent.getResources()
				.getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}

	/**
	 * 把px转换成dp
	 * */
	public static int Px2Dp(float px) {
		final float scale = mApplicationContent.getResources()
				.getDisplayMetrics().density;
		return (int) (px / scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 sp 的单位 转成为 px
	 *
	 * @param spValue
	 *            SP值
	 * @return 像素值
	 */
	public static int sp2px(float spValue) {
		float fontScale = mApplicationContent.getResources()
				.getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	/**
	 * 获得屏幕宽度
	 * */
	public static int getScreenWidth() {
		DisplayMetrics dm = mApplicationContent.getResources()
				.getDisplayMetrics();
		return dm.widthPixels;
	}

	/**
	 * 获得屏幕高度
	 * */
	public static int getScreenHeight() {
		DisplayMetrics dm = mApplicationContent.getResources()
				.getDisplayMetrics();
		return dm.heightPixels - getStatusBarHeight();
	}

	/**
	 * 取屏幕高度包含状态栏高度
	 * 
	 * @return
	 */
	public static int getScreenHeightWithStatusBar() {
		DisplayMetrics dm = mApplicationContent.getResources()
				.getDisplayMetrics();
		return dm.heightPixels;
	}

	/**
	 * 取状态栏高度
	 * 
	 * @return
	 */
	public static int getStatusBarHeight() {
		int result = 0;
		int resourceId = mApplicationContent.getResources().getIdentifier(
				"status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = mApplicationContent.getResources().getDimensionPixelSize(
					resourceId);
		}
		return result;
	}

	/**
	 * 获取dialog宽度
	 *
	 * @param activity
	 *            Activity
	 * @return Dialog宽度
	 */
	public static int getDialogW(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		dm = activity.getResources().getDisplayMetrics();
		int w = dm.widthPixels - 100;
		// int w = aty.getWindowManager().getDefaultDisplay().getWidth() - 100;
		return w;
	}

	/**
	 * 获取当前屏幕截图，包含状态栏
	 * 
	 * @param activity
	 * @return
	 */
	public static Bitmap snapShotWithStatusBar(Activity activity) {
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		int width = getScreenWidth();
		int height = getScreenHeight();
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
		view.destroyDrawingCache();
		return bp;

	}

	/**
	 * 获取当前屏幕截图，不包含状态栏
	 * 
	 * @param activity
	 * @return
	 */
	public static Bitmap snapShotWithoutStatusBar(Activity activity) {
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;

		int width = getScreenWidth();
		int height = getScreenHeight();
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
				- statusBarHeight);
		view.destroyDrawingCache();
		return bp;

	}

    /**
     * 获取视图在屏幕中的位置
     *
     * @param view 要获取坐标的视图
     * @return 返回一个含有2个元素的数组, 第一个元素是x坐标、第二个为y坐标
     */
    public static int[] getViewLocationInScreen(View view) {
        int[] loc = new int[2];
        view.getLocationOnScreen(loc);
        return loc;
    }

    /**
     * 获取视图在一个Window中的位置
     *
     * @param view 要获取坐标的视图
     * @return 返回一个含有2个元素的数组, 第一个元素是x坐标、第二个为y坐标
     */
    public static int[] getViewLocationInWindow(View view) {
        int[] loc = new int[2];
        view.getLocationInWindow(loc);
        return loc;
    }
	
	/**
	 * 为给定的编辑器开启软键盘
	 * @param context 
	 * @param editText 给定的编辑器
	 */
	public static void openSoftKeyboard(Context context, EditText editText){
		editText.requestFocus();
		InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(
				Context.INPUT_METHOD_SERVICE);
		inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
		ViewUtils.setEditTextSelectionToEnd(editText);
	}
	
	/**
	 * 关闭软键盘
	 * @param context
	 */
	public static void closeSoftKeyboard(Activity activity){
		InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(
				Context.INPUT_METHOD_SERVICE);
		//如果软键盘已经开启
		if(inputMethodManager.isActive()){
			inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
	
	/**
	 * 切换软键盘的状态
	 * @param context
	 */
	public static void toggleSoftKeyboardState(Context context){
		((InputMethodManager) context.getSystemService(
				Context.INPUT_METHOD_SERVICE)).toggleSoftInput(
				InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
	}
}
