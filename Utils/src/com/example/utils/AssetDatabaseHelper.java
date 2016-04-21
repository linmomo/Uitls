package com.example.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Environment;
import android.text.TextUtils;

/**
 * 数据库帮助类
 * <ul>
 * <li>自动复制资源中的 /data/data/package_name/databases</li>
 * <li>类似 {@link SQLiteDatabase}使用它, 使用{@link #getWritableDatabase()}创建或者打开一个数据库用于读写。
 * 	使用  {@link #getReadableDatabase()} 创建或打开一个数据库,仅用于读取。</li>
 * </ul>
 *
 */
public class AssetDatabaseHelper {
	
	private Context context;
	private String databaseName;

	public AssetDatabaseHelper(Context context, String databaseName) {
		this.context = context;
		this.databaseName = databaseName;
	}

	/**
	 * 创建或者打开一个数据库用于读写。
	 *
	 * @return SQLiteDatabase对象
	 * @throws RuntimeException
	 *             if cannot copy database from assets
	 * @throws SQLiteException
	 *             if the database cannot be opened
	 */
	public synchronized SQLiteDatabase getWritableDatabase() {
		File dbFile = context.getDatabasePath(databaseName);
		if (dbFile != null && !dbFile.exists()) {
			try {
				copyDatabase(dbFile);
			} catch (IOException e) {
				throw new RuntimeException("Error creating source database", e);
			}
		}

		assert dbFile != null;
		return SQLiteDatabase.openDatabase(dbFile.getPath(), null,
				SQLiteDatabase.OPEN_READWRITE);
	}

	/**
	 *  创建或打开一个数据库,仅用于读取
	 *
	 * @return SQLiteDatabase对象
	 * @throws RuntimeException
	 *             if cannot copy database from assets
	 * @throws SQLiteException
	 *             if the database cannot be opened
	 */
	public synchronized SQLiteDatabase getReadableDatabase() {
		File dbFile = context.getDatabasePath(databaseName);
		if (dbFile != null && !dbFile.exists()) {
			try {
				copyDatabase(dbFile);
			} catch (IOException e) {
				throw new RuntimeException("Error creating source database", e);
			}
		}

		return SQLiteDatabase.openDatabase(dbFile.getPath(), null,
				SQLiteDatabase.OPEN_READONLY);
	}

	/**
	 * 获取数据库名称
	 *
	 * @return the database name
	 */
	public String getDatabaseName() {
		return databaseName;
	}

	private void copyDatabase(File dbFile) throws IOException {
		InputStream stream = context.getAssets().open(databaseName);
		FileUtils.writeFile(dbFile, stream);
		stream.close();
	}
	
	/**
     * 导出数据库  此操作比较耗时,建议在线程中进行
     *
     * @param context      上下文
     * @param targetFile   目标文件
     * @param databaseName 要拷贝的数据库文件名
     * @return 是否倒出成功
     */
    public boolean startExportDatabase(Context context, String targetFile,
                                       String databaseName) {
        if (!Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            return false;
        }
        String sourceFilePath = Environment.getDataDirectory() + "/data/"
                + context.getPackageName() + "/databases/" + databaseName;
        String destFilePath = Environment.getExternalStorageDirectory()
                + (TextUtils.isEmpty(targetFile) ? (context.getPackageName() + ".db")
                : targetFile);
        boolean isCopySuccess = FileUtils.copyFile(sourceFilePath, destFilePath);
        return isCopySuccess;
    }
}
