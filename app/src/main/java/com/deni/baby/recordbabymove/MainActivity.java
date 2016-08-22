package com.deni.baby.recordbabymove;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.IBinder;
import android.content.ServiceConnection;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Context;
import android.content.Intent;
import android.content.ComponentName;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    String ACTION_DIALOG = "android.intent.action.MEDIA_BUTTON";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


        //PendingIntent recordPendingIntent = PendingIntent.getBroadcast(this, 1, new Intent(this, RecordReceiver.class), PendingIntent.FLAG_CANCEL_CURRENT);

        Intent clickIntent = new Intent();
        clickIntent.setAction(ACTION_DIALOG);
        PendingIntent recordPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews mRemoteViews = new RemoteViews(getPackageName(), R.layout.notification_view);
        mRemoteViews.setOnClickPendingIntent(R.layout.notification_view, recordPendingIntent);

        Notification.Builder builder = new Notification.Builder(this);

        builder.setContent(mRemoteViews);
        builder.setAutoCancel(false);
        builder.setTicker("this is ticker text");
        builder.setContentTitle("WhatsApp Notification");
        builder.setContentText("You have a new message");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentIntent(recordPendingIntent);
        builder.setOngoing(true);

        Notification mNotification = builder.build();
        manager.notify(0, mNotification);

        ListView listView=(ListView)findViewById(R.id.listView);
        //获取查询结果
        ArrayList<HashMap<String, Object>> listData=fillList();
        //获取适配器
        SimpleAdapter adapter=fillAdapter(listData);
        //添加并且显示
        listView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startService(View view)
    {
        ListView listView=(ListView)findViewById(R.id.listView);
        //获取查询结果
        ArrayList<HashMap<String, Object>> listData=fillList();
        //获取适配器
        SimpleAdapter adapter=fillAdapter(listData);
        //添加并且显示
        listView.setAdapter(adapter);
    }

    public  ArrayList<HashMap<String, Object>> fillList(){

        //生成动态数组，并且转载数据
        ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();

        RecordDBHelper helper=new RecordDBHelper(this);
        SQLiteDatabase db=helper.getReadableDatabase();

        try{
            Cursor cursor=db.rawQuery("SELECT * FROM "+RecordDBHelper.TABLE_NAME, null);

            if(cursor.moveToFirst()) {
                do {
                    Integer appId = cursor.getInt(cursor.getColumnIndex("Id"));
                    String appName = cursor.getString(cursor.getColumnIndex("Time"));

                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("Id", appId);
                    map.put("Time", appName);
                    dataList.add(map);
                }while (cursor.moveToNext());
            }

            Log.i("MainActivity","Query Finish");

        }catch(Exception ex){
            ex.printStackTrace();
        }finally{

            if(db.isOpen()){
                db.close();
            }
        }

        return dataList;
    }

    /**
     * 填充数据，取得数据适配器.
     * @param listData
     * @return
     */
    public SimpleAdapter fillAdapter(ArrayList<HashMap<String, Object>> listData){


        //生成适配器，数组===》ListItem
        SimpleAdapter adapter = new SimpleAdapter(this,
                listData,//数据来源
                R.layout.record_item,//ListItem的XML实现
                //动态数组与ListItem对应的子项
                new String[] {"Id", "Time"},
                //ListItem的XML文件里面的两个TextView ID
                new int[] {R.id.TextId,R.id.TextTime});

        return adapter;

    }
}
