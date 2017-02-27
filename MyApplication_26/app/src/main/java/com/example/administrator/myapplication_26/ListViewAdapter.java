package com.example.administrator.myapplication_26;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2015/10/27.
 */
public class ListViewAdapter extends BaseAdapter {

    private Context context;
    private List<TestEntity> list;
    private LayoutInflater inflater;

    public ListViewAdapter(Context context, List<TestEntity> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        if(list.size() != 0){
            return list.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if(convertView == null){
            holder = new Holder();
            convertView = inflater.inflate(R.layout.item_list, parent, false);
            holder.name =(TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(holder);
        }else {
            holder =(Holder) convertView.getTag();
        }

        TestEntity entity = list.get(position);
        holder.name.setText(entity.getName());

        return convertView;
    }

    public void remove(int position){
        list.remove(position);
    }

    class Holder{
        TextView name;
    }

}
