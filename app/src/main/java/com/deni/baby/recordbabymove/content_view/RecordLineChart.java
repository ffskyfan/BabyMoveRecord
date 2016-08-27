package com.deni.baby.recordbabymove.content_view;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.deni.baby.recordbabymove.RecordDBHelper;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by deni on 2016/8/28.
 *
 */
public class RecordLineChart {

    private Context context;

    public void setContext(Context _context)
    {
        context = _context;
    }

    private XYMultipleSeriesRenderer mRenderer;
    private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();


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

    private void showChart(LinearLayout rootLayout) {

        View view = ChartFactory.getLineChartView(context, mDataset, mRenderer);
        rootLayout.addView(view);
    }


    protected static XYMultipleSeriesDataset buildDataset(String[] titles,
                                                          List<Double> xValues,
                                                          List<Double> yValues)
    {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

        int length = titles.length;                  //有几条线
        for (int i = 0; i < length; i++)
        {
            XYSeries series = new XYSeries(titles[i]);    //根据每条线的名称创建

            double[] xV = new double[xValues.size()];
            for (int x = 0; x < xValues.size(); x++) {
                xV[x] = xValues.get(x);                // java 1.5+ style (outboxing)
            }

            double[] yV = new double[yValues.size()];
            for (int y = 0; y < yValues.size(); y++) {
                yV[y] = xValues.get(y);                // java 1.5+ style (outboxing)
            }

            int seriesLength = xV.length;                 //有几个点

            for (int k = 0; k < seriesLength; k++)        //每条线里有几个点
            {
                series.add(xV[k], yV[k]);
            }

            dataset.addSeries(series);
        }

        return dataset;
    }

    protected static XYMultipleSeriesRenderer buildRenderer(int[] colors, PointStyle[] styles, boolean fill)
    {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        int length = colors.length;
        for (int i = 0; i < length; i++)
        {
            XYSeriesRenderer r = new XYSeriesRenderer();
            r.setColor(colors[i]);
            r.setPointStyle(styles[i]);
            r.setFillPoints(fill);
            renderer.addSeriesRenderer(r);
        }
        return renderer;
    }

    protected static void setChartSettings(XYMultipleSeriesRenderer renderer, String title,
                                           String xTitle,String yTitle, double xMin,
                                           double xMax, double yMin, double yMax,
                                           int axesColor,int labelsColor)
    {
        renderer.setChartTitle(title);
        renderer.setXTitle(xTitle);
        renderer.setYTitle(yTitle);
        renderer.setXAxisMin(xMin);
        renderer.setXAxisMax(xMax);
        renderer.setYAxisMin(yMin);
        renderer.setYAxisMax(yMax);
        renderer.setAxesColor(axesColor);
        renderer.setLabelsColor(labelsColor);
    }
}
