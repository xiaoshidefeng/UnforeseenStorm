package com.example.cw.unforeseenstorm.WeatherStrategy;

import android.support.design.widget.CollapsingToolbarLayout;
import android.widget.ImageView;

import com.example.cw.unforeseenstorm.Bean.RealWeatherBean;

/**
 * Created by cw on 2017/5/29.
 * 天气接口
 */

public interface Weather {
    void weather(ImageView imageView, CollapsingToolbarLayout collapsingToolbarLayout,
                 RealWeatherBean realWeatherBean);
}
