package com.example.myapplication;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

/**
 * Created by Administrator on 2016/7/16.
 */
public class MainActivity extends Activity {

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("rd96", "onServiceConnected :::" + name);
//            mMyService = ((MyService.MyBinder)service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("rd96", "onServiceDisconnected :::" + name);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        startService(new Intent(this, MyService.class));
        findViewById(R.id.btn_bind).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindService(new Intent(MainActivity.this, MyService.class), mConnection, BIND_AUTO_CREATE);
            }
        });
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Log.d("rd96", "activity onDestroy");
//        unbindService(mConnection);
//    }
}
