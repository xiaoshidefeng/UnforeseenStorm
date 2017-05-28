package com.example.cw.unforeseenstorm.Bean;

/**
 * Created by cw on 2017/5/28.
 */

public class RealWeatherBean {
    public String city;
    public String time;
    public String code;
    public String text;
    public String bodyTmp;
    public String xiangDuiShiDu;
    public String jiangShuiLiang;
    public String qiYa;
    public String tmp;
    public String nengJianDu;
    public String windDir;
    public String windForce;
    public String windSpeed;

    public RealWeatherBean(String city, String time, String code
            , String text, String bodyTmp, String xiangDuiShiDu
            , String jiangShuiLiang, String qiYa, String tmp
            , String nengJianDu, String windDir, String windForce
            , String windSpeed) {
        this.city = city;
        this.time = time;
        this.code = code;
        this.text = text;
        this.bodyTmp = bodyTmp;
        this.xiangDuiShiDu = xiangDuiShiDu;
        this.jiangShuiLiang = jiangShuiLiang;
        this.qiYa = qiYa;
        this.tmp = tmp;
        this.nengJianDu = nengJianDu;
        this.windDir = windDir;
        this.windForce = windForce;
        this.windSpeed = windSpeed;
    }
}
