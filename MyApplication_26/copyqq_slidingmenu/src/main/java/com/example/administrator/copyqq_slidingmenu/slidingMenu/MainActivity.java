package com.example.administrator.copyqq_slidingmenu.slidingMenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.example.administrator.copyqq_slidingmenu.R;

/**
 * Created by Administrator on 2015/11/10.
 */
public class MainActivity extends Activity {

    private SlidingMenu mMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        mMenu = (SlidingMenu) findViewById(R.id.id_menu);
    }

    public void toggleMenu(View view)
    {
        mMenu.toggle();
    }
}
