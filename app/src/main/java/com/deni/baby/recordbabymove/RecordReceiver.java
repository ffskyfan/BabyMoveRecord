package com.deni.baby.recordbabymove;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by loveislife on 2016/8/23.
 */
public class RecordReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("RecordReceiver","onReceive");

        RecordDBHelper dbHelper= new RecordDBHelper(context);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();

        db.execSQL("insert into " + RecordDBHelper.TABLE_NAME + " (Happy) values (1)");

        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }
}
