package com.example.administrator.listviewandviewpage.application;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * Created by Administrator on 2015/12/14.
 */
public class BaseApplication extends Application {
    private static Context mContext;

    private static Handler mHandlr= new Handler() {
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getmContext() {
        return mContext;
    }

    public static Handler getMainThreadHandler(){
        return mHandlr;
    }

    public static int dip2px(int dip){
        final float scale = getmContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }
}
