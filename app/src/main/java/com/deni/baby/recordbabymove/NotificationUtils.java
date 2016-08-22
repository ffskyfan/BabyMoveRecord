package com.deni.baby.recordbabymove;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;


public class NotificationUtils {
	
	static Context mContext;
	
	public static void saveContext(Context context) {
		mContext = context;
	}
	
	public static void addNotification(int type, int seconds, String title, String text){
		
		AlarmManager manager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(mContext, NotificationService.class);
		intent.setAction(NotificationService.ACTION);
		String intentType = Integer.toString(type);
		intent.setType(intentType);
		intent.putExtra("type", type);
		intent.putExtra("title", title);
		intent.putExtra("text", text);
		PendingIntent pendingIntent = PendingIntent.getService(mContext, 0,intent, PendingIntent.FLAG_UPDATE_CURRENT);
					
		long triggerAtTime = SystemClock.elapsedRealtime()+ (seconds*1000);
		manager.set(AlarmManager.ELAPSED_REALTIME, triggerAtTime , pendingIntent);
	}
		
	public static void cancelNotification(int type){
			
		AlarmManager manager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(mContext, NotificationService.class);
		String intentType = Integer.toString(type);
		intent.setType(intentType);
		intent.setAction(NotificationService.ACTION);
		intent.putExtra("type", type);
		PendingIntent pendingIntent = PendingIntent.getService(mContext, 0,intent, PendingIntent.FLAG_UPDATE_CURRENT);
		manager.cancel(pendingIntent);
	}
		
	
}
