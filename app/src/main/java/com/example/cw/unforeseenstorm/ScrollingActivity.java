package com.example.cw.unforeseenstorm;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.cw.unforeseenstorm.NetWork.GetDayForecast;
import com.example.cw.unforeseenstorm.NetWork.GetHourlyForecast;
import com.example.cw.unforeseenstorm.NetWork.GetRealWeather;
import com.example.cw.unforeseenstorm.NetWork.GetSuggestion;
import com.example.cw.unforeseenstorm.NetWork.GetVersion;
import com.example.cw.unforeseenstorm.Tool.SetBarColor;
import com.github.mikephil.charting.charts.LineChart;
import com.zaaach.citypicker.CityPickerActivity;

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

//                    sharedPreferences = getSharedPreferences("LocationInfo",
//                            Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString("city", cityName);

                    getWeather(cityName);

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


    private static final int REQUEST_CODE_PICK_CITY = 0;



    private ImageView weatherImageView;

    private TextView tvCity;

    private LineChart hourlyLineChart;

    private RecyclerView recyclerView;

    private RecyclerView recyclerViewvSuggestion;

    CollapsingToolbarLayout mCollapsingToolbarLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //设置透明状态栏
        SetBarColor.MakeBarTrans(ScrollingActivity.this);
        //这里ACCESS_COARSE_LOCATION申请权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    132);//自定义的code
        }

        try {
            version();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        init();


    }

    private void version() throws PackageManager.NameNotFoundException {
        PackageManager pm = this.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(this.getPackageName(), 0);
        String versionName = pi.versionName;
        GetVersion toGetVersion = new GetVersion(this, versionName);
        toGetVersion.getVersion();
    }

    private void init() {


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        tvCity = (TextView) findViewById(R.id.id_TvCity);
        hourlyLineChart = (LineChart) findViewById(R.id.id_LineChartForeseen);
        weatherImageView = (ImageView) findViewById(R.id.id_img_weather);
        recyclerView = (RecyclerView) findViewById(R.id.id_RvDayWeather);
        recyclerViewvSuggestion = (RecyclerView) findViewById(R.id.id_RvSuggestion);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                weatherImageView.setImageResource(R.mipmap.img_couldy_day);
//
////                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
//            }
//        });

//        toolbar.setPopupTheme();
//

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

            //启动
            startActivityForResult(new Intent(ScrollingActivity.this, CityPickerActivity.class),
                    REQUEST_CODE_PICK_CITY);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void getWeather(String city) {

        tvCity.setText(city);

        GetRealWeather getRealWeather = new GetRealWeather(city, ScrollingActivity.this, weatherImageView, mCollapsingToolbarLayout);
        getRealWeather.getRealWeather();

        GetHourlyForecast getHourlyForecast = new GetHourlyForecast(ScrollingActivity.this, hourlyLineChart, city);
        getHourlyForecast.getHourlyForecast();

        GetDayForecast getDayForecast = new GetDayForecast(ScrollingActivity.this, city, recyclerView);
        getDayForecast.getDayForcast();

        GetSuggestion getSuggestion = new GetSuggestion(ScrollingActivity.this, city, recyclerViewvSuggestion);
        getSuggestion.getSuggestion();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_CITY && resultCode == RESULT_OK){
            if (data != null){
                String city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
                Toast.makeText(this, "当前选择" + city, Toast.LENGTH_LONG).show();
                getWeather(city);
//                resultTV.setText("当前选择：" + city);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //可在此继续其他操作。
        if(requestCode == 123) {
            init();
        }else {
//            init();
            Toast.makeText(this, "无法定位，请在系统中开启定位权限", Toast.LENGTH_LONG).show();
        }

    }



}
