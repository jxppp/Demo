package com.example.administrator.asynctaskloadimage.useCache;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.administrator.asynctaskloadimage.R;

import java.util.List;

/**
 * Created by Administrator on 2015/12/2.
 */
public class MyAdapterUseCaches extends BaseAdapter implements AbsListView.OnScrollListener{

    private LayoutInflater mInflater;
    private List<String> mData;
    private ImageLoaderWithCache mImageLoader;
    private ImageLoaderWithDoubleCaches imageLoaderWithDoubleCaches;
    private int mStart = 0, mEnd = 0;
    private boolean mFirstFlag;

    public MyAdapterUseCaches(Context context, List<String> mData, ListView listView) {
        this.mData = mData;
        mInflater = LayoutInflater.from(context);
//        mImageLoader = new ImageLoaderWithCache(listView);
//        mImageLoader.loadImages(mStart, mEnd);
        imageLoaderWithDoubleCaches = new ImageLoaderWithDoubleCaches(context, listView);
        imageLoaderWithDoubleCaches.loadImages(mStart, mEnd);
        mFirstFlag = true;
        listView.setOnScrollListener(this);
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
//        mImageLoader.showImage(url, viewHolder.imageView);
        imageLoaderWithDoubleCaches.showImage(url, viewHolder.imageView);
        return convertView;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        if (scrollState == SCROLL_STATE_IDLE){
            imageLoaderWithDoubleCaches.loadImages(mStart, mEnd);
        }else {
//            mImageLoader.cancelAllTasks();
            imageLoaderWithDoubleCaches.cancelAllTasks();
        }

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        mStart = firstVisibleItem;
        mEnd = firstVisibleItem + visibleItemCount;
        if (mFirstFlag && visibleItemCount > 0){
            imageLoaderWithDoubleCaches.loadImages(mStart, mEnd);
            mFirstFlag = false;
        }
    }

    public class ViewHolder {
        public ImageView imageView;
    }
}
