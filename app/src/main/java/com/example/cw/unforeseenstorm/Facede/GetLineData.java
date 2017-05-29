package com.example.cw.unforeseenstorm.Facede;

import android.graphics.Color;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

/**
 * Created by cw on 2017/5/29.
 */

public class GetLineData {

    /**
     * 生成一个数据
     *
     * @return
     */
    protected static LineData getLineData(double[] doubles) {
        // y轴的数据
        ArrayList<Entry> yValues = new ArrayList<Entry>();

        if(doubles != null){
            for(int i = 0; i < doubles.length ;i++){
                Entry y = new Entry(i, (float)doubles[i]) ;
                yValues.add(y);

            }
        }

        // y轴的数据集合
        LineDataSet lineDataSet = new LineDataSet(yValues, "气温" /*显示在比例图上*/);

        //用y轴的集合来设置参数
        lineDataSet.setLineWidth(1.75f); // 线宽
        lineDataSet.setCircleSize(5f);// 显示的圆形大小
        lineDataSet.setColor(Color.rgb(0,153,0));// 显示颜色
        lineDataSet.setCircleColor(Color.rgb(0,153,0));// 圆形的颜色
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);//曲线风格
        lineDataSet.setCubicIntensity(0.2f);//设置曲线的平滑度
        lineDataSet.setDrawFilled(true);//设置允许填充

        ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
        lineDataSets.add(lineDataSet); // add the datasets

        // create a data object with the datasets
        LineData lineData = new LineData(lineDataSet);

        return lineData;
    }
}
