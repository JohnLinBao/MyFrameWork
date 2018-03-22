package com.alfred.framework.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.alfred.framework.myframework.R;

/**
 * Created by Alfred on 2017/11/15.
 * 此service只能start启动
 */
public class UpGradeService extends Service {

	public final static String APKpath = Environment.getExternalStorageDirectory().getPath() + "/upgradePat/update.apk";
	public final static String APKdir = Environment.getExternalStorageDirectory().getPath() + "/upgradePat";

	/******** download progress step *********/
	private static final int down_step_custom = 3;

	private static final int TIMEOUT = 10 * 1000;// 超时
	private static final int DOWN_OK = 1;
	private static final int DOWN_ERROR = 0;

	private String app_name;
	private String url;
	private NotificationManager notificationManager;
	private Notification.Builder builder;
	private RemoteViews contentView;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	/**
	 * 方法描述：onStartCommand方法
	 * 
	 * @param intent, int flags, int startId
	 * @return int
	 * @see UpGradeService
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		app_name = "" + getApplicationInfo().loadLabel(getPackageManager());
		url = intent.getStringExtra("url");
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			createDownLoadNotification();
			createThread();
		} else {
			Toast.makeText(this, "没有SD卡，将无法更新应用。", Toast.LENGTH_SHORT).show();
			stopSelf();
		}
		return super.onStartCommand(intent, flags, startId);
	}

	/********* update UI ******/
	private final Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DOWN_OK:
				createOverNotification(true,"更新成功");
				/***** 安装APK ******/
				installApk();
				/*** stop service *****/
				stopSelf();
				break;

