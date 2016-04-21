package com.example.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Bitmap与DrawAble与byte[]与InputStream之间的转换工具类
 *
 */
public class IOFormat {

	/**
	 * 将byte[]转换成InputStream
	 * @param byte[] b
	 * @return
	 */
	public static InputStream byte2InputStream(byte[] b) {
		ByteArrayInputStream bais = new ByteArrayInputStream(b);
		return bais;
	}

	/**
	 * 将InputStream转换成byte[]
	 * @param InputStream is
	 * @return
	 */
	public static byte[] inputStream2Bytes(InputStream is) {
		String str = "";
		byte[] readByte = new byte[1024];
		@SuppressWarnings("unused")
		int readCount = -1;
		try {
			while ((readCount = is.read(readByte, 0, 1024)) != -1) {
				str += new String(readByte).trim();
			}
			return str.getBytes();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
    /**
     * 输入流转byte[]
     *
     * @param inStream InputStream
     * @return Byte数组
     */
//    public static final byte[] input2byte(InputStream inStream) {
//        if (inStream == null)
//            return null;
//        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
//        byte[] buff = new byte[100];
//        int rc = 0;
//        try {
//            while ((rc = inStream.read(buff, 0, 100)) > 0) {
//                swapStream.write(buff, 0, rc);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return swapStream.toByteArray();
//    }

	/**
	 * 将Bitmap转换成InputStream
	 * @param Bitmap bm
	 * @return
	 */
	public static InputStream bitmap2InputStream(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		InputStream is = new ByteArrayInputStream(baos.toByteArray());
		return is;
	}

	/**
	 * 将Bitmap转换成InputStream
	 * @param Bitmap bm
	 * @param int quality
	 * @return
	 */
	public static InputStream bitmap2InputStream(Bitmap bm, int quality) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, quality, baos);
		InputStream is = new ByteArrayInputStream(baos.toByteArray());
		return is;
	}

	/**
	 * 将InputStream转换成Bitmap
	 * @param InputStream is
	 * @return
	 */
	public static Bitmap inputStream2Bitmap(InputStream is) {
		if (is == null) {
			return null;
		}
		return BitmapFactory.decodeStream(is);
	}

	/**
	 * Drawable转换成InputStream
	 * @param Drawable d
	 * @return
	 */
	public static InputStream drawable2InputStream(Drawable d) {
		if (d == null) {
			return null;
		}
		Bitmap bitmap = drawable2Bitmap(d);
		return bitmap2InputStream(bitmap);
	}

	/**
	 * InputStream转换成Drawable
	 * @param InputStream is
	 * @return
	 */
	public static Drawable inputStream2Drawable(InputStream is) {
		if (is == null) {
			return null;
		}
		Bitmap bitmap = inputStream2Bitmap(is);
		return bitmap2Drawable(bitmap);
	}

	/**
	 * Drawable转换成byte[]
	 * @param Drawable d
	 * @return
	 */
	public static byte[] drawable2Bytes(Drawable d) {
		if (d == null) {
			return null;
		}
		Bitmap bitmap = drawable2Bitmap(d);
		return bitmap2Bytes(bitmap);
	}

	/**
	 * byte[]转换成Drawable
	 * @param byte[] b
	 * @return
	 */
	public static Drawable bytes2Drawable(byte[] b) {
		if (b == null) {
			return null;
		}
		Bitmap bitmap = bytes2Bitmap(b);
		return bitmap2Drawable(bitmap);
	}

	/**
	 * Bitmap转换成byte[]
	 * @param Bitmap bm
	 * @return
	 */
	public static byte[] bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	/**
	 * byte[]转换成Bitmap
	 * @param byte[] b
	 * @return
	 */
	public static Bitmap bytes2Bitmap(byte[] b) {
		if (b.length != 0) {
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		}
		return null;
	}

	/**
	 * Drawable转换成Bitmap
	 * @param Drawable drawable
	 * @return
	 */
	public static Bitmap drawable2Bitmap(Drawable drawable) {
		Bitmap bitmap = Bitmap.createBitmap(
						drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight(),
						drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
								: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}

	/**
	 * Bitmap转换成Drawable
	 * @param Bitmap bitmap
	 * @return
	 */
	public static Drawable bitmap2Drawable(Bitmap bitmap) {
		@SuppressWarnings("deprecation")
		BitmapDrawable bd = new BitmapDrawable(bitmap);
		Drawable d = (Drawable) bd;
		return d;
	}
	
}
