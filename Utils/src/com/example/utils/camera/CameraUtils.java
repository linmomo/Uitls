package com.example.utils.camera;

import java.io.File;

import com.example.constans.Constans;
import com.example.utils.FileUtils;
import com.example.utils.R;

import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

public class CameraUtils implements android.view.View.OnClickListener{
	
	private static final int TAKE_BIG_PICTURE = 1000;
	private static final int CROP_BIG_PICTURE_ALBUM = 1001;
	private static final int CROP_BIG_PICTURE_TAKE = 1002;
	
	private Activity mActivity;
	private OnBitmapListener mOnBitmapListener;
	private View camera_view;
	private PopupWindow popupWindow;
	/** 指定文件保存的文件夹 */
	private String Temp_Path=Constans.DIR_IMAGE;
	private String mFileName;
	private String mFileCropName;
	
	private int mAspectX = -1;
	private int mAspectY = -1;
	private int mOutputX = -1;
	private int mOutputY = -1;

	public CameraUtils(Activity context,OnBitmapListener listener) {
		this.mActivity = context;
		this.mOnBitmapListener = listener;
		if(camera_view==null){
			camera_view = mActivity.getLayoutInflater().inflate(R.layout.camera_menu_dialog, null);
		}
		camera_view.findViewById(R.id.tv_camera).setOnClickListener(this);
		camera_view.findViewById(R.id.tv_album).setOnClickListener(this);
		camera_view.findViewById(R.id.tv_cancel).setOnClickListener(this);
		if(popupWindow==null){
			popupWindow = new PopupWindow(camera_view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		}
		// 需要设置一下此参数，点击外边可消失
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		// 设置点击窗口外边窗口消失
		popupWindow.setOutsideTouchable(true);
		// 设置此参数获得焦点，否则无法点击
		popupWindow.setFocusable(true);
	}
	
	/**
	 * 显示popwindow
	 */
	public void showPop(){
		if(popupWindow!=null){
			mFileName=FileUtils.getFileName();
			mFileCropName="crop_"+FileUtils.getFileName();
			popupWindow.showAtLocation(camera_view, Gravity.CENTER, 0, 0);
		}	
	}
	
	/**
	 * 设置裁剪的比例
	 * @param aspectx
	 * @param aspecty
	 */
	public void setAspect(int aspectx, int aspecty) {
		mAspectX = aspectx;
		mAspectY = aspecty;
	}
	
	/**
	 * 设置裁剪的宽高
	 * @param outputx
	 * @param outputy
	 */
	public void setOutput(int outputx, int outputy) {
		mOutputX = outputx;
		mOutputY = outputy;
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_camera://从相机
			camera();
			if(popupWindow!=null){
				popupWindow.dismiss();
			}
			break;
		case R.id.tv_album://从相册
			album();
			if(popupWindow!=null){
				popupWindow.dismiss();
			}
			break;
		case R.id.tv_cancel://取消
			if(popupWindow!=null){
				popupWindow.dismiss();
			}
			break;
		default:
			break;
		}
	}
	
	/**
	 * 拍照
	 */
	public void camera() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File file = new File(Constans.DIR_IMAGE, mFileName);
		if (file.exists()) {
			file.delete();
		}
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
		mActivity.startActivityForResult(intent, TAKE_BIG_PICTURE);
	}

	/**
	 * 拍照返回结果裁剪
	 * 
	 * @param data
	 */
	private void cameraResult(Intent data) {
		Uri uri = Uri.fromFile(new File(Temp_Path, mFileName));
		Uri uri_crop = Uri.fromFile(new File(Temp_Path, mFileCropName));
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		if (mAspectX>0 && mAspectY>0) {
			intent.putExtra("aspectX", mAspectX);
			intent.putExtra("aspectY", mAspectY);
		}
		if (mOutputX >0 && mOutputY >0) {
			intent.putExtra("outputX", mOutputX);// 裁剪区的宽
			intent.putExtra("outputY", mOutputY);// 裁剪去的高
		}
		intent.putExtra("scale", true);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri_crop);
		intent.putExtra("return-data", false);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true); // no face detection
		mActivity.startActivityForResult(intent, CROP_BIG_PICTURE_TAKE);
	}

	/**
	 * 相册裁剪后返回结果
	 */
	public void album() {
		Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
		intent.setType("image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("scale", true);
		if (mAspectX>0 && mAspectY>0) {
			intent.putExtra("aspectX", mAspectX);
			intent.putExtra("aspectY", mAspectY);
		}
		if (mOutputX >0 && mOutputY >0) {
			intent.putExtra("outputX", mOutputX);// 裁剪区的宽
			intent.putExtra("outputY", mOutputY);// 裁剪去的高
		}
		intent.putExtra("return-data", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(new File(Temp_Path, mFileName)));
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", false);
		mActivity.startActivityForResult(intent, CROP_BIG_PICTURE_ALBUM);
//		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
//		intent.setType("image/*");
//		intent.putExtra("crop", "true");
//		intent.putExtra("scale", true);
//		if (mAspectX != -1 && mAspectY != -1) {
//			intent.putExtra("aspectX", mAspectX);
//			intent.putExtra("aspectY", mAspectY);
//		}
//		intent.putExtra("return-data", false);
//		intent.putExtra(MediaStore.EXTRA_OUTPUT,
//				Uri.fromFile(new File(TEMP_PATH, mFileName)));
//		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
//		intent.putExtra("noFaceDetection", false);
//		mActivity.startActivityForResult(intent, CROP_BIG_PICTURE);
	}
	
	public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case TAKE_BIG_PICTURE:
				cameraResult(data);
				return true;
			case CROP_BIG_PICTURE_ALBUM:
				
				if (mOnBitmapListener != null) {
					//2.回调到上个界面创建CameraUtils里面的bitmapResult
					mOnBitmapListener.bitmapResult(new File(Temp_Path,mFileName));
				}
				return true;
			case CROP_BIG_PICTURE_TAKE:
				
				if (mOnBitmapListener != null) {
					mOnBitmapListener.bitmapResult(new File(Temp_Path,mFileCropName));
				}
				return true;
			}
		}
		return false;
	}
	
	//拍照、相册获取图片返回的接口
	public interface OnBitmapListener {
		public void bitmapResult(File file);
	}
	
}
