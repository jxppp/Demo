package com.example.administrator.asynctaskloadimage;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.administrator.asynctaskloadimage.notUseCache.MyAdapterNotUseCaches;
import com.example.administrator.asynctaskloadimage.useCache.MyAdapterUseCaches;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2015/11/30.
 */
public class MainActivity extends Activity {
    private ListView mListView;
    private List<String> mData;
    private MyAdapterNotUseCaches adapter;
    private MyAdapterUseCaches myAdapterUseCaches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mListView =(ListView) findViewById(R.id.my_list);
        mData = Arrays.asList(Images.IMAGE_URLS);
//        adapter = new MyAdapterNotUseCaches(this, mData);
        myAdapterUseCaches = new MyAdapterUseCaches(this, mData, mListView);
        mListView.setAdapter(myAdapterUseCaches);
    }
}
