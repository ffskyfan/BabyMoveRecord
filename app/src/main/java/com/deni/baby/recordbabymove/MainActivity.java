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

import com.deni.baby.recordbabymove.content_view.RecordList;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity  {

    String ACTION_DIALOG = "android.intent.action.MEDIA_BUTTON";

    private RecordList recordList = new RecordList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        recordList.setContext(this);


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

            TabHost.TabSpec listSpec = host.newTabSpec("List");
            listSpec.setIndicator("数据", null);
            listSpec.setContent(R.id.content_record_list);
            host.addTab(listSpec);

            TabHost.TabSpec chartSpec = host.newTabSpec("Chart");
            chartSpec.setIndicator("图表", null);
            chartSpec.setContent(R.id.content_record_line_chart);
            host.addTab(chartSpec);

            TabHost.TabSpec timesSpec = host.newTabSpec("Times");
            timesSpec.setIndicator("计数", null);
            timesSpec.setContent(R.id.content_record_times);
            host.addTab(timesSpec);
        }

        ListView listView=(ListView)findViewById(R.id.listView);
        recordList.refreshList(listView);
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
        recordList.refreshList(listView);
    }

}
