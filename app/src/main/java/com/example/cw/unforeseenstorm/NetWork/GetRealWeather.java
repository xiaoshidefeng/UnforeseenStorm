package com.example.cw.unforeseenstorm.NetWork;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.cw.unforeseenstorm.Bean.RealWeatherBean;
import com.example.cw.unforeseenstorm.Tool.ConstClass;

import org.json.JSONArray;
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

    public GetRealWeather(String city, Context context, RealWeatherBean realWeatherBean) {
        this.city = city;
        this.context = context;
    }

    public void handler(){
        handler.sendEmptyMessage(1);//此处发送消息给handler,然后handler接收消息并处理消息进而更新ui
    }
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            if(jsonArray != null){


            }

        }
    };

    public void getRealWeather() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpURLConnection connection = null;
                try {
                    String s = ConstClass.API_REALTIME_WEATHER + city + ConstClass.API_KEY;
                    URL url = new URL(s);

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

                    jsonArray = new JSONArray(response.toString());

                    for(int i = 0; i < jsonArray.length(); i++){

                        //解决重名
                        if(jsonArray.length() == 1) {
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                        }else {
                            JSONObject jsonObject = jsonArray.getJSONObject(1);
                        }


                    }

                    handler();

                    //TODO

                }   catch (Exception e) {
                    Log.e("errss", e.getMessage());
                }
            }
        }).start();

    }

    public void addMsg(JSONObject jsonObject) {

    }

}
