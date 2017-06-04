package com.example.cw.unforeseenstorm.NetWork;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.example.cw.unforeseenstorm.Tool.ConstClass;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by cw on 2017/6/4.
 */

public class GetVersion {
    private Context context;
    private String version;
    private String newVersion;

    public GetVersion(Context context, String version) {
        this.context = context;
        this.version = version;
    }


    public void handler5(){
        handler.sendEmptyMessage(1);//此处发送消息给handler,然后handler接收消息并处理消息进而更新ui
    }
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            if(!newVersion.equals("  " + GetVersion.this.version)) {
                //不是最新版本 需要更新
                AlertDialog.Builder builder=new AlertDialog.Builder(GetVersion.this.context);
                builder.setTitle("更新通知");
                builder.setMessage("检测到您当前的版本(" + GetVersion.this.version + ")不是最新版本" +
                        "(" + newVersion + ")是否立即下载更新");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(ConstClass.API_DOWNLOAD_NEW_VERSION));
                        GetVersion.this.context.startActivity(intent);
                    }
                });
                builder.setNegativeButton("取消",null);
                builder.show();

            }

        }
    };

    public void getVersion() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpURLConnection connection = null;
                try {
                    String s = ConstClass.API_GET_NEW_VERSION;
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


                    newVersion = response.toString();
                    Log.e("errss", newVersion);

                    handler5();

                }   catch (Exception e) {
                    Log.e("errss", e.getMessage());
                }
            }
        }).start();

    }

}
