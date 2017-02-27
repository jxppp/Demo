package com.example.mylineviewtest;

import android.app.Activity;
import android.os.Bundle;

import com.example.mylineviewtest.view.LineController;
import com.example.mylineviewtest.view.LineView;

/**
 * Created by Administrator on 2016/8/17.
 */
public class MainActivity extends Activity {
    private LineView lineView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lineView = (LineView) findViewById(R.id.lineView);
        LineController mLineController = new LineController(lineView);


    }
}
