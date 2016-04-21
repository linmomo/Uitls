package com.example.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * findview帮助类
 * 
 * @author 林
 */
public class ViewFinder {

	/**
	 * find包装接口
	 */
    private static interface FindWrapper {

        View findViewById(int id);

        Resources getResources();
    }

    /**
	 * 窗口对象包装类
	 */
    private static class WindowWrapper implements FindWrapper {

        private final Window window;

        WindowWrapper(final Window window) {
            this.window = window;
        }

        public View findViewById(final int id) {
            return window.findViewById(id);
        }

        public Resources getResources() {
            return window.getContext().getResources();
        }
    }

    /**
	 * view对象包装类
	 */
    private static class ViewWrapper implements FindWrapper {

        private final View view;

        ViewWrapper(final View view) {
            this.view = view;
        }

        public View findViewById(final int id) {
            return view.findViewById(id);
        }

        public Resources getResources() {
            return view.getResources();
        }
    }

    private final FindWrapper wrapper;

    /**
     * 初始化findview基于view
     * 用于自定义视图，fragment
     * 
     * @param view
     */
    public ViewFinder(final View view) {
        wrapper = new ViewWrapper(view);
    }

    /**
     * 初始化findview基于window
     * 用于dialog，popwindow
     *
     * @param window
     */
    public ViewFinder(final Window window) {
        wrapper = new WindowWrapper(window);
    }

    /**
     * 初始化findview基于Activity
     *
     * @param activity
     */
    public ViewFinder(final Activity activity) {
        this(activity.getWindow());
    }

    /**
     * 基础findViewById
     *
     * @param id
     * @return found view
     */
    @SuppressWarnings("unchecked")
    public <V extends View> V find(final int id) {
        return (V) wrapper.findViewById(id);
    }
    
    /**
     * 获得 TextView
     *
     * @param id
     * @return text view
     */
    public TextView textView(final int id) {
        return find(id);
    }
    
    /**
     * 获得 Button
     *
     * @param id
     * @return Button
     */
    public Button btn(final int id) {
        return find(id);
    }

    /**
     * 获得ImageView
     *
     * @param id
     * @return image view
     */
    public ImageView imageView(final int id) {
        return find(id);
    }

    /**
     *获得 CompoundButton
     *
     * @param id
     * @return image view
     */
    public CompoundButton compoundButton(final int id) {
        return find(id);
    }

    /**
     * 注册监听，根据控件id
     *
     * @param id
     * @param listener
     * @return view registered with listener
     */
    public View setOnClick(final int id, final OnClickListener listener) {
        View clickable = find(id);
        clickable.setOnClickListener(listener);
        return clickable;
    }

    /**
     * Register runnable to be invoked when child view with given id is clicked
     *
     * @param id
     * @param runnable
     * @return view registered with runnable
     */
    public View setOnClick(final int id, final Runnable runnable) {
        return setOnClick(id, new OnClickListener() {

            public void onClick(View v) {
                runnable.run();
            }
        });
    }

    /**
     * 批量注册监听，根据控件id数组
     *
     * @param ids
     * @param listener
     */
    public void setOnClick(final OnClickListener listener, final int... ids) {
        for (int id : ids)
            find(id).setOnClickListener(listener);
    }

    /**
     * Register runnable to be invoked when all given child view ids are clicked
     *
     * @param ids
     * @param runnable
     */
    public void onClick(final Runnable runnable, final int... ids) {
    	setOnClick(new OnClickListener() {

            public void onClick(View v) {
                runnable.run();
            }
        }, ids);
    }


    /**
     * 直接根据TextView控件id设置文本
     *
     * @param id
     * @param content
     * @return text view
     */
    public TextView setText(final int id, final CharSequence content) {
        final TextView text = find(id);
        text.setText(content);
        return text;
    }

    /**
     * 直接根据TextView控件id设置文本
     * 文本为资源
     *
     * @param id
     * @param content
     * @return text view
     */
    public TextView setText(final int id, final int content) {
        return setText(id, wrapper.getResources().getString(content));
    }
    
    /**
     * 设置图片，根据Imagview控件id
     *
     * @param id
     * @param drawable
     * @return image view
     */
    public ImageView setDrawable(final int id, final int drawable) {
        ImageView image = imageView(id);
        image.setImageDrawable(image.getResources().getDrawable(drawable));
        return image;
    }

    /**
     * 注册勾选监听，根据CompoundButton控件id
     *
     * @param id
     * @param listener
     * @return view registered with listener
     */
    public CompoundButton onCheck(final int id, final OnCheckedChangeListener listener) {
        CompoundButton checkable = find(id);
        checkable.setOnCheckedChangeListener(listener);
        return checkable;
    }

    /**
     * Register runnable to be invoked when child view with given id is
     * checked/unchecked
     *
     * @param id
     * @param runnable
     * @return view registered with runnable
     */
    public CompoundButton onCheck(final int id, final Runnable runnable) {
        return onCheck(id, new OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                runnable.run();
            }
        });
    }

    /**
     * 注册勾选监听，根据CompoundButton控件id数组
     *
     * @param ids
     * @param listener
     */
    public void onCheck(final OnCheckedChangeListener listener,
                        final int... ids) {
        for (int id : ids)
            compoundButton(id).setOnCheckedChangeListener(listener);
    }

    /**
     * Register runnable to be invoked when all given child view ids are
     * checked/unchecked
     *
     * @param ids
     * @param runnable
     */
    public void onCheck(final Runnable runnable, final int... ids) {
        onCheck(new OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                runnable.run();
            }
        }, ids);
    }

}
