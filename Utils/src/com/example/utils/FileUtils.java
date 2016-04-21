package com.example.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

/**
 * 文件操作工具类
 * 
 * @author Administrator
 *
 */
public class FileUtils {

	public static final long GB = 1073741824; // 1024 * 1024 * 1024
	public static final long MB = 1048576; // 1024 * 1024
	public static final long KB = 1024;

	public static final int ICON_TYPE_ROOT = 1;//根目录
	public static final int ICON_TYPE_FOLDER = 2;//文件夹
	public static final int ICON_TYPE_MP3 = 3;
	public static final int ICON_TYPE_MTV = 4;
	public static final int ICON_TYPE_JPG = 5;
	public static final int ICON_TYPE_FILE = 6;//文件

	public static final String MTV_REG = "^.*\\.(mp4|3gp)$";
	public static final String MP3_REG = "^.*\\.(mp3|wav)$";
	public static final String JPG_REG = "^.*\\.(gif|jpg|png)$";

	private static final String FILENAME_REGIX = "^[^\\/?\"*:<>\\]{1,255}$";
	public final static String FILE_EXTENSION_SEPARATOR = ".";

	/**
	 * 不可实例化
	 */
	private FileUtils() {
		throw new Error("Do not need instantiate!");
	}

	/**
	 * 获取文件名 格式为：20140718_221839.jpg
	 * 
	 * @return
	 */
	public static String getFileName() {
		String name = DateUtils.getCurrentTimeInString();
		String[] names = name.replaceAll(" ", "#").split("#");
		String dateStr = names[0].replaceAll("-", "");
		String timeStr = names[1].replaceAll(":", "");
		StringBuffer picname = new StringBuffer();
		picname.append(dateStr);
		picname.append("_");
		picname.append(timeStr);
		picname.append(".png");
		return picname.toString();
	}

