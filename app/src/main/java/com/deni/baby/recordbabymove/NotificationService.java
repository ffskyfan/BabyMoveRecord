package com.deni.baby.recordbabymove;


import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class NotificationService extends IntentService {


	public NotificationService()
	{
		super(NotificationService.class.getName());
	}

	@Override
	public void	onHandleIntent(Intent intent)
	{
		Log.i("NotificationService","onHandleIntent");

	}


}

