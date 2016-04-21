package com.example.utils;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

//为了不被继承
/**
 * @author Administrator
 *
 */
public final class UILUtils {

	public static DisplayImageOptions options, roundoptions;

	// 静态方法可以直接被类点击调用
	public static void displayImage(String imageUrl,ImageView img) {
		// 执行此方法前，先执行其它两个方法
		initoptions();
		ImageLoader.getInstance().displayImage(imageUrl, img, options);
	}
	
	//带监听的显示方法
	public static void displayImage(String imageUrl,ImageView img,ImageLoadingListener listener) {
		// 执行此方法前，先执行其它两个方法
		initoptions();
		ImageLoader.getInstance().displayImage(imageUrl, img, options, listener);
	}
	
	// 监听下载状态
	public static void loadImage(String url, ImageLoadingListener listener) {
		initoptions();
		ImageLoader.getInstance().loadImage(url, options, listener);
	}

	// 方法要改为静态
	private static void initoptions() {
		// 进入前先判断对象是否为空，为空就新建，不为空不做任何
		if (options == null) {//普通的显示图片方法
			options = new DisplayImageOptions.Builder()
					.showImageOnLoading(R.drawable.ic_stub)
					.showImageForEmptyUri(R.drawable.ic_empty)
					.showImageOnFail(R.drawable.ic_error).cacheInMemory(true)
					.cacheOnDisk(true).considerExifParams(true)
					.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
					.bitmapConfig(Bitmap.Config.RGB_565)
					.displayer(new FadeInBitmapDisplayer(300)).build();
		}
//		if (roundoptions == null) {
//			roundoptions = new DisplayImageOptions.Builder()
//					.showImageOnLoading(R.drawable.ic_stub)
//					// 设置图片在下载期间显示的图片
//					.showImageForEmptyUri(R.drawable.ic_empty)
//					// 设置图片Uri为空或是错误的时候显示的图片
//					.showImageOnFail(R.drawable.ic_error)
//					// 设置图片加载/解码过程中错误时候显示的图片
//					.cacheInMemory(true)
//					// 设置下载的图片是否缓存在内存中
//					.cacheOnDisk(true)
//					// 设置下载的图片是否缓存在SD卡中
//					.considerExifParams(true)
//					// 是否考虑JPEG图像EXIF参数（旋转，翻转）
//					.imageScaleType(ImageScaleType.EXACTLY)
//					.bitmapConfig(Bitmap.Config.RGB_565)
//					.displayer(new RoundedBitmapDisplayer(20))// 设置图片的显示方式
//					.build();
//		}
	}
	
	/**
	 * 设置图片显示options， 动态设置加载中，错误，空的图片
	 * @param loadingImg 加载中的显示的图片
	 * @param emptyImg  Uri为空或是错误的时候显示的图片
	 * @param failImg  图片加载/解码过程中错误时候显示的图片
	 */
	public static DisplayImageOptions setoptions(Integer loadingImg, Integer emptyImg,Integer failImg){
		loadingImg = (loadingImg == null ? (R.drawable.ic_stub) : loadingImg);
		emptyImg = (emptyImg == null ? (R.drawable.ic_empty) : emptyImg);
		failImg = (failImg == null ? (R.drawable.ic_error) : failImg);
		DisplayImageOptions	diyoptions = new DisplayImageOptions.Builder()
		.showImageOnLoading(loadingImg)
		.showImageForEmptyUri(emptyImg)
		.showImageOnFail(failImg)
		.cacheInMemory(true)
		.cacheOnDisk(true).
		considerExifParams(true)
		.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.displayer(new FadeInBitmapDisplayer(300)).build();
		return diyoptions;
	}

	// 私有化构造方法，防止被实例化
	private UILUtils() {
	}

	// 图片的所有配置
	// .showImageOnLoading(R.drawable.ic_stub)//设置图片在下载期间显示的图片
	// .showImageForEmptyUri(R.drawable.ic_empty)//设置图片Uri为空或是错误的时候显示的图片
	// .showImageOnFail(R.drawable.ic_error)//设置图片加载/解码过程中错误时候显示的图片
	// .cacheInMemory(true)//设置下载的图片是否缓存在内存中
	// .cacheOnDisk(true)//设置下载的图片是否缓存在SD卡中
	// .considerExifParams(true)//是否考虑JPEG图像EXIF参数（旋转，翻转）
	// .imageScaleType(ImageScaleType.IN_SAMPLE_INT)//设置图片以如何的编码方式显示
	// EXACTLY :图像将完全按比例缩小的目标大小-推荐使用
	// EXACTLY_STRETCHED:图片会缩放到目标大小完全
	// IN_SAMPLE_INT:图像将被二次采样的整数倍-推荐使用
	// IN_SAMPLE_POWER_OF_2:图片将降低2倍，直到下一减少步骤，使图像更小的目标大小
	// NONE:图片不会调整
	// .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型
	// 默认是ARGB_8888，使用RGB_565会比使用ARGB_8888少消耗2倍的内存
	// //.decodingOptions(BitmapFactory.Options decodingOptions)//设置图片的解码配置
	// .delayBeforeLoading(0)//int delayInMillis为你设置的下载前的延迟时间
	// //设置图片加入缓存前，对bitmap进行设置
	// //.preProcessor(BitmapProcessor preProcessor)
	// .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
	// .displayer(new RoundedBitmapDisplayer(20))//设置图片的显示方式
	// 　 　RoundedBitmapDisplayer（int
	// roundPixels）设置圆角图片，不推荐！！！他会创建新的ARGB_8888格式的Bitmap对象；
	// FakeBitmapDisplayer（）这个类什么都没做
	// FadeInBitmapDisplayer（int durationMillis）设置图片渐显的时间
	// SimpleBitmapDisplayer()正常显示一张图片

	// 加载其它来源的图片
	// String imageUri = "http://site.com/image.png"; // from Web
	// String imageUri = "file:///mnt/sdcard/image.png"; // from SD card
	// String imageUri = "content://media/external/audio/albumart/13"; // from
	// content provider
	// String imageUri = "assets://image.png"; // from assets
	// String imageUri = "drawable://" + R.drawable.image; // from drawables
	// (only images, non-9patch)
}
