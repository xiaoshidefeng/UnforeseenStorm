package com.example.cw.unforeseenstorm.NetWork;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.Log;
import android.widget.ImageView;

import com.example.cw.unforeseenstorm.Bean.RealWeatherBean;
import com.example.cw.unforeseenstorm.Tool.ConstClass;
import com.example.cw.unforeseenstorm.WeatherStrategy.StrategyContext;
import com.example.cw.unforeseenstorm.WeatherStrategy.Weathers.Couldy;
import com.example.cw.unforeseenstorm.WeatherStrategy.Weathers.Rainy;
import com.example.cw.unforeseenstorm.WeatherStrategy.Weathers.Sunny;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by cw on 2017/5/28.
 */

public class GetRealWeather {

    private String city;
    private JSONArray jsonArray;
    private Context context;
    private RealWeatherBean realWeatherBean;
    private ImageView imageView;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    public GetRealWeather(String city, Context context,
                          ImageView imageView, CollapsingToolbarLayout collapsingToolbarLayout) {
        this.city = city;
        this.context = context;
        this.imageView = imageView;
        this.collapsingToolbarLayout = collapsingToolbarLayout;
    }

    /**
     * 策略模式
     */

    public void handler(){
        handler.sendEmptyMessage(1);//此处发送消息给handler,然后handler接收消息并处理消息进而更新ui
    }
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            if(jsonArray != null){

                Log.e("test", realWeatherBean.toString());


                StrategyContext strategyContext = new StrategyContext();


                //策略
                if(realWeatherBean.text.equals("晴")) {
                    strategyContext.setWeather(new Sunny());

                }else if(realWeatherBean.text.equals("阴")) {
                    strategyContext.setWeather(new Couldy());

                }else if(realWeatherBean.text.equals("雨")) {
                    strategyContext.setWeather(new Rainy());

                }
                strategyContext.showWeather(GetRealWeather.this.imageView,
                        GetRealWeather.this.collapsingToolbarLayout, realWeatherBean);

            }

        }
    };

    public void getRealWeather() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpURLConnection connection = null;
                try {
                    String s = ConstClass.API_REALTIME_WEATHER + GetRealWeather.this.city + "&key=" + ConstClass.API_KEY;
                    URL url = new URL(s);
                    Log.e("errss", s.toString());

                    connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();

                    //连接超时设置
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);

                    //获取输入流
                    InputStream in = connection.getInputStream();

                    //对获取的流进行读取
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in,"utf-8"));
                    final StringBuilder response = new StringBuilder();
                    String line=null;
                    while ((line=reader.readLine())!=null){
                        response.append(line);
                    }

                    JSONObject json = new JSONObject(response.toString());

                    jsonArray = json.getJSONArray("HeWeather5");
                    JSONObject jsonObject;
                    for(int i = 0; i < jsonArray.length(); i++){

                        //解决重名
                        if(jsonArray.length() == 1) {
                            jsonObject = jsonArray.getJSONObject(0);
                        }else {
                            jsonObject = jsonArray.getJSONObject(1);
                        }

                        addMsg(jsonObject);
                    }

                    handler();

                }   catch (Exception e) {
                    Log.e("errss", e.getMessage());
                }
            }
        }).start();

    }

    public void addMsg(JSONObject jsonObject) throws JSONException {

        Log.e("RealJson", jsonObject.toString());

        JSONObject basic = jsonObject.getJSONObject("basic");
        String cityname = basic.getString("city");
        JSONObject update = basic.getJSONObject("update");
        String time = update.getString("loc");

        JSONObject now = jsonObject.getJSONObject("now");

        JSONObject cond = now.getJSONObject("cond");
        String code = cond.getString("code");
        String txt = cond.getString("txt");

        String bodyTmp = now.getString("fl");
        String xiangDuiShiDu = now.getString("hum");
        String jiangShuiLiang = now.getString("pcpn");
        String qiYa = now.getString("pres");
        String tmp = now.getString("tmp");
        String nengJianDu = now.getString("vis");

        JSONObject wind = now.getJSONObject("wind");
        String dir = wind.getString("dir");
        String windForce = wind.getString("sc");
        String windSpeed = wind.getString("spd");


        realWeatherBean = new RealWeatherBean(
                cityname,
                time,
                code,
                txt,
                bodyTmp,
                xiangDuiShiDu,
                jiangShuiLiang,
                qiYa,
                tmp,
                nengJianDu,
                dir,
                windForce,
                windSpeed
        );

    }

}
