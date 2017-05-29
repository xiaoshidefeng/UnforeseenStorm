package com.example.cw.unforeseenstorm.Facede;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;

/**
 * Created by cw on 2017/5/29.
 * 门面
 */

public class ChartFacede {
    private String[] strings;
    private double[] doubles;
    private LineChart lineChart;

    public ChartFacede(String[] strings, double[] doubles, LineChart lineChart) {
        this.strings = strings;
        this.doubles = doubles;
        this.lineChart = lineChart;
    }

    public void makeChart(){

        if(ChartFacede.this.doubles != null){
            //组件 生成数据
            LineData mLineData = GetLineData.getLineData(ChartFacede.this.doubles);

            //组件 生成表格
            ShowChart showChart = new ShowChart();
            showChart.setStrings(ChartFacede.this.strings);
            showChart.showChart(ChartFacede.this.lineChart, mLineData, Color.rgb(255, 255, 255));
        }

    }

}