			case DOWN_ERROR:
				createOverNotification(false,"更新失败");
				/*** stop service *****/
				stopSelf();
				break;
			default:
				stopSelf();
				break;
			}
		}
	};

	private void installApk() {
		// TODO Auto-generated method stub
		/********* 下载完成，点击安装 ***********/
		Uri uri = Uri.fromFile(new File(APKpath));
		Intent intent = new Intent(Intent.ACTION_VIEW);

		/**********
		 * 加这个属性是因为使用Context的startActivity方法的话，就需要开启一个新的task
		 **********/
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		intent.setDataAndType(uri, "application/vnd.android.package-archive");
		UpGradeService.this.startActivity(intent);
	}

	/**
	 * 方法描述：createThread方法, 开线程下载
	 * 
	 * @param
	 * @return
	 * @see
	 */
	public void createThread() {
		new DownLoadThread().start();
	}

	private class DownLoadThread extends Thread {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Message message = new Message();
			try {
				long downloadSize = downloadUpdateFile(url, APKpath);

				if (downloadSize > 0) {
					// down success
					message.what = DOWN_OK;
					handler.sendMessage(message);
				}
			} catch (Exception e) {
				e.printStackTrace();
				message.what = DOWN_ERROR;
				handler.sendMessage(message);
			}
		}
	}

	/**
	 * 方法描述：创建下载消息通知（自定义布局）
	 * @param
	 * @return
	 */
	public void createDownLoadNotification() {
		notificationManager =(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.notification_item);
		contentView.setImageViewResource(R.id.notificationImage,R.mipmap.ic_launcher);
		contentView.setTextViewText(R.id.notificationImage, app_name+"全新体验即将展开");
		contentView.setTextViewText(R.id.notificationTitle, app_name+"全新体验即将展开");
		contentView.setTextViewText(R.id.notificationPercent, "0%");
		contentView.setProgressBar(R.id.notificationProgress, 100, 0,false);
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
			builder = new Notification.Builder(this);
		}else {
			builder = new Notification.Builder(this,"001");//channelId 8.0通知渠道的ID号
		}
		builder.setTicker(app_name +"正在更新");
		builder.setAutoCancel(true);
		builder.setWhen(System.currentTimeMillis());
		builder.setSmallIcon(R.mipmap.ic_launcher);
		Intent intent = null;
		try {
			intent = new Intent(this, Class.forName("com.example.alfred.service.ServiceActivity"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);//PendingIntent.FLAG_CANCEL_CURRENT
		//点击跳转的intent
		builder.setContentIntent(pendingIntent);
		builder.setDefaults(Notification.DEFAULT_ALL);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
			builder.setCustomContentView(contentView);
		}else{
			builder.setContent(contentView);
		}
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			notificationManager.notify(R.layout.notification_item,builder.build());
		}else{
			notificationManager.notify(R.layout.notification_item,builder.getNotification());
		}
	}

	public void createOverNotification(boolean isSuccess,String des){
		notificationManager =(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
			builder = new Notification.Builder(this);
		}else {
			builder = new Notification.Builder(this,"001");//channelId 8.0通知渠道的ID号
		}
		builder.setTicker(app_name +"  "+des);
		builder.setContentTitle(app_name);
		builder.setContentText(des);
		builder.setAutoCancel(true);
		builder.setWhen(System.currentTimeMillis());
		builder.setSmallIcon(R.mipmap.ic_launcher);
		builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
		builder.setDefaults(Notification.DEFAULT_ALL);
		if(isSuccess){
			/********* 下载完成，点击安装 ***********/
			Uri uri = Uri.fromFile(new File(APKpath));
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setDataAndType(uri, "application/vnd.android.package-archive");
			PendingIntent pendingIntent = PendingIntent.getActivity(UpGradeService.this, 0, intent, 0);
			builder.setContentIntent(pendingIntent);
		}
		if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
			notificationManager.notify(R.layout.notification_item,builder.build());
		}else{
			notificationManager.notify(R.layout.notification_item,builder.getNotification());
		}
	}


	/***
	 * down file
	 * http://125.65.86.164:2030/ophiuxAppointDataService/OphiuxRPFClien3.apk
	 * 
	 * @return
	 * @throws MalformedURLException
	 */
	public long downloadUpdateFile(String down_url, String filepath) throws Exception {

		int down_step = down_step_custom;// 提示step
		int totalSize;// 文件总大小
		int downloadCount = 0;// 已经下载好的大小
		int updateCount = 0;// 已经上传的文件大小

		InputStream inputStream;
		OutputStream outputStream;

		URL url = new URL(down_url);
		File file = new File(APKdir);
		if (!file.exists()) {
			file.mkdir();
		}

		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
		httpURLConnection.setConnectTimeout(TIMEOUT);
		httpURLConnection.setReadTimeout(TIMEOUT);

		httpURLConnection.setRequestMethod("POST");
		httpURLConnection.setDoOutput(true);
		httpURLConnection.setDoInput(true);
		httpURLConnection.setUseCaches(false);

		httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		httpURLConnection.setRequestProperty("Accept-Encoding", "identity"); // 添加这行代码
		httpURLConnection.connect();
		// 获取下载文件的size

		totalSize = httpURLConnection.getContentLength();
		Log.i("result", "totalSize" + totalSize + "------>>>" + httpURLConnection.getResponseCode());
		if (httpURLConnection.getResponseCode() == 404) {
			throw new Exception("fail!");
		} else if (httpURLConnection.getResponseCode() == -1) {
			throw new Exception("fail!");
		}

		inputStream = httpURLConnection.getInputStream();
		outputStream = new FileOutputStream(filepath, false);// 文件存在则覆盖掉

		byte buffer[] = new byte[1024];
		int readsize = 0;

		while ((readsize = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, readsize);
			downloadCount += readsize;// 时时获取下载到的大小
			/*** 每次增张3% **/
			if (updateCount == 0 || (downloadCount * 100 / totalSize - down_step) >= updateCount) {
				updateCount += down_step;
				// 改变通知栏
				contentView.setTextViewText(R.id.notificationPercent, updateCount + "%");
				contentView.setProgressBar(R.id.notificationProgress, 100, updateCount, false);
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
					builder.setCustomContentView(contentView);
				}else{
					builder.setContent(contentView);
				}
				if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
					notificationManager.notify(R.layout.notification_item,builder.build());
				}else{
					notificationManager.notify(R.layout.notification_item,builder.getNotification());
				}

			}
		}
		if (httpURLConnection != null) {
			httpURLConnection.disconnect();
		}
		inputStream.close();
		outputStream.close();
		return downloadCount;
	}

}
