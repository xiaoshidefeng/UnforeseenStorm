package com.example.cw.unforeseenstorm.Bean;

/**
 * Created by cw on 2017/5/28.
 */

public class RealWeatherBean {
    private String city;
    private String time;
    private String code;
    private String text;
    private String bodyTmp;
    private String xianDuiShiDu;
    private String jiangShuiLiang;
    private String qiYa;
    private String tmp;
    private String nengJianDu;
    private String windDir;
    private String windForce;
    private String windSpeed;

    public RealWeatherBean(String city, String time, String code
            , String text, String bodyTmp, String xianDuiShiDu
            , String jiangShuiLiang, String qiYa, String tmp
            , String nengJianDu, String windDir, String windForce
            , String windSpeed) {
        this.city = city;
        this.time = time;
        this.code = code;
        this.text = text;
        this.bodyTmp = bodyTmp;
        this.xianDuiShiDu = xianDuiShiDu;
        this.jiangShuiLiang = jiangShuiLiang;
        this.qiYa = qiYa;
        this.tmp = tmp;
        this.nengJianDu = nengJianDu;
        this.windDir = windDir;
        this.windForce = windForce;
        this.windSpeed = windSpeed;
    }
}
