package com.example.cw.unforeseenstorm.WeatherStrategy;

import android.content.Context;
import android.support.design.widget.CollapsingToolbarLayout;
import android.widget.ImageView;

import com.example.cw.unforeseenstorm.Bean.RealWeatherBean;

/**
 * Created by cw on 2017/5/29.
 * 天气接口
 */

public interface Weather {
    void weather(Context context, ImageView imageView, CollapsingToolbarLayout collapsingToolbarLayout,
                 RealWeatherBean realWeatherBean);
}