	/**
	 * 使用系统程序打开文件
	 *
	 * @param activity
	 *            Activity
	 * @param file
	 *            File
	 * @throws Exception
	 */
	public static void openFile(Activity activity, File file) throws Exception {
		Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file), getMimeType(file, activity));
		activity.startActivity(intent);
	}

	// -----------------------文件的各种操作处理 ---------------------------
	// 增删改查
	// 是否存在
	// 获取大小
	
	/**
	 * 从路径中获取文件，如果文件为横屏，将修改为竖屏
	 * */
	public static File getImageFileFromPath(String path) {

		if (TextUtils.isEmpty(path)) {
			return null;
		}

		// 获取旋转的角度
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			Log.e("IOException", "旋转角度异常");
		}

		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(new File(path)));
		} catch (FileNotFoundException e1) {
		}
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(in, null, options);
		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
			}
		}
		int i = 0;
		Bitmap bitmap = null;
		while (true) {
			if ((options.outWidth >> i <= 1000)
					&& (options.outHeight >> i <= 1000)) {
				try {
					in = new BufferedInputStream(new FileInputStream(new File(
							path)));
				} catch (FileNotFoundException e) {
				}
				options.inSampleSize = (int) Math.pow(2.0D, i);
				options.inJustDecodeBounds = false;
				bitmap = BitmapFactory.decodeStream(in, null, options);
				break;
			}
			i += 1;
		}

		// 修正图片旋转的角
		Matrix matrix = new Matrix();
		
		matrix.postRotate(degree);
		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
				bitmap.getWidth(), bitmap.getHeight(), matrix, true);

		File file_img = new File(path);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

		try {
			FileOutputStream fos = new FileOutputStream(file_img);
			fos.write(baos.toByteArray());
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return file_img;
	}


	/**
	 * 以此文件路径创建一个文件夹
	 *
	 * @param filePath
	 *            文件路径
	 * @return 如果创建了必要的目录或目标目录已经存在,不能创建假的一个目录。
	 */
	public static boolean makeDirs(String filePath) {
		String folderName = getFolderName(filePath);
		if (TextUtils.isEmpty(folderName)) {
			return false;
		}

		File folder = new File(folderName);
		// folder.exists() 文件或目录是否存在
		// folder.isDirectory() 文件是否是一个目录
		// folder.mkdirs() 创建路径名指定的目录，包括所有必需但不存在的父目录。
		return (folder.exists() && folder.isDirectory()) || folder.mkdirs();
	}

	/**
	 * 文件是否存在
	 *
	 * @param filePath
	 *            文件路径
	 * @return 是否存在文件
	 */
	public static boolean isFileExist(String filePath) {
		if (TextUtils.isEmpty(filePath)) {
			return false;
		}

		File file = new File(filePath);
		//file.isFile()  是否是一个文件
		return (file.exists() && file.isFile());
	}

	/**
	 * 文件夹是否存在
	 *
	 * @param directoryPath
	 *            文件夹路径
	 * @return 文件夹是否存在
	 */
	public static boolean isFolderExist(String directoryPath) {
		if (TextUtils.isEmpty(directoryPath)) {
			return false;
		}

		File dire = new File(directoryPath);
		return (dire.exists() && dire.isDirectory());
	}

	/**
	 * 递归删除文件和文件夹
	 *
	 * @param file
	 *            要删除的根目录
	 */
	public static void DeleteFile(File file) {
		// 文件或目录是否存在。
		if (file.exists() == false) {
			return;
		} else {
			// 测试此抽象路径名表示的文件是否是一个标准文件
			if (file.isFile()) {
				file.delete();
				return;
			}
			// 如果该目录中的文件为空，则直接删除该文件目录
			if (file.isDirectory()) {
				// file.listFiles() 返回此目录下文件数组
				File[] childFile = file.listFiles();
				if (childFile == null || childFile.length == 0) {
					file.delete();
					return;
				}
				// 如果不为空，则递归删除该目录中的文件
				for (File f : childFile) {
					DeleteFile(f);
				}
				// 最后删除该文件目录
				file.delete();
			}
		}
	}

	/**
	 * 删除文件或文件夹
	 *
	 * @param path
	 *            文件路径
	 * @return 是否删除成功
	 */
	public static boolean deleteFile(String path) {
		if (TextUtils.isEmpty(path)) {
			return true;
		}

		File file = new File(path);
		//文件是否存在
		if (!file.exists()) {
			return true;
		}
		//是否是一个文件
		if (file.isFile()) {
			return file.delete();
		}
		//是否是一个文件夹
		if (!file.isDirectory()) {
			return false;
		}
		for (File f : file.listFiles()) {
			if (f.isFile()) {
				f.delete();
			} else if (f.isDirectory()) {
				// 返回此文件的绝对路径名字符串
				deleteFile(f.getAbsolutePath());
			}
		}
		return file.delete();
	}

	/**
	 * 重命名文件和文件夹
	 *
	 * @param file
	 *            File对象
	 * @param newFileName
	 *            新的文件名
	 * @return 执行结果
	 */
	public static boolean renameFile(File file, String newFileName) {
		//此字符串是否匹配给定的正则表达式
		if (newFileName.matches(FILENAME_REGIX)) {
			File newFile = null;
			if (file.isDirectory()) {
				newFile = new File(file.getParentFile(), newFileName);
			} else {
				String temp = newFileName
						+ file.getName().substring(
								file.getName().lastIndexOf('.'));
				newFile = new File(file.getParentFile(), temp);
			}
			//重新命名路径名表示的文件。
			if (file.renameTo(newFile)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 递归查找文件夹下面的符合条件的文件
	 *
	 * @param folder
	 *            文件夹
	 * @param filter
	 *            文件过滤器
	 * @return 符合条件的文件List
	 */
	public static List<HashMap<String, Object>> recursionFolder(File folder,
			FileFilter filter) {

		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		// 获得文件夹下的所有目录和文件集合
		File[] files = folder.listFiles();
		/** 如果文件夹下没内容,会返回一个null **/
		// 判断适配器是否为空
		if (filter != null) {
			files = folder.listFiles(filter);
		}
		// 找到合适的文件返回
		if (files != null) {
			for (int m = 0; m < files.length; m++) {
				File file = files[m];
				if (file.isDirectory()) {
					// 是否递归调用
					list.addAll(recursionFolder(file, filter));

				} else {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("file", file);
					// 设置图标种类
					if (file.getAbsolutePath().toLowerCase(Locale.CHINA).matches(MP3_REG)) {
						map.put("iconType", 3);
					} else if (file.getAbsolutePath().toLowerCase(Locale.CHINA).matches(MTV_REG)) {
						map.put("iconType", 4);
					} else if (file.getAbsolutePath().toLowerCase(Locale.CHINA).matches(JPG_REG)) {
						map.put("iconType", 5);
					} else {
						map.put("iconType", 6);
					}
					list.add(map);
				}
			}
		}
		return list;
	}

	/**
	 * 资源管理器,查找该文件夹下的文件和目录
	 *
	 * @param folder
	 *            文件夹
	 * @param filter
	 *            文件过滤器
	 * @return 符合条件的List
	 */
	public static List<HashMap<String, Object>> unrecursionFolder(File folder,
			FileFilter filter) {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		// 如果是SD卡路径,不添加父路径
		if (!folder.getAbsolutePath().equals(
				Environment.getExternalStorageDirectory().getAbsolutePath())) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("file", folder.getParentFile());
			map.put("iconType", ICON_TYPE_ROOT);
			list.add(map);
		}
		// 获得文件夹下的所有目录和文件集合
		File[] files = folder.listFiles();
		/** 如果文件夹下没内容,会返回一个null **/
		// 判断适配器是否为空
		if (filter != null) {
			files = folder.listFiles(filter);
		}
		if (files != null && files.length > 0) {
			for (File p : files) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("file", p);
				// 设置图标种类
				if (p.isDirectory()) {
					map.put("iconType", ICON_TYPE_FOLDER);
				} else {
					if (p.getAbsolutePath().toLowerCase(Locale.CHINA).matches(MP3_REG)) {
						map.put("iconType", ICON_TYPE_MP3);
					} else if (p.getAbsolutePath().toLowerCase(Locale.CHINA).matches(MTV_REG)) {
						map.put("iconType", ICON_TYPE_MTV);
					} else if (p.getAbsolutePath().toLowerCase(Locale.CHINA).matches(JPG_REG)) {
						map.put("iconType", ICON_TYPE_JPG);
					} else {
						map.put("iconType", ICON_TYPE_FILE);
					}
				}
				// 添加
				list.add(map);
			}
		}
		return list;
	}

	/**
	 * 获取文件大小
	 * 
	 * @param path
	 *            文件路径
	 * @return 返回该文件的长度字节。如果文件不存在返回-1
	 */
	public static long getFileSize(String path) {
		if (TextUtils.isEmpty(path)) {
			return -1;
		}

		File file = new File(path);
		return (file.exists() && file.isFile() ? file.length() : -1);
	}

	/**
	 * 文件大小获取
	 *
	 * @param file
	 *            File对象
	 * @return 文件大小字符串
	 */
	public static String getFileSize(File file) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			// 返回下一次对此输入流调用的方法可以不受阻塞地从此输入流读取（或跳过）的估计剩余字节数。
			int length = fis.available();
			if (length >= GB) {
				return String
						.format(Locale.CHINA, "%.2f GB", length * 1.0 / GB);
			} else if (length >= MB) {
				return String
						.format(Locale.CHINA, "%.2f MB", length * 1.0 / MB);
			} else {
				return String
						.format(Locale.CHINA, "%.2f KB", length * 1.0 / KB);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "未知";
	}


	// ------------------ 读取和写入文件，复制-----------------------

	/**
	 * 根据指定的字符集读取文件
	 *
	 * @param filePath
	 *            文件路径
	 * @param charsetName 
	 * 				编码方式 受支持的字符 {@link java.nio.charset.Charset </code>charset <code>}
	 * @return 如果文件不存在,返回null,否则返回文件的内容
	 * @throws RuntimeException
	 */
	public static String readFile(String filePath, String charsetName) {
		File file = new File(filePath);
		StringBuilder fileContent = new StringBuilder("");
		if (!file.isFile()) {
			return null;
		}

		BufferedReader reader = null;
		try {
			InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
			reader = new BufferedReader(is);
			String line;
			while ((line = reader.readLine()) != null) {
				if (!fileContent.toString().equals("")) {
					fileContent.append("\r\n");
				}
				fileContent.append(line);
			}
			reader.close();
			return fileContent.toString();
		} catch (IOException e) {
			throw new RuntimeException("IOException occurred. ", e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					throw new RuntimeException("IOException occurred. ", e);
				}
			}
		}
	}

	/**
	 * 根据指定的字符集读取字符串容器
	 *
	 * @param filePath
	 *            文件路径
	 * @param charsetName
	 *            编码方式 受支持的字符 {@link java.nio.charset.Charset </code>charset<code>}
	 * @return 如果文件不存在,返回null,否则返回文件的内容
	 * @throws RuntimeException
	 *             if an error occurs while operator BufferedReader
	 */
	public static List<String> readFileToList(String filePath,String charsetName) {
		File file = new File(filePath);
		List<String> fileContent = new ArrayList<String>();
		if (!file.isFile()) {
			return null;
		}

		BufferedReader reader = null;
		try {
			InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
			reader = new BufferedReader(is);
			String line = null;
			while ((line = reader.readLine()) != null) {
				fileContent.add(line);
			}
			reader.close();
			return fileContent;
		} catch (IOException e) {
			throw new RuntimeException("IOException occurred. ", e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					throw new RuntimeException("IOException occurred. ", e);
				}
			}
		}
	}

	/**
	 * 将文本写入文件
	 *
	 * @param filePath
	 *            文件路径
	 * @param content
	 *            内容
	 * @param append
	 *            如果为 true，则将数据写入文件末尾处，而不是写入文件开始处。
	 * @return 如果内容为空返回false 否则为true
	 * @throws RuntimeException
	 *             if an error occurs while operator FileWriter
	 */
	public static boolean writeFile(String filePath, String content,
			boolean append) {
		if (TextUtils.isEmpty(content)) {
			return false;
		}

		FileWriter fileWriter = null;
		try {
			//先创建写入的文件
			makeDirs(filePath);
			fileWriter = new FileWriter(filePath, append);
			fileWriter.write(content);
			fileWriter.close();
			return true;
		} catch (IOException e) {
			throw new RuntimeException("IOException occurred. ", e);
		} finally {
			if (fileWriter != null) {
				try {
					fileWriter.close();
				} catch (IOException e) {
					throw new RuntimeException("IOException occurred. ", e);
				}
			}
		}
	}

	/**
	 * 将文本容器写入文件
	 *
	 * @param filePath
	 *            文件路径
	 * @param contentList
	 *            内容List
	 * @param append
	 *            如果为 true，则将数据写入文件末尾处，而不是写入文件开始处。
	 * @return 如果内容列表为空返回false 否则为true
	 * @throws RuntimeException
	 */
	public static boolean writeFile(String filePath, List<String> contentList,
			boolean append) {
		if (contentList == null || contentList.size() < 1) {
			return false;
		}

		FileWriter fileWriter = null;
		try {
			makeDirs(filePath);
			fileWriter = new FileWriter(filePath, append);
			int i = 0;
			for (String line : contentList) {
				if (i++ > 0) {
					fileWriter.write("\r\n");
				}
				fileWriter.write(line);
			}
			fileWriter.close();
			return true;
		} catch (IOException e) {
			throw new RuntimeException("IOException occurred. ", e);
		} finally {
			if (fileWriter != null) {
				try {
					fileWriter.close();
				} catch (IOException e) {
					throw new RuntimeException("IOException occurred. ", e);
				}
			}
		}
	}

	/**
	 * 写入文件,字符串将被写入到文件的开始
	 *
	 * @param filePath
	 *            文件路径
	 * @param content
	 *            内容
	 * @return 执行结果
	 */
	public static boolean writeFile(String filePath, String content) {
		return writeFile(filePath, content, false);
	}

	/**
	 * 写入文件，字符串列表将被写入到文件的开始
	 *
	 * @param filePath
	 *            文件路径
	 * @param contentList
	 *            内容List
	 * @return 执行结果
	 */
	public static boolean writeFile(String filePath, List<String> contentList) {
		return writeFile(filePath, contentList, false);
	}

	/**
	 * 写入文件,输入流
	 *
	 * @param file
	 *            文件
	 * @param stream
	 *            输入流
	 * @param append
	 *            如果为 true，则将数据写入文件末尾处，而不是写入文件开始处。
	 * @return return true
	 * @throws RuntimeException
	 *             if an error occurs while operator FileOutputStream
	 */
	public static boolean writeFile(File file, InputStream stream,
			boolean append) {
		OutputStream o = null;
		try {
			makeDirs(file.getAbsolutePath());
			o = new FileOutputStream(file, append);
			byte data[] = new byte[1024];
			int length = -1;
			while ((length = stream.read(data)) != -1) {
				o.write(data, 0, length);
			}
			o.flush();
			return true;
		} catch (FileNotFoundException e) {
			throw new RuntimeException("FileNotFoundException occurred. ", e);
		} catch (IOException e) {
			throw new RuntimeException("IOException occurred. ", e);
		} finally {
			if (o != null) {
				try {
					o.close();
					stream.close();
				} catch (IOException e) {
					throw new RuntimeException("IOException occurred. ", e);
				}
			}
		}
	}

	/**
	 * 写入文件,输入流
	 *
	 * @param filePath
	 *            文件路径
	 * @param stream
	 *            输入流
	 * @param append
	 *            如果为true的,就将字节写入文件的结束,而不是开始
	 * @return return true
	 * @throws RuntimeException
	 *             if an error occurs while operator FileOutputStream
	 */
	public static boolean writeFile(String filePath, InputStream stream,
			boolean append) {
		return writeFile(filePath != null ? new File(filePath) : null, stream,
				append);
	}

	/**
	 * 写入文件,将输入流写入文件的开始
	 *
	 * @param file
	 *            File对象
	 * @param stream
	 *            InputStream
	 * @return
	 * @see {@link #writeFile(File, InputStream, boolean)}
	 */
	public static boolean writeFile(File file, InputStream stream) {
		return writeFile(file, stream, false);
	}

	/**
	 * 写入文件,将输入流写入文件的开始
	 *
	 * @param filePath
	 *            文件路径
	 * @param stream
	 *            InputStream
	 * @return 执行结果
	 * @see {@link #writeFile(String, InputStream, boolean)}
	 */
	public static boolean writeFile(String filePath, InputStream stream) {
		return writeFile(filePath, stream, false);
	}

	/**
	 * 复制文件
	 *
	 * @param sourceFilePath
	 *            源文件路径
	 * @param destFilePath
	 *            目标文件路径
	 * @return 执行结果
	 * @throws RuntimeException
	 *             if an error occurs while operator FileOutputStream
	 */
	public static boolean copyFile(String sourceFilePath, String destFilePath) {
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(sourceFilePath);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("FileNotFoundException occurred. ", e);
		}
		return writeFile(destFilePath, inputStream);
	}
	
	  /**
     * 移动文件
     * 
     * @param sourceFilePath
     * @param destFilePath
     */
    public static void moveFile(String sourceFilePath, String destFilePath) {
        if (TextUtils.isEmpty(sourceFilePath) || TextUtils.isEmpty(destFilePath)) {
            throw new RuntimeException("Both sourceFilePath and destFilePath cannot be null.");
        }
        moveFile(new File(sourceFilePath), new File(destFilePath));
    }

    /**
     * 移动文件
     * 
     * @param srcFile
     * @param destFile
     */
    public static void moveFile(File srcFile, File destFile) {
        boolean rename = srcFile.renameTo(destFile);
        if (!rename) {
            copyFile(srcFile.getAbsolutePath(), destFile.getAbsolutePath());
            deleteFile(srcFile.getAbsolutePath());
        }
    }


	// ---------------------获取文件，文件后缀等名称-----------------------

	/**
	 * 获取文件后缀类型
	 *
	 * @param file
	 *            File
	 * @param activity
	 *            Activity
	 * @return MimeType字符串
	 * @throws Exception
	 */
	public static String getMimeType(File file, Activity activity)
			throws Exception {

		String name = file.getName()
				.substring(file.getName().lastIndexOf('.') + 1)
				.toLowerCase(Locale.CHINA);
		int id = activity.getResources().getIdentifier(
				activity.getPackageName() + ":string/" + name, null, null);

		// 特殊处理
		if ("class".equals(name)) {
			return "application/octet-stream";
		}
		if ("3gp".equals(name)) {
			return "video/3gpp";
		}
		if ("nokia-op-logo".equals(name)) {
			return "image/vnd.nok-oplogo-color";
		}
		if (id == 0) {
			throw new Exception("未找到分享该格式的应用");
		}
		return activity.getString(id);
	}

	/**
	 * 获取一个文件过滤器对象 示例:"^.*\\.(mp3|mp4|3gp)$"
	 *
	 * @param reg
	 *            目前允许取值 REG_MTV, REG_MP3, REG_JPG三种
	 * @param isdir
	 *            是否文件夹
	 * @return 文件过滤器
	 */
	public static FileFilter getFileFilter(final String reg, boolean isdir) {
		if (isdir) {
			return new FileFilter() {
				@Override
				public boolean accept(File pathname) {

					return pathname.getAbsolutePath().toLowerCase(Locale.CHINA)
							.matches(reg)
							|| pathname.isDirectory();
				}
			};
		} else {
			return new FileFilter() {
				@Override
				public boolean accept(File pathname) {

					return pathname.getAbsolutePath().toLowerCase(Locale.CHINA)
							.matches(reg)
							&& pathname.isFile();
				}
			};
		}
	}
	
	/**
	 * 从路径获得文件名,不包括后缀
	 * <p/>
	 * 
	 * <pre>
	 *      getFileNameWithoutExtension(null)               =   null
	 *      getFileNameWithoutExtension("")                 =   ""
	 *      getFileNameWithoutExtension("   ")              =   "   "
	 *      getFileNameWithoutExtension("abc")              =   "abc"
	 *      getFileNameWithoutExtension("a.mp3")            =   "a"
	 *      getFileNameWithoutExtension("a.b.rmvb")         =   "a.b"
	 *      getFileNameWithoutExtension("c:\\")              =   ""
	 *      getFileNameWithoutExtension("c:\\a")             =   "a"
	 *      getFileNameWithoutExtension("c:\\a.b")           =   "a"
	 *      getFileNameWithoutExtension("c:a.txt\\a")        =   "a"
	 *      getFileNameWithoutExtension("/home/admin")      =   "admin"
	 *      getFileNameWithoutExtension("/home/admin/a.txt/b.mp3")  =   "b"
	 * </pre>
	 *
	 * @param filePath
	 *            文件路径
	 * @return 文件名，不包含后缀
	 * @see
	 */
	public static String getFileNameWithoutExtension(String filePath) {
		if (TextUtils.isEmpty(filePath)) {
			return filePath;
		}

		int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
		int filePosi = filePath.lastIndexOf(File.separator);
		if (filePosi == -1) {
			return (extenPosi == -1 ? filePath : filePath.substring(0,
					extenPosi));
		}
		if (extenPosi == -1) {
			return filePath.substring(filePosi + 1);
		}
		return (filePosi < extenPosi ? filePath.substring(filePosi + 1,
				extenPosi) : filePath.substring(filePosi + 1));
	}
	
	/**
	 * 从路径获取文件后缀名称
	 * <p/>
	 * 
	 * <pre>
	 *      getFileExtension(null)               =   ""
	 *      getFileExtension("")                 =   ""
	 *      getFileExtension("   ")              =   "   "
	 *      getFileExtension("a.mp3")            =   "mp3"
	 *      getFileExtension("a.b.rmvb")         =   "rmvb"
	 *      getFileExtension("abc")              =   ""
	 *      getFileExtension("c:\\")              =   ""
	 *      getFileExtension("c:\\a")             =   ""
	 *      getFileExtension("c:\\a.b")           =   "b"
	 *      getFileExtension("c:a.txt\\a")        =   ""
	 *      getFileExtension("/home/admin")      =   ""
	 *      getFileExtension("/home/admin/a.txt/b")  =   ""
	 *      getFileExtension("/home/admin/a.txt/b.mp3")  =   "mp3"
	 * </pre>
	 *
	 * @param filePath
	 *            文件路径
	 * @return 文件后缀名称
	 */
	public static String getFileExtension(String filePath) {
		if (TextUtils.isEmpty(filePath)) {
			return filePath;
		}

		int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
		int filePosi = filePath.lastIndexOf(File.separator);
		if (extenPosi == -1) {
			return "";
		}
		return (filePosi >= extenPosi) ? "" : filePath.substring(extenPosi + 1);
	}

	/**
	 * 从路径获得文件名,包括后缀
	 * <p/>
	 * 
	 * <pre>
	 *      getFileName(null)               =   null
	 *      getFileName("")                 =   ""
	 *      getFileName("   ")              =   "   "
	 *      getFileName("a.mp3")            =   "a.mp3"
	 *      getFileName("a.b.rmvb")         =   "a.b.rmvb"
	 *      getFileName("abc")              =   "abc"
	 *      getFileName("c:\\")              =   ""
	 *      getFileName("c:\\a")             =   "a"
	 *      getFileName("c:\\a.b")           =   "a.b"
	 *      getFileName("c:a.txt\\a")        =   "a"
	 *      getFileName("/home/admin")      =   "admin"
	 *      getFileName("/home/admin/a.txt/b.mp3")  =   "b.mp3"
	 * </pre>
	 *
	 * @param filePath
	 *            文件路径
	 * @return 文件名，包含后缀
	 */
	public static String getFileName(String filePath) {
		if (TextUtils.isEmpty(filePath)) {
			return filePath;
		}

		int filePosi = filePath.lastIndexOf(File.separator);
		return (filePosi == -1) ? filePath : filePath.substring(filePosi + 1);
	}

	/**
	 * 从路径获取文件夹名称
	 * <p/>
	 * 
	 * <pre>
	 *      getFolderName(null)               =   null
	 *      getFolderName("")                 =   ""
	 *      getFolderName("   ")              =   ""
	 *      getFolderName("a.mp3")            =   ""
	 *      getFolderName("a.b.rmvb")         =   ""
	 *      getFolderName("abc")              =   ""
	 *      getFolderName("c:\\")              =   "c:"
	 *      getFolderName("c:\\a")             =   "c:"
	 *      getFolderName("c:\\a.b")           =   "c:"
	 *      getFolderName("c:a.txt\\a")        =   "c:a.txt"
	 *      getFolderName("c:a\\b\\c\\d.txt")    =   "c:a\\b\\c"
	 *      getFolderName("/home/admin")      =   "/home"
	 *      getFolderName("/home/admin/a.txt/b.mp3")  =   "/home/admin/a.txt"
	 * </pre>
	 *
	 * @param filePath
	 *            文件路径
	 * @return 文件夹名称
	 */
	public static String getFolderName(String filePath) {

		if (TextUtils.isEmpty(filePath)) {
			return filePath;
		}

		int filePosi = filePath.lastIndexOf(File.separator);
		return (filePosi == -1) ? "" : filePath.substring(0, filePosi);
	}


	/**
	 * 把uri转为File对象
	 *
	 * @param activity
	 *            Activity
	 * @param uri
	 *            文件Uri
	 * @return File对象
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static File uri2File(Activity activity, Uri uri) {
		if (Build.VERSION.SDK_INT < 11) {
			// 在API11以下可以使用：managedQuery
			String[] proj = { MediaStore.Images.Media.DATA };
			@SuppressWarnings("deprecation")
			Cursor actualimagecursor = activity.managedQuery(uri, proj, null,
					null, null);
			int actual_image_column_index = actualimagecursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			actualimagecursor.moveToFirst();
			String img_path = actualimagecursor
					.getString(actual_image_column_index);
			return new File(img_path);
		} else {
			// 在API11以上：要转为使用CursorLoader,并使用loadInBackground来返回
			String[] projection = { MediaStore.Images.Media.DATA };
			CursorLoader loader = new CursorLoader(activity, uri, projection,
					null, null, null);
			Cursor cursor = loader.loadInBackground();
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return new File(cursor.getString(column_index));
		}
	}

}
