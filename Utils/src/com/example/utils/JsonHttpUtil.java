package com.example.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

import com.example.constans.Constans;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class JsonHttpUtil
{

    private static JsonHttpUtil jsonHttpUtil;

	private JsonHttpUtil()
	{
	}

	public static JsonHttpUtil getInstance()
	{
		if (jsonHttpUtil == null) {
			jsonHttpUtil = new JsonHttpUtil();
		}
		return jsonHttpUtil;
	}

	public boolean isNetworkConnected(Context context)
	{
		if (context != null)
		{
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null)
			{
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}
	
	
	private void dissmissDialog(Dialog dialog)
    {
	    if (dialog != null && dialog.isShowing())
        {
	        dialog.dismiss();
        }

    }
	


	/**
	 * POST方法有加进度条提示的
	 * @param valueList 
	 * */ 
	public String getJsonData(final HttpPost httpPost,
			Context context,final Dialog creatDialog, MultipartEntity muutil)
	{
		if (!isNetworkConnected(context))
		{
		    dissmissDialog(creatDialog);
			return "网络没连接";
		}

		
		int statusCode = -1;
		
		// 将请求体内容加入请求中
		httpPost.setEntity(muutil);
		// 需要客户端对象来发送请求
		HttpClient httpClient = new DefaultHttpClient();
		// 请求超时
		
		httpClient.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
		// 读取超时
		HttpResponse response = null;
		try
		{
			response = httpClient.execute(httpPost);
			if (creatDialog != null)
            {
			    creatDialog.setOnDismissListener(new OnDismissListener()
			    {
			        
			        @Override
			        public void onDismiss(DialogInterface dialog1)
			        {
			            if (httpPost.isAborted())
			            {
			                httpPost.abort();
			            }
			            dissmissDialog(creatDialog);
			        }
			    });
            }
			
		}
		catch (ClientProtocolException e)
		{
			return null;
		}
		catch (IOException e)
		{
			String message = e.getMessage();
			if (message.contains("refused"))
			{
			    dissmissDialog(creatDialog);
				return "服务器异常";
			}
			if (message.contains("timed"))
			{
				// Log.e("3", " 请求超时 " + message);
			    dissmissDialog(creatDialog);
				return "请求超时";
			}
		}
		if (response != null)
		{

			statusCode = response.getStatusLine().getStatusCode();
		}
		else
		{
		    dissmissDialog(creatDialog);
			return "请求异常";
		}
		if (statusCode != 200)
		{
			Log.e("statusCode != 200", "参数异常或url错误");
			dissmissDialog(creatDialog);
			return "参数异常";
		}
		InputStream is = null;
		try
		{
			HttpEntity entity = response.getEntity();
            is = entity.getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(is,
					"utf-8"));
			String tmp = null;
			while ((tmp = br.readLine()) != null)
			{
				if (tmp != null)
				{
					Log.e("resultjson", tmp.toString());
					dissmissDialog(creatDialog);
					return tmp;
				}
			}
		}
		catch (IllegalStateException e)
		{
			// Log.e("4", "                             " + e.getMessage());
		}
		catch (IOException e)
		{
			// Log.e("5", "                             " + e.getMessage());
		}finally
        {
            if(is != null)
            {
                try
                {
                    is.close();
                }
                catch (IOException e)
                {
                }
            }
        }

		dissmissDialog(creatDialog);
		return null;
	}
	
	
	/**
	 * POST方法没有加进度条提示的
	 * @param muutil 
	 * @param creatDialog 
	 * */ 
	public String getJsonData(final HttpPost httpPost,Context context, MultipartEntity muutil)
	{
	    if (!isNetworkConnected(context))
	    {
	        return "网络没连接";
	    }
	    
	    int statusCode = -1;
	    
	    // URL使用基本URL即可，其中不需要加参数
	    // 将请求体内容加入请求中
		httpPost.setEntity(muutil);
	    // 需要客户端对象来发送请求
	    HttpClient httpClient = new DefaultHttpClient();
	    //如果有乱码 说明 这里的配置给
	    // 请求超时
	    
	    httpClient.getParams().setParameter(
	            CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
	    // 读取超时
	    
	    HttpResponse response = null;
	    try
	    {
	    	response = httpClient.execute(httpPost);
	    }
	    catch (ClientProtocolException e)
	    {
	        
	        return null;
	    }
	    catch (IOException e)
	    {
	        String message = e.getMessage();
	        if (message.contains("refused"))
	        {
	            return "服务器异常";
	        }
	        if (message.contains("timed"))
	        {
	            // Log.e("3", " 请求超时 " + message);
	            return "请求超时";
	        }
	    }
	    if (response != null)
	    {
	        
	        statusCode = response.getStatusLine().getStatusCode();
	    }
	    else
	    {
	        return "请求异常";
	    }
	    if (statusCode != 200)
	    {
	        Log.e("statusCode != 200", "参数异常或url错误");
	        return "参数异常";
	    }
	    InputStream is = null;
	    try
	    {
	        HttpEntity entity = response.getEntity();
	        is = entity.getContent();
	        BufferedReader br = new BufferedReader(new InputStreamReader(is,
	                "utf-8"));
	        String tmp = null;
	        while ((tmp = br.readLine()) != null)
	        {
	            if (tmp != null)
	            {
	                Log.e("resultjson", tmp.toString());
	                return tmp;
	            }
	        }
	    }
	    catch (IllegalStateException e)
	    {
	        // Log.e("4", "                             " + e.getMessage());
	    }
	    catch (IOException e)
	    {
	        // Log.e("5", "                             " + e.getMessage());
	    }finally
	    {
	        if(is != null)
	        {
	            try
	            {
	                is.close();
	            }
	            catch (IOException e)
	            {
	            }
	        }
	    }
	    
	    return null;
	}
	
	
	public String getJsonDataForServiec(final HttpPost httpPost, MultipartEntity muutil)
	{
		int statusCode = -1;
		
		// URL使用基本URL即可，其中不需要加参数
		// 将请求体内容加入请求中
		httpPost.setEntity(muutil);
		// 需要客户端对象来发送请求
		HttpClient httpClient = new DefaultHttpClient();
		//如果有乱码 说明 这里的配置给
		// 请求超时
		
		httpClient.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
		// 读取超时
		
		HttpResponse response = null;
		try
		{
			response = httpClient.execute(httpPost);
		}
		catch (ClientProtocolException e)
		{
			
			return null;
		}
		catch (IOException e)
		{
			String message = e.getMessage();
			if (message.contains("refused"))
			{
				return "服务器异常";
			}
			if (message.contains("timed"))
			{
				// Log.e("3", " 请求超时 " + message);
				return "请求超时";
			}
		}
		if (response != null)
		{
			
			statusCode = response.getStatusLine().getStatusCode();
		}
		else
		{
			return "请求异常";
		}
		if (statusCode != 200)
		{
			Log.e("statusCode != 200", "参数异常或url错误");
			return "参数异常";
		}
		InputStream is = null;
		try
		{
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(is,
					"utf-8"));
			String tmp = null;
			while ((tmp = br.readLine()) != null)
			{
				if (tmp != null)
				{
					Log.e("resultjson", tmp.toString());
					return tmp;
				}
			}
		}
		catch (IllegalStateException e)
		{
			// Log.e("4", "                             " + e.getMessage());
		}
		catch (IOException e)
		{
			// Log.e("5", "                             " + e.getMessage());
		}finally
		{
			if(is != null)
			{
				try
				{
					is.close();
				}
				catch (IOException e)
				{
				}
			}
		}
		
		return null;
	}
	
	public String downFile(String fileUrl){
        String fileDir = "";
        String filename = fileUrl.substring(fileUrl.lastIndexOf("/")+1);
        File fileexit = new File(Constans.DIR_VOICE + filename);
        if (fileexit.exists())
        {
            return Constans.DIR_VOICE + filename ;
        }
        try
        {
        	Log.e("fileurl", ""+Constans.IMGROOTHOST + fileUrl);
                URL url = new URL(Constans.IMGROOTHOST + fileUrl);
                // 创建连接
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                conn.connect();
                // 获取文件大小
//                int contentLength = conn.getContentLength();
                // 创建输入流
                InputStream is = conn.getInputStream();

                File file = new File(Constans.DIR_VOICE);
                // 判断文件目录是否存在
                if (!file.exists())
                {
                    file.mkdirs();
                }
				FileOutputStream fos = new FileOutputStream(new File(Constans.DIR_VOICE,filename));
                // 缓存
                byte buf[] = new byte[1024];
                // 写入到文件中
                do
                {
                    int numread = is.read(buf);
                    if (numread <= 0)
                    {
                        // 下载完成
                        fileDir = Constans.DIR_VOICE  + filename;
                        break;
                    }
                    // 写入文件
                    fos.write(buf, 0, numread);
                } while (true);// !mCancelUpdate);// 点击取消就停止下载.
                fos.close();
                is.close();
        }
        catch (IOException e)
        {
            //
            Log.e("HttpUtils", "文件下载地址错误！");
        }

        return fileDir;
}
}