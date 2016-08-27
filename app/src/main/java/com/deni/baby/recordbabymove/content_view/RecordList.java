package com.deni.baby.recordbabymove.content_view;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.deni.baby.recordbabymove.R;
import com.deni.baby.recordbabymove.RecordDBHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by deni on 2016/8/28.
 *
 */
public class RecordList {
    private Context context;

    public void setContext(Context _context)
    {
        context = _context;
    }

    public void refreshList(ListView listView)
    {
        //ListView listView=(ListView)context.findViewById(R.id.listView);
        //获取查询结果
        ArrayList<HashMap<String, Object>> listData=fillList();
        //获取适配器
        SimpleAdapter adapter=fillAdapter(listData);
        //添加并且显示
        listView.setAdapter(adapter);
    }

    public ArrayList<HashMap<String, Object>> fillList(){

        //生成动态数组，并且转载数据
        ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();

        RecordDBHelper helper=new RecordDBHelper(context);
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
        SimpleAdapter adapter = new SimpleAdapter(context,
                listData,//数据来源
                R.layout.record_item,//ListItem的XML实现
                //动态数组与ListItem对应的子项
                new String[] {"Id", "Time"},
                //ListItem的XML文件里面的两个TextView ID
                new int[] {R.id.TextId, R.id.TextTime});

        return adapter;

    }
}
