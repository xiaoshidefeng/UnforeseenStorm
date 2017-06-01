package com.example.cw.unforeseenstorm.WeatherStrategy;

import android.content.Context;
import android.support.design.widget.CollapsingToolbarLayout;
import android.widget.ImageView;

import com.example.cw.unforeseenstorm.Bean.RealWeatherBean;

/**
 * Created by cw on 2017/5/29.
 * 上下文环境类
 */

public class StrategyContext {

    private Weather weather;

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public Context context;

    public void showWeather(Context context, ImageView imageView,
                            CollapsingToolbarLayout collapsingToolbarLayout,
                            RealWeatherBean realWeatherBean) {
        weather.weather(context, imageView, collapsingToolbarLayout, realWeatherBean);

    }
}
