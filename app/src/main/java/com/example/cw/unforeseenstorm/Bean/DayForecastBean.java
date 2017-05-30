package com.example.cw.unforeseenstorm.Bean;

/**
 * Created by cw on 2017/5/30.
 */

public class DayForecastBean {
    public String txt_d;
    public String date;
    public String maxTmp;
    public String minTmp;

    public DayForecastBean(String txt_d, String date, String maxTmp, String minTmp) {
        this.txt_d = txt_d;
        this.date = date;
        this.maxTmp = maxTmp;
        this.minTmp = minTmp;
    }
}
