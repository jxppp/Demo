package com.example.administrator.listviewandviewpage;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.administrator.listviewandviewpage.adapter.MyListViewAdapter;
import com.example.administrator.listviewandviewpage.holder.ViewHolder;
import com.example.administrator.listviewandviewpage.view.InterceptorFrameLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2015/12/14.
 */
public class MainActivity extends Activity implements AdapterView.OnItemClickListener {

    //用来存放listview的条目文本内容的集合
    private List<String> items = new ArrayList<>();

    //要显示的Listview
    private ListView mListView;

    //用来存放轮播图片的id
    private List<Integer> resList = Arrays.asList(R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        mListView = new ListView(this);

        ViewHolder holder = new ViewHolder(resList);

        View mHeaderView = holder.getRootView();
        mListView.addHeaderView(mHeaderView);

        mListView.setAdapter(new MyListViewAdapter(items, mListView.getHeaderViewsCount()));

        for (int i = 0; i < 50; i++){
            StringBuffer sb = new StringBuffer();
            sb.append("item");
            sb.append(i);
            items.add(sb.toString());
        }

        RelativeLayout rl =(RelativeLayout) findViewById(R.id.rl);

        InterceptorFrameLayout ifl = new InterceptorFrameLayout(MainActivity.this);

        ifl.addView(mListView);

        ifl.addInterceptorView(mHeaderView, InterceptorFrameLayout.ORIENTATION_LEFT
                | InterceptorFrameLayout.ORIENTATION_RIGHT);
        rl.addView(ifl);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(MainActivity.this, "item" + (position - mListView.getHeaderViewsCount()) + "被点击了！", Toast.LENGTH_SHORT).show();
    }
}
