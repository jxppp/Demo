package com.example.administrator.mystudyqq;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Administrator on 2015/10/29.
 */
public class DownLoadManagerActivity extends Activity implements View.OnClickListener {

    private TextView tv_downLoad, tv_pause, tv_delete;
    private DownloadManager downloadManager;
    String apkUrl = "http://img.meilishuo.net/css/images/AndroidShare/Meilishuo_3.6.1_10006.apk";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_downLoad =(TextView) findViewById(R.id.tv_downLoad);
        tv_pause =(TextView) findViewById(R.id.tv_pause);
        tv_delete =(TextView) findViewById(R.id.tv_delete);

        tv_downLoad.setOnClickListener(this);
        tv_pause.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_downLoad:
                down();
                break;
            case R.id.tv_pause:

                break;
            case R.id.tv_delete:

                break;
        }
    }

    public void down(){
        downloadManager =(DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(apkUrl));
        request.setDestinationInExternalFilesDir(this, "jxp", "MeiLiShuo.apk");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setTitle("StarWrist 下载中");
        long reference = downloadManager.enqueue(request);
    }
}
