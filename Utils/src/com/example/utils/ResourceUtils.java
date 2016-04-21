package com.example.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;

/**
 * 文件资源读取工具类
 * res/raw和assets的不同点：
 * 1.res/raw中的文件会被映射到R.java文件中，访问的时候直接使用资源ID即R.id.filename；assets文件夹下的文件不会被映射到R.java中，
 * 访问的时候需要AssetManager类。
 * 2.res/raw不可以有目录结构，而assets则可以有目录结构，也就是assets目录下可以再建立文件夹
 * 
 */
public final class ResourceUtils {

    /**
     * 不可实例化
     */
    private ResourceUtils() {
        throw new Error("Do not need instantiate!");
    }

    /**
     * 从Assets里读文本文件
     *
     * @param context
     * @param fileName 资源名称。
     * @return
     */
    public static String geFileFromAssets(Context context, String fileName) {
        if (context == null || TextUtils.isEmpty(fileName)) {
            return null;
        }

        StringBuilder s = new StringBuilder("");
        try {
            InputStreamReader in = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader br = new BufferedReader(in);
            String line;
            while ((line = br.readLine()) != null) {
                s.append(line);
            }
            return s.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 从一个本地资源获取内容. 
     * 只能是资源,其价值是资源文件的名字——也就是说,它可以用来打开Drawable的,声音,和本地资源
     * 字符串和颜色资源会获取失败。
     *
     * @param context
     * @param resId   资源标识符打开，由apptool，产生的。
     * @return
     */
    public static String geFileFromRaw(Context context, int resId) {
        if (context == null) {
            return null;
        }

        StringBuilder s = new StringBuilder();
        try {
            InputStreamReader in = new InputStreamReader(context.getResources().openRawResource(resId));
            BufferedReader br = new BufferedReader(in);
            String line;
            while ((line = br.readLine()) != null) {
                s.append(line);
            }
            return s.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * same to {@link ResourceUtils#geFileFromAssets(Context, String)}, but return type is List<String>
     *
     * @param context
     * @param fileName
     * @return
     */
    public static List<String> geFileToListFromAssets(Context context, String fileName) {
        if (context == null || TextUtils.isEmpty(fileName)) {
            return null;
        }

        List<String> fileContent = new ArrayList<String>();
        try {
            InputStreamReader in = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader br = new BufferedReader(in);
            String line;
            while ((line = br.readLine()) != null) {
                fileContent.add(line);
            }
            br.close();
            return fileContent;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * same to {@link ResourceUtils#geFileFromRaw(Context, int)}, but return type is List<String>
     *
     * @param context
     * @param resId
     * @return
     */
    public static List<String> geFileToListFromRaw(Context context, int resId) {
        if (context == null) {
            return null;
        }

        List<String> fileContent = new ArrayList<String>();
        BufferedReader reader = null;
        try {
            InputStreamReader in = new InputStreamReader(context.getResources().openRawResource(resId));
            reader = new BufferedReader(in);
            String line = null;
            while ((line = reader.readLine()) != null) {
                fileContent.add(line);
            }
            reader.close();
            return fileContent;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
