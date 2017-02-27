package com.example.administrator.listviewitemslide;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ListView;

/**
 * Created by Administrator on 2015/11/9.
 */
public class MainActivity extends Activity {

    private ListView mListView;
    private ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView =(ListView) findViewById(R.id.my_list);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        adapter = new ListViewAdapter(this, dm.widthPixels);

        Log.d("rd96", dm.widthPixels+"");
        mListView.setAdapter(adapter);
    }
}
