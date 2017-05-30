package com.example.cw.unforeseenstorm.WeatherStrategy.Weathers;

import android.support.design.widget.CollapsingToolbarLayout;
import android.widget.ImageView;

import com.example.cw.unforeseenstorm.Bean.RealWeatherBean;
import com.example.cw.unforeseenstorm.R;
import com.example.cw.unforeseenstorm.WeatherStrategy.Weather;

/**
 * Created by cw on 2017/5/29.
 */

public class Couldy implements Weather {

    @Override
    public void weather(ImageView imageView, CollapsingToolbarLayout collapsingToolbarLayout, RealWeatherBean realWeatherBean) {
        imageView.setImageResource(R.mipmap.img_couldy_day);
        collapsingToolbarLayout.setTitle(realWeatherBean.text + "  " + realWeatherBean.tmp + "℃");

    }
}
