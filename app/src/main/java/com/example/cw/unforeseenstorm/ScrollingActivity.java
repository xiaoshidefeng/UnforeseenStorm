package com.example.cw.unforeseenstorm;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.cw.unforeseenstorm.NetWork.GetDayForecast;
import com.example.cw.unforeseenstorm.NetWork.GetHourlyForecast;
import com.example.cw.unforeseenstorm.NetWork.GetRealWeather;
import com.example.cw.unforeseenstorm.Tool.SetBarColor;
import com.github.mikephil.charting.charts.LineChart;

public class ScrollingActivity extends AppCompatActivity {

    String cityName;

    SharedPreferences sharedPreferences;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {

        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            // TODO Auto-generated method stub
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //可在其中解析amapLocation获取相应内容。
                    double locationType = amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    double latitude = amapLocation.getLatitude();//获取纬度
                    double jindu = amapLocation.getLongitude();
                    //这里是异步获取城市名
                    cityName = amapLocation.getCity();
                    tvCity.setText(cityName);

                    sharedPreferences = getSharedPreferences("LocationInfo",
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("city", cityName);

                    GetRealWeather getRealWeather = new GetRealWeather(cityName, ScrollingActivity.this, weatherImageView, mCollapsingToolbarLayout);
                    getRealWeather.getRealWeather();

                    GetHourlyForecast getHourlyForecast = new GetHourlyForecast(ScrollingActivity.this, hourlyLineChart, cityName);
                    getHourlyForecast.getHourlyForecast();

                    GetDayForecast getDayForecast = new GetDayForecast(ScrollingActivity.this, cityName, recyclerView);
                    getDayForecast.getDayForcast();

                    Log.e("Amap==经度：纬度", "locationType:"+locationType+",latitude:"+latitude + "经度" + jindu + "城市" + cityName);
                }else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError","location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }

    };

    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;



    private ImageView weatherImageView;

    private TextView tvCity;

    private LineChart hourlyLineChart;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    CollapsingToolbarLayout mCollapsingToolbarLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //设置透明状态栏
        SetBarColor.MakeBarTrans(ScrollingActivity.this);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        tvCity = (TextView) findViewById(R.id.id_TvCity);
        hourlyLineChart = (LineChart) findViewById(R.id.id_LineChartForeseen);
        weatherImageView = (ImageView) findViewById(R.id.id_img_weather);
        recyclerView = (RecyclerView) findViewById(R.id.id_RvDayWeather);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                weatherImageView.setImageResource(R.mipmap.img_couldy_day);
//
////                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
//            }
//        });



        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);

        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        /**
         * 获取一次定位
         */
        //该方法默认为false，true表示只定位一次
        mLocationOption.setOnceLocation(true);

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();


//        getList();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


//    public void getList() {
//        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        adapter = new MyAdapter(getData());
//
//        // 设置布局管理器
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
//        // 设置adapter
//        recyclerView.setAdapter(adapter);
//
//    }
//
//    private ArrayList<String> getData() {
//        ArrayList<String> data = new ArrayList<>();
//        String temp = " item";
//        for(int i = 0; i < 20; i++) {
//            data.add(i + temp);
//        }
//
//        return data;
//    }

}
