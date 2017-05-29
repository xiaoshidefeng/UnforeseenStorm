package com.example.cw.unforeseenstorm.NetWork;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.cw.unforeseenstorm.Facede.ChartFacede;
import com.example.cw.unforeseenstorm.Tool.ConstClass;
import com.github.mikephil.charting.charts.LineChart;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by cw on 2017/5/29.
 * 门面模式（外观模式）
 */

public class GetHourlyForecast {
    public static String[] times;

    public static double[] tmps;

    private JSONObject jsonObject;

    private Context context;

    private LineChart lineChart;

    private String city;

    public GetHourlyForecast(Context context, LineChart lineChart, String city) {
        this.context = context;
        this.lineChart = lineChart;
        this.city = city;
    }

    /**
     * 功能 更新获取预测天气实时数据ui init
     * 门面模式
     */
    public void handler2(){
        handler.sendEmptyMessage(1);//此处发送消息给handler,然后handler接收消息并处理消息进而更新ui
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(jsonObject != null){
                ChartFacede chartFacede = new ChartFacede(times, tmps, GetHourlyForecast.this.lineChart);
                chartFacede.makeChart();

            }

        }
    };


    /**
     * 功能 获取预测数据 init
     * 方法 GET
     */
    public void initEnterprises(){

        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpURLConnection connection = null;
                try {
                    String s = ConstClass.API_HOURLY_FORECAST +
                            GetHourlyForecast.this.city + "&key=" +
                            ConstClass.API_KEY;

                    URL url  = new URL(s);

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


                    jsonObject = new JSONObject(response.toString());


                    if(getJson(jsonObject) == 1) {
                        handler2();
                    }


                }   catch (Exception e) {
                    Log.e("errss", e.getMessage());
                }
            }
        }).start();
    }

    public int getJson(JSONObject jsonObject) throws JSONException {

        JSONArray jsonArray1 = jsonObject.getJSONArray("HeWeather5");
        JSONObject jsonObject1 = jsonArray1.getJSONObject(0);

        if(jsonObject1.getString("status").equals("ok")) {
            //返回正常数据
            JSONArray hourly_forecast = jsonObject1.getJSONArray("hourly_forecast");

            times = new String[hourly_forecast.length()];
            tmps = new double[hourly_forecast.length()];

            for(int i = 0,count = 0; i < hourly_forecast.length(); i++,count++){
                JSONObject oneForecast = hourly_forecast.getJSONObject(i);

                JSONObject cond = oneForecast.getJSONObject("cond");
                String code = cond.getString("code");
                String txt = cond.getString("txt");

                String date = oneForecast.getString("date");
                String xiangDuiShiDu = oneForecast.getString("hum");
                String jiangShuiGaiLv = oneForecast.getString("pop");
                String qiYa = oneForecast.getString("pres");
                String tmp = oneForecast.getString("tmp");

                JSONObject wind = oneForecast.getJSONObject("wind");
                String dir = wind.getString("dir");
                String windForce = wind.getString("sc");
                String windSpeed = wind.getString("spd");

                times[count] = date.substring(10,date.length()) + " " + txt;

                int a = Integer.parseInt(tmp);
                tmps[count] = a;
            }

            return 1;
        }else {
            return 0;
        }

    }


}
