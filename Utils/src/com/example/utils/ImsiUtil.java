package com.example.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.app.Application;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * IMSI工具类
 * 国际移动用户识别码（IMSI：International Mobile Subscriber Identification Number）
 * 是区别移动用户的标志，储存在SIM卡中，可用于区别移动用户的有效信息。
 *	权限--READ_PHONE_STATE
 */
public class ImsiUtil {

    private static Integer simId_1 = 0;
    private static Integer simId_2 = 1;
    private static String imsi_1 = "";
    private static String imsi_2 = "";
    private static String imei_1 = "";
    private static String imei_2 = "";
    private static Context mApplicationContent;

    /**
     * 不可实例化
     */
    private ImsiUtil() {
    	 throw new Error("Do not need instantiate!");
    }
    
    /**
	 * 在application中初始化
	 */
	public static void initImsiUtil(Application app) {
		mApplicationContent = app.getApplicationContext();
	}

    /**
     * 获取IMSInfo
     *
     * @return
     */
    public static IMSInfo getIMSInfo() {
        IMSInfo imsInfo = initQualcommDoubleSim();
        if (imsInfo != null) {
            return imsInfo;
        } else {
            imsInfo = initMtkDoubleSim();
            if (imsInfo != null) {
                return imsInfo;
            } else {
                imsInfo = initMtkSecondDoubleSim();
                if (imsInfo != null) {
                    return imsInfo;
                } else {
                    imsInfo = initSpreadDoubleSim();
                    if (imsInfo != null) {
                        return imsInfo;
                    } else {
                        imsInfo = getIMSI();
                        if (imsInfo != null) {
                            return imsInfo;
                        } else {
                            imsInfo = null;
                            return imsInfo;
                        }
                    }
                }
            }
        }
    }

    /**
     * MTK的芯片的判断
     *
     * @return
     */
    public static IMSInfo initMtkDoubleSim() {
        IMSInfo imsInfo = null;
        try {
            TelephonyManager tm = (TelephonyManager) mApplicationContent
                    .getSystemService(Context.TELEPHONY_SERVICE);
            Class<?> c = Class.forName("com.android.internal.telephony.Phone");
            Field fields1 = c.getField("GEMINI_SIM_1");
            fields1.setAccessible(true);
            simId_1 = (Integer) fields1.get(null);
            Field fields2 = c.getField("GEMINI_SIM_2");
            fields2.setAccessible(true);
            simId_2 = (Integer) fields2.get(null);

            Method m = TelephonyManager.class.getDeclaredMethod(
                    "getSubscriberIdGemini", int.class);
            imsi_1 = (String) m.invoke(tm, simId_1);
            imsi_2 = (String) m.invoke(tm, simId_2);

            Method m1 = TelephonyManager.class.getDeclaredMethod(
                    "getDeviceIdGemini", int.class);
            imei_1 = (String) m1.invoke(tm, simId_1);
            imei_2 = (String) m1.invoke(tm, simId_2);

            imsInfo = new IMSInfo();
            imsInfo.chipName = "MTK芯片";
            imsInfo.imei_1 = imei_1;
            imsInfo.imei_2 = imei_2;
            imsInfo.imsi_1 = imsi_1;
            imsInfo.imsi_2 = imsi_2;

        } catch (Exception e) {
            imsInfo = null;
            return imsInfo;
        }
        return imsInfo;
    }

    /**
     * MTK的芯片的判断2
     *
     * @return
     */
    public static IMSInfo initMtkSecondDoubleSim() {
        IMSInfo imsInfo = null;
        try {
            TelephonyManager tm = (TelephonyManager) mApplicationContent
                    .getSystemService(Context.TELEPHONY_SERVICE);
            Class<?> c = Class.forName("com.android.internal.telephony.Phone");
            Field fields1 = c.getField("GEMINI_SIM_1");
            fields1.setAccessible(true);
            simId_1 = (Integer) fields1.get(null);
            Field fields2 = c.getField("GEMINI_SIM_2");
            fields2.setAccessible(true);
            simId_2 = (Integer) fields2.get(null);

            Method mx = TelephonyManager.class.getMethod("getDefault",
                    int.class);
            TelephonyManager tm1 = (TelephonyManager) mx.invoke(tm, simId_1);
            TelephonyManager tm2 = (TelephonyManager) mx.invoke(tm, simId_2);

            imsi_1 = tm1.getSubscriberId();
            imsi_2 = tm2.getSubscriberId();

            imei_1 = tm1.getDeviceId();
            imei_2 = tm2.getDeviceId();

            imsInfo = new IMSInfo();
            imsInfo.chipName = "MTK芯片";
            imsInfo.imei_1 = imei_1;
            imsInfo.imei_2 = imei_2;
            imsInfo.imsi_1 = imsi_1;
            imsInfo.imsi_2 = imsi_2;

        } catch (Exception e) {
            imsInfo = null;
            return imsInfo;
        }
        return imsInfo;
    }

