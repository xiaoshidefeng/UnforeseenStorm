package com.example.cw.unforeseenstorm.Tool;

import android.app.Activity;
import android.os.Build;
import android.view.WindowManager;

/**
 * Created by cw on 2017/5/28.
 */

public class SetBarColor {

    public static void MakeBarTrans(Activity activity){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
}
