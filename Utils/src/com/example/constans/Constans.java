package com.example.constans;

import android.os.Environment;

/**
 * 常量
 * @author lin
 *
 */
public class Constans {

	/**
	 * 本应用的文件图片都放到这个路径
	 * */
	public static final String APPDIR = Environment.getExternalStorageDirectory() + "/linapp/";
	public static final String DIR_IMAGE = APPDIR + "images/";
	public static final String DIR_VOICE = APPDIR + "voice/";
	public static final String OUTPUT_IMAGE = APPDIR + "send/";
	public static final String TEMP_PATH = "temp"; // SD卡中的临时文件夹路径
	
	/**全局日志打印设置 **/
	public static final boolean DEBUG = true;


	// 小林
	public static final String ROOT = "http://192.168.0.188:8081/";

	// 小吴账号 13860477911 123456
	// 小林账号 18850569878 1234567

	// 接口请求地址
	public static final String ROOTHOST = ROOT + "zhonghe/web/";

	// 小林图片请求地址
	public static final String IMGROOTHOST = ROOT + "zhonghe/upload/";

}
