package com.example.cw.unforeseenstorm.NetWork;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.cw.unforeseenstorm.Adapter.DayForecastAdapter;
import com.example.cw.unforeseenstorm.Bean.DayForecastBean;
import com.example.cw.unforeseenstorm.MyDividerItemDecoration;
import com.example.cw.unforeseenstorm.Tool.ConstClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by cw on 2017/5/30.
 */

public class GetDayForecast {

    private JSONObject jsonObject;

    private Context context;

    private String city;

    private RecyclerView recyclerView;

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    ArrayList<DayForecastBean> dayForecastArrayList = new ArrayList();

    public GetDayForecast(Context context, String city, RecyclerView recyclerView) {
        this.context = context;
        this.city = city;
        this.recyclerView = recyclerView;
    }

    public void handler3(){
        handler.sendEmptyMessage(1);//此处发送消息给handler,然后handler接收消息并处理消息进而更新ui
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
//            if(jsonObject != null && !dayForecastArrayList.isEmpty()){
                layoutManager = new LinearLayoutManager(GetDayForecast.this.context, LinearLayoutManager.HORIZONTAL, false);
                adapter = new DayForecastAdapter(dayForecastArrayList);
                recyclerView.setAdapter(adapter);
                // 设置布局管理器
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.addItemDecoration(new MyDividerItemDecoration(GetDayForecast.this.context, LinearLayoutManager.HORIZONTAL));
                // 设置adapter

//            }

        }
    };


    /**
     * 功能 获取预测数据 init
     * 方法 GET
     */
    public void getDayForcast(){

        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpURLConnection connection = null;
                try {
                    String s = ConstClass.API_DAY_FORECAST +
                            GetDayForecast.this.city + "&key=" +
                            ConstClass.API_KEY;

                    URL url  = new URL(s);

                    Log.e("url", url.toString());

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
                        handler3();
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
        Log.e("errss", jsonObject1.toString());
        if(jsonObject1.getString("status").equals("ok") && jsonObject1.has("daily_forecast")) {
            //返回正常数据
            JSONArray hourly_forecast = jsonObject1.getJSONArray("daily_forecast");

            for(int i = 0; i < hourly_forecast.length(); i++){
                JSONObject oneForecast = hourly_forecast.getJSONObject(i);

                JSONObject cond = oneForecast.getJSONObject("cond");
                String txt_d = cond.getString("txt_d");

                String date = oneForecast.getString("date");

                JSONObject tmp = oneForecast.getJSONObject("tmp");
                Log.e("errss", tmp.toString());
                String maxTmp = tmp.getString("max");
                String minTmp = tmp.getString("min");
                dayForecastArrayList.add(new DayForecastBean(
                        txt_d,
                        date,
                        maxTmp,
                        minTmp

                ));
            }

            return 1;
        }else {
            return 0;
        }

    }
}