    /**
     * 展讯芯片的判断
     *
     * @return
     */
    public static IMSInfo initSpreadDoubleSim() {
        IMSInfo imsInfo = null;
        try {
            Class<?> c = Class
                    .forName("com.android.internal.telephony.PhoneFactory");
            Method m = c.getMethod("getServiceName", String.class, int.class);
            String spreadTmService = (String) m.invoke(c,
                    Context.TELEPHONY_SERVICE, 1);
            TelephonyManager tm = (TelephonyManager) mApplicationContent
                    .getSystemService(Context.TELEPHONY_SERVICE);
            imsi_1 = tm.getSubscriberId();
            imei_1 = tm.getDeviceId();
            TelephonyManager tm1 = (TelephonyManager) mApplicationContent
                    .getSystemService(spreadTmService);
            imsi_2 = tm1.getSubscriberId();
            imei_2 = tm1.getDeviceId();
            imsInfo = new IMSInfo();
            imsInfo.chipName = "展讯芯片";
            imsInfo.imei_1 = imei_1;
            imsInfo.imei_2 = imei_2;
            imsInfo.imsi_1 = imsi_1;
            imsInfo.imsi_2 = imsi_2;
        } catch (Exception e) {
            imsInfo = null;
            return imsInfo;
        }
        return imsInfo;
    }

    /**
     * 高通芯片判断
     *
     * @return
     */
    public static IMSInfo initQualcommDoubleSim() {
        IMSInfo imsInfo = null;
        try {
            Class<?> cx = Class
                    .forName("android.telephony.MSimTelephonyManager");
            Object obj = mApplicationContent.getSystemService("phone_msim");
            Method md = cx.getMethod("getDeviceId", int.class);
            Method ms = cx.getMethod("getSubscriberId", int.class);
            imei_1 = (String) md.invoke(obj, simId_1);
            imei_2 = (String) md.invoke(obj, simId_2);
            imsi_1 = (String) ms.invoke(obj, simId_1);
            imsi_2 = (String) ms.invoke(obj, simId_2);
            int statephoneType_2 = 0;
            boolean flag = false;
            try {
                Method mx = cx.getMethod("getPreferredDataSubscription",
                        int.class);
                Method is = cx.getMethod("isMultiSimEnabled", int.class);
                statephoneType_2 = (Integer) mx.invoke(obj);
                flag = (Boolean) is.invoke(obj);
            } catch (Exception e) {
                // TODO: handle exception
            }
            imsInfo = new IMSInfo();
            imsInfo.chipName = "高通芯片-getPreferredDataSubscription:"
                    + statephoneType_2 + ",flag:" + flag;
            imsInfo.imei_1 = imei_1;
            imsInfo.imei_2 = imei_2;
            imsInfo.imsi_1 = imsi_1;
            imsInfo.imsi_2 = imsi_2;

        } catch (Exception e) {
            imsInfo = null;
            return imsInfo;
        }
        return imsInfo;
    }

    /**
     * 系统的api
     *
     * @return
     */
    public static IMSInfo getIMSI() {
        IMSInfo imsInfo = null;
        try {
            TelephonyManager tm = (TelephonyManager) mApplicationContent
                    .getSystemService(Context.TELEPHONY_SERVICE);
            imsi_1 = tm.getSubscriberId();
            imei_1 = tm.getDeviceId();
        } catch (Exception e) {
            // TODO: handle exception
            imsInfo = null;
            return imsInfo;
        }
        if (TextUtils.isEmpty(imsi_1) || imsi_1.length() < 10) {
            imsInfo = null;
            return imsInfo;
        } else {
            imsInfo = new IMSInfo();
            imsInfo.chipName = "单卡芯片";
            imsInfo.imei_1 = imei_1;
            imsInfo.imei_2 = "没有";
            imsInfo.imsi_1 = imsi_1;
            imsInfo.imsi_2 = "没有";
            return imsInfo;
        }
    }

    public static class IMSInfo {
        public String chipName;
        public String imsi_1;
        public String imei_1;
        public String imsi_2;
        public String imei_2;
    }

}
