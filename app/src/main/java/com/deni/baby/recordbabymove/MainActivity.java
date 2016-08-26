package com.deni.baby.recordbabymove;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.SimpleAdapter;
import android.widget.TabHost;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity  {

    String ACTION_DIALOG = "android.intent.action.MEDIA_BUTTON";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Intent clickIntent = new Intent();
        clickIntent.setAction(ACTION_DIALOG);
        PendingIntent recordPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews mRemoteViews = new RemoteViews(getPackageName(), R.layout.notification_view);
        mRemoteViews.setOnClickPendingIntent(R.layout.notification_view, recordPendingIntent);

        Notification.Builder builder = new Notification.Builder(this);
        builder.setContent(mRemoteViews);
        builder.setAutoCancel(false);
        builder.setTicker("记录宝宝胎动");
        builder.setContentTitle("宝宝胎动记录");
        builder.setContentText("");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentIntent(recordPendingIntent);
        builder.setOngoing(true);

        Notification mNotification = builder.build();
        manager.notify(0, mNotification);


        TabHost host = (TabHost) findViewById(R.id.tabHost);
        if(host !=null){
            host.setup();

            TabHost.TabSpec homeSpec = host.newTabSpec("Home"); // This param will
            // be used as tabId.
            homeSpec.setIndicator(null, // This param will diplay as title.
                    getResources().getDrawable(R.drawable.sweet_icon));
            homeSpec.setContent(R.id.linearLayout);
            host.addTab(homeSpec);


            TabHost.TabSpec homeSpec2 = host.newTabSpec("Home2"); // This param will
            // be used as tabId.
            homeSpec2.setIndicator(null, // This param will diplay as title.
                    getResources().getDrawable(R.drawable.sweet_icon));
            homeSpec2.setContent(R.id.linearLayout2);
            host.addTab(homeSpec2);

        }


        //ListView listView=(ListView)findViewById(R.id.listView);
        ////获取查询结果
        //ArrayList<HashMap<String, Object>> listData=fillList();
        ////获取适配器
        //SimpleAdapter adapter=fillAdapter(listData);
        ////添加并且显示
        //listView.setAdapter(adapter);

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
            Cursor cursor=db.rawQuery("SELECT Id,datetime(Time,'localtime') FROM "+RecordDBHelper.TABLE_NAME, null);

            if(cursor.moveToFirst()) {
                do {
                    Integer appId = cursor.getInt(cursor.getColumnIndex("Id"));
                    String appName = cursor.getString( cursor.getColumnIndex("datetime(Time,'localtime')") );

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
