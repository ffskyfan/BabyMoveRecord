package com.deni.baby.recordbabymove;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

public class NotificationService extends Service {

	public static final String ACTION = "org.cocos2dx.service.NotificationService";

	static Context mContext;

	public static void saveContext(Context context) {
		mContext = context;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {

	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		System.out.println("onStart...");

		Log.d("NotificationService", "onStartCommand: ");
		
		String title = "";
		if (intent.hasExtra("title")) {
			title = intent.getStringExtra("title");
        }
		
		String text = "";
		if (intent.hasExtra("text")) {
			text = intent.getStringExtra("text");
        }
		
		NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		//int icon = R.mipmap.ic_launcher;
		//Notification mNotification = new Notification();
		//mNotification.icon = icon;
		//mNotification.tickerText = title;
		//mNotification.defaults |= Notification.DEFAULT_SOUND;
		//mNotification.flags = Notification.FLAG_AUTO_CANCEL;
		//
		//mNotification.when = System.currentTimeMillis();
		//Navigator to the new activity when click the notification title
		Intent i = new Intent(this, MainActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i,PendingIntent.FLAG_UPDATE_CURRENT);
		//mNotification.
		//mNotification.setLatestEventInfo(this,getResources().getString(R.string.app_name), text, pendingIntent);


		RemoteViews mRemoteViews = new RemoteViews(getPackageName(), R.layout.notification_view);

		Notification.Builder builder = new Notification.Builder(mContext);

		builder.setContent(mRemoteViews);
		builder.setAutoCancel(false);
		builder.setTicker("this is ticker text");
		builder.setContentTitle("WhatsApp Notification");
		builder.setContentText("You have a new message");
		builder.setSmallIcon(R.mipmap.ic_launcher);
		builder.setContentIntent(pendingIntent);
		builder.setOngoing(true);
		//builder.setSubText("This is subtext...");   //API level 16
		//builder.setNumber(100);
		builder.addAction(R.mipmap.ic_launcher, "Call", pendingIntent);
		builder.setStyle(new Notification.BigTextStyle().bigText(""));

		Notification mNotification = builder.build();
		manager.notify(0, mNotification);

		return START_STICKY_COMPATIBILITY;
	}

	

	
	
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		System.out.println("Service:onDestroy");
	}

	/**
	 * DownloadBinder中定义了一些实用的方法
	 *
	 * @author user
	 *
	 */
	public class NotifacationBinder extends Binder {

		/**
		 * 开始下载
		 */
		public void start() {
			new Thread() {
				public void run() {
				};
			}.start();
		}

		/**
		 * 获取进度
		 *
		 * @return
		 */
		public int getProgress() {
			return 0;
		}

		/**
		 * 取消下载
		 */
		public void cancel() {
			//cancelled = true;
		}

		/**
		 * 是否已被取消
		 *
		 * @return
		 */
		public boolean isCancelled() {
			return false;
		}
	}
}

