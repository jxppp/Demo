package com.example.administrator.myapplication_26;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/26.
 */
public class MainActivity extends Activity implements SlideCutListView.RemoveListener{

    private ListView listView;
    private List<TestEntity> list;
    private ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView =(ListView) findViewById(R.id.my_list);
        list = new ArrayList<>();
        for(int i = 0; i < 20; i++){
            TestEntity entity = new TestEntity();
            entity.setName("jxp hello" + i);
            list.add(entity);
        }

        adapter = new ListViewAdapter(this, list);
        listView.setAdapter(adapter);
  //      listView.setRemoveListener(this);

        MyDialog.builderDialog(this, "jxp", "111", "222", "hahaha").show();

    }

    @Override
    public void removeItem(SlideCutListView.RemoveDirection direction, int position) {
        adapter.remove(position);
        adapter.notifyDataSetChanged();
        switch (direction){
            case RIGHT:
                Toast.makeText(this, "向右删除  " + position, Toast.LENGTH_SHORT).show();
                break;
            case LEFT:
                Toast.makeText(this, "向左删除  "+ position, Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }
}
