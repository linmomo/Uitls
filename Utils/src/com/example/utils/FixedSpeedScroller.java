package com.example.utils;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * viewpager切换速度处理
 * 
 * */
public class FixedSpeedScroller extends Scroller {

	private int mDuration = 500;// 默认500ms

	public FixedSpeedScroller(Context context) {
		super(context);
	}

	public FixedSpeedScroller(Context context, Interpolator interpolator) {
		super(context, interpolator);
	}

	@Override
	public void startScroll(int startX, int startY, int dx, int dy, int duration) {
		// Ignore received duration, use fixed one instead
		super.startScroll(startX, startY, dx, dy, mDuration);
	}

	@Override
	public void startScroll(int startX, int startY, int dx, int dy) {
		// Ignore received duration, use fixed one instead
		super.startScroll(startX, startY, dx, dy, mDuration);
	}

	public void setmDuration(int time) {
		mDuration = time;
	}

	public int getmDuration() {
		return mDuration;
	}
	
	// 初始化viewpager后调用此方法
//	FixedSpeedScroller mScroller = null;
//	private void controlViewPagerSpeed() {
//		try {
//			Field mField;
//
//			mField = ViewPager.class.getDeclaredField("mScroller");
//			mField.setAccessible(true);
//
//			mScroller = new FixedSpeedScroller(
//					mMainHolder.main_viewpager.getContext(),
//					new AccelerateInterpolator());
//			mScroller.setmDuration(2000); // 2000ms
//			mField.set(mMainHolder.main_viewpager, mScroller);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
