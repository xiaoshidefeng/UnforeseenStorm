package com.example.cw.unforeseenstorm.NetWork;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.cw.unforeseenstorm.Adapter.SuggestionAdapter;
import com.example.cw.unforeseenstorm.Bean.SuggestionBean;
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
 * Created by cw on 2017/6/2.
 */

public class GetSuggestion {

    private JSONObject jsonObject;

    private Context context;

    private String city;

    private RecyclerView recyclerView;

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    ArrayList<SuggestionBean> suggestionBeanArrayList = new ArrayList();

    public GetSuggestion(Context context, String city, RecyclerView recyclerView) {
        this.context = context;
        this.city = city;
        this.recyclerView = recyclerView;
    }

    public void handler4(){
        handler.sendEmptyMessage(1);//此处发送消息给handler,然后handler接收消息并处理消息进而更新ui
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
//            if(jsonObject != null && !dayForecastArrayList.isEmpty()){
            layoutManager = new LinearLayoutManager(GetSuggestion.this.context, LinearLayoutManager.VERTICAL, false);
            adapter = new SuggestionAdapter(suggestionBeanArrayList);
            // 设置布局管理器
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.addItemDecoration(new MyDividerItemDecoration(GetSuggestion.this.context, LinearLayoutManager.VERTICAL));
            // 设置adapter
            recyclerView.setAdapter(adapter);
//            }

        }
    };


    /**
     * 功能 获取预测数据 init
     * 方法 GET
     */
    public void getSuggestion(){

        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpURLConnection connection = null;
                try {
                    String s = ConstClass.API_SUGGESTION +
                            GetSuggestion.this.city + "&key=" +
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
                        handler4();
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
        if(jsonObject1.getString("status").equals("ok") && jsonObject1.has("suggestion")) {
            //返回正常数据
            JSONObject suggestion = jsonObject1.getJSONObject("suggestion");

            JSONObject comf = suggestion.getJSONObject("comf");
            JSONObject cw = suggestion.getJSONObject("cw");
            JSONObject drsg = suggestion.getJSONObject("drsg");
            JSONObject sprot = suggestion.getJSONObject("sport");
            JSONObject uv = suggestion.getJSONObject("uv");

            String brf = comf.getString("brf");
            String txt = comf.getString("txt");
            suggestionBeanArrayList.add(new SuggestionBean(
                    "舒适度指数",
                    brf,
                    txt

            ));

            brf = cw.getString("brf");
            txt = cw.getString("txt");
            suggestionBeanArrayList.add(new SuggestionBean(
                    "洗车指数",
                    brf,
                    txt

            ));

            brf = drsg.getString("brf");
            txt = drsg.getString("txt");
            suggestionBeanArrayList.add(new SuggestionBean(
                    "穿衣指数",
                    brf,
                    txt

            ));

            brf = sprot.getString("brf");
            txt = sprot.getString("txt");
            suggestionBeanArrayList.add(new SuggestionBean(
                    "运动指数",
                    brf,
                    txt

            ));

            brf = uv.getString("brf");
            txt = uv.getString("txt");
            suggestionBeanArrayList.add(new SuggestionBean(
                    "紫外线指数",
                    brf,
                    txt

            ));

            return 1;
        }else {
            return 0;
        }

    }
}
