package com.deni.baby.recordbabymove.fragment;

import android.support.v4.app.Fragment;

import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.List;

/**
 * Created by deni on 2016/8/25.
 *
 */

public class RecordToLineChartFragment extends Fragment{

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
