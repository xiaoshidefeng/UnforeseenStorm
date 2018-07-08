package com.example.cw.unforeseenstorm.WeatherStrategy.Weathers;

import android.content.Context;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.example.cw.unforeseenstorm.Bean.RealWeatherBean;
import com.example.cw.unforeseenstorm.R;
import com.example.cw.unforeseenstorm.WeatherStrategy.Weather;

/**
 * Created by cw on 2017/5/29.
 */

public class Overcast implements Weather {

    @Override
    public void weather(Context context, ImageView imageView, CollapsingToolbarLayout collapsingToolbarLayout, RealWeatherBean realWeatherBean) {
        imageView.setImageResource(R.mipmap.img_overcast_day);
        collapsingToolbarLayout.setTitle(realWeatherBean.text + "  " + realWeatherBean.tmp + "℃");

        //修改Bar颜色
        collapsingToolbarLayout.setStatusBarScrimColor(ContextCompat.getColor(context, R.color.colorOvercast));
        collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(context, R.color.colorOvercast));
    }
}
