package com.example.activity;

import android.app.Application;

/**
 * 全局appl
 * @author lin
 *
 */
public class App extends Application {
	
	private static App instance;
	
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
	}

	// ��ȡ��appContext
	public static App getInstance() {
		return instance;
	}
}
