package com.example.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.protocol.HTTP;

import android.R.color;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Window;
import android.widget.ProgressBar;

/**
 * post请求工具
 * 
 * @author Administrator 武
 * 
 * 数据设置好后 .post() 方法启动线程请求数据
 * */
public class MyPostUtil
{
	private static final int MSG_SET_ALIAS = 1001;
    private MultipartEntity multiEntity;
	private Context context;
	private OnJsonResultListener listener;
	private int tag;
	private Dialog progressDialog;
	private Activity activity;
	private HttpPost httpPost;

	public MyPostUtil() {
		this.creat();
	}
	
	public  void creat(){
		multiEntity = new MultipartEntity();
    	httpPost = new HttpPost();
    	if (!httpPost.containsHeader("Accept")) {
    		httpPost.addHeader("Accept", "application/json");
    	}
    	//-----------
        if (!httpPost.containsHeader("Accept-Encoding")) {
        	httpPost.addHeader("Accept-Encoding", "gzip");
        }
	}
	
    /**
     * 放Sring 数据
     * */
    public void pS(String key,String value){
    	if (StringUtils.isEmpty(value)) {
			return ;
		}
    	try {
			multiEntity.addPart(key,new StringBody(String.valueOf(value), Charset.forName(HTTP.UTF_8)));
		} catch (UnsupportedEncodingException e) {
		}
//    	httpPost.addHeader(key, value);
    }
    
    /**
     * 放File
     * */
    public  void pF(String key ,File file){
        if (file == null)
        {
            return ;
        }
        multiEntity.addPart(key, new FileBody(file));
    }
    
    /**
     * 开启异步加载 （用MultipartEntity工具上传数据给服务器(可以上传文件)）
     * @param url 完整地址
     * @param listener 返回json数据的监听者(OnJsonResultListener)
     * @param tag 同个页面请求时区分数据
     * @param context 上下文
     * @param isOpenPB 是否开启 全屏进度条提示
     * */
    public void post( String url, OnJsonResultListener listener,  int tag,  final Context context, final boolean isOpenPB){
    	try {
			httpPost.setURI(new URI(url).normalize());
		} catch (URISyntaxException e) {
			ToastUtils.customLong(context,"请填写正确的访问地址！");
		}
    	this.context = context ;
	    this.listener = listener ;
	    this.tag = tag ;
	    this.activity = (Activity) context ;
	    if (isOpenPB && !activity.isFinishing())
        {
            progressDialog = creatDialog(context);
        }
	    new Thread(new Runnable() {
			
			@Override
			public void run() {
		    	 String jsonData = null ;
		 	    if (isOpenPB)
		         {
		 	        jsonData = JsonHttpUtil.getInstance().getJsonData(httpPost,context,progressDialog,multiEntity);
		         }else{
		             jsonData = JsonHttpUtil.getInstance().getJsonData(httpPost,context,multiEntity);
		         }
		 	    
		 	   Message msg = new Message();
		       msg.obj = jsonData ;
		       handler.sendMessage(msg);
		 	    
		    }
		}).start();
    }
    
    
    private  Dialog creatDialog(Context context)
    {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ProgressBar progressBar = new ProgressBar(context);
        dialog.setContentView(progressBar);

        dialog.getWindow().setBackgroundDrawableResource(color.transparent);
        dialog.setCanceledOnTouchOutside(false);
        if(dialog != null)
        {
            dialog.show();
        }
        return dialog;

    }
    
	   @SuppressLint("HandlerLeak")
	private Handler handler = new Handler(){
	        
	        public void handleMessage(Message msg) {
	          returnJson((String) msg.obj, context);
	        };
	    };
    
		private  void returnJson(String result, Context context)
		{
			if (activity.isFinishing()) {
				return ;
			}
			if (TextUtils.isEmpty(result))
	        {
				ToastUtils.customLong(context,"请求数据失败！");
	            result = null ;
	        }
			else if ("网络没连接".equals(result))
		    {
		        ToastUtils.customLong(context,"网络没连接！");
		        result = null  ;
		    }
		    else if ("服务器异常".equals(result))
			{
				ToastUtils.customLong(context,"数据请求异常！");
				result = null  ;
			}
		    else if ("请求超时".equals(result))
			{
				ToastUtils.customLong(context,"请求超时!");
				result = null  ;

			}
		    else if ("请求异常".equals(result))
			{
				ToastUtils.customLong(context,"请求异常!");
				result = null  ;
			}
		    else if ("参数异常".equals(result))
			{
				ToastUtils.customLong(context,"请求服务器失败!");
				result = null ;
			}
			
//			if (!TextUtils.isEmpty(result)) {
//				try {
//					JSONObject json = new JSONObject(result);
//						String msg = json.getString("msg");
//						if (!TextUtils.isEmpty(msg) && "请先登录系统".equals(msg) || "此账号在异地登陆".equals(msg)) {
//							SPUtils.setBoolean("islogin", false);
//							SPUtils.setValues("memId", "");
//							SPUtils.setValues("token", "");
//							SPUtils.setValues("datasource", "");
//							ToastUtils.showCustomsLong(context, "账号异常请重新登陆！");
//							mHandler.sendMessage(mHandler.obtainMessage(
//					                 MSG_SET_ALIAS,""));
//							toLogin();
//							return;
//						}
//					
//				} catch (JSONException e) {
//				}
//			}
			listener.returnJsonResult(result, tag);
		}
		
//		private void toLogin()
//	    {
//			SPUtils.setBoolean("isremindpsw", false);
//			SysApplication.getI().exit();
//	        context.startActivity(new Intent(context,LoginActivity.class));
//	    }

		 public interface OnJsonResultListener
			{
				public void returnJsonResult(String json, int tag);
			}

			public void setOnResultListener(OnJsonResultListener listener)
			{
				this.listener = listener;
			}

			
}

