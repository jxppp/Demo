package com.example.administrator.asynctaskloadimage.notUseCache;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.administrator.asynctaskloadimage.R;

import java.util.List;

/**
 * Created by Administrator on 2015/11/30.
 */
public class MyAdapterNotUseCaches extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<String> mData;
    private ImageLoaderWithoutCaches mImageLoader = new ImageLoaderWithoutCaches();

    public MyAdapterNotUseCaches(Context context, List<String> mData) {
        mInflater = LayoutInflater.from(context);
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String url = mData.get(position);
        ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_image, parent, false);
            viewHolder.imageView =(ImageView) convertView.findViewById(R.id.iv_loadImage);
            convertView.setTag(viewHolder);
        }else {
            viewHolder =(ViewHolder) convertView.getTag();
        }
        viewHolder.imageView.setTag(url);
        viewHolder.imageView.setImageResource(R.drawable.ic_launcher);
//        mImageLoader.showImageByThread(viewHolder.imageView, url);
        mImageLoader.showImageByASync(viewHolder.imageView, url);
        return convertView;
    }

    public class ViewHolder{
        public ImageView imageView;
    }

}
