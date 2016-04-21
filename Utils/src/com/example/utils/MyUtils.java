package com.example.utils;

import java.math.BigDecimal;
import java.util.HashMap;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.util.SparseArray;
import android.view.View;

/**
 * 常用工具类
 * 
 * @author 林
 * 
 * */
public class MyUtils {

	private static Context mApplicationContent;
	private static long lastClickTime;
	
	 /**
     * 不可实例化
     */
	private MyUtils() {
	   throw new Error("Do not need instantiate!");
	}

	/**
	 * 在application中初始化
	 */
	public static void initUtils(Application app) {
		mApplicationContent = app.getApplicationContext();
	}

	 /**
	   * 四舍五入保留几位小数
	   *
	   * @param number  原数
	   * @param decimal 保留几位小数
	   * @return 四舍五入后的值
	   */
	  public static BigDecimal round(double number, int decimal){
	    return new BigDecimal(number).setScale(decimal, BigDecimal.ROUND_HALF_UP);
	  }	
    
    /**
     * 判断是否为连击
     *
     * @return  boolean
     */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

	/**
	 * 根据总的数据条数 跟后台每一页放回的条数算出总页数
	 * 
	 * @param total
	 *            后台放回的数据总条数
	 * */
	public static int getTPager(int total) {

		return total % 10 == 0 ? total / 10 : total / 10 + 1;
	}

	/**
	 * 从游标里获取String类型数据
	 * */
	public static String getSFromCursor(Cursor cursor, String key) {
		return cursor.getString(cursor.getColumnIndex(key));
	}

	/**
	 * 从游标里获取Int类型数据
	 * */
	public static int getIFromCursor(Cursor cursor, String key) {
		return cursor.getInt(cursor.getColumnIndex(key));
	}

	/**
	 * 从ViewHolder中获取View
	 */
	@SuppressWarnings("unchecked")
	public static <T extends View> T getViewFromVH(View convertView, int id) {
		SparseArray<View> viewHolder = (SparseArray<View>) convertView.getTag();
		if (viewHolder == null) {
			viewHolder = new SparseArray<View>();
			convertView.setTag(viewHolder);
		}
		View childView = viewHolder.get(id);
		if (childView == null) {
			childView = convertView.findViewById(id);
			viewHolder.put(id, childView);
		}
		return (T) childView;
	}

	/**
	 * 复制文本到剪贴板
	 * 
	 * @param text
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static void copyToClipboard(String text){
	 ClipboardManager cbm = (ClipboardManager)
	 mApplicationContent.getSystemService(Activity.CLIPBOARD_SERVICE);
	 cbm.setPrimaryClip(ClipData.newPlainText(mApplicationContent.getPackageName(), text));
	 }

	/**
	 * 获得一个HashMap对象
	 * */
	public static HashMap<String, String> getMap() {
		return new HashMap<String, String>();
	}

	/**
	 * 经纬度测距
	 * 
	 * @param jingdu1
	 * @param weidu1
	 * @param jingdu2
	 * @param weidu2
	 * @return 两个坐标的距离
	 */
	public static double distance(double jingdu1, double weidu1,
			double jingdu2, double weidu2) {
		double a, b, R;
		R = 6378137; // 地球半径
		weidu1 = weidu1 * Math.PI / 180.0;
		weidu2 = weidu2 * Math.PI / 180.0;
		a = weidu1 - weidu2;
		b = (jingdu1 - jingdu2) * Math.PI / 180.0;
		double d;
		double sa2, sb2;
		sa2 = Math.sin(a / 2.0);
		sb2 = Math.sin(b / 2.0);
		d = 2* R* Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(weidu1)* Math.cos(weidu2) * sb2 * sb2));
		return d;
	}

	/**
	 * 读取资源文件Uri
	 * 
	 * @return
	 */
	public static Uri getUriFromRes(int id) {
		return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
				+ mApplicationContent.getResources().getResourcePackageName(id)
				+ "/"
				+ mApplicationContent.getResources().getResourceTypeName(id)
				+ "/"
				+ mApplicationContent.getResources().getResourceEntryName(id));
	}
	

}
