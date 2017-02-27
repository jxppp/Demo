package com.example.myapplication;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.FileDescriptor;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2016/7/16.
 */
public class MyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("rd96", "service onBind----------------");
        return new MyBinder();
    }

    public MyService() {
        super();
    }

    @Override
    public void onCreate() {
        Log.d("rd96", "service onCreate----------------");
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.d("rd96", "service onStart----------------");
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("rd96", "service onStartCommand----------------");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d("rd96", "service onDestroy----------------");
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d("rd96", "service onConfigurationChanged----------------");
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        Log.d("rd96", "service onLowMemory----------------");
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        Log.d("rd96", "service onTrimMemory----------------");
        super.onTrimMemory(level);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("rd96", "service onUnbind----------------");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d("rd96", "service onRebind----------------");
        super.onRebind(intent);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.d("rd96", "service onTaskRemoved----------------");
        super.onTaskRemoved(rootIntent);
    }

    @Override
    protected void dump(FileDescriptor fd, PrintWriter writer, String[] args) {
        Log.d("rd96", "service dump----------------");
        super.dump(fd, writer, args);
    }

    public class MyBinder extends Binder{
        public MyService getService() {
            return MyService.this;
        }

        public int getMyNum {

        }

    }
}
