package com.example.administrator.asynctaskloadimage.useCache;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.administrator.asynctaskloadimage.Images;
import com.example.administrator.asynctaskloadimage.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2015/12/2.
 */
public class ImageLoaderWithCache {
    private Set<ASyncDownloadImage2> mTasks;
    private LruCache<String, Bitmap> mMemoryCaches;
    private ListView mListView;

    public ImageLoaderWithCache(ListView listView) {
        this.mListView = listView;
        mTasks = new HashSet<>();
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 10;
        mMemoryCaches = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    public void showImage(String url, ImageView imageView) {
        Bitmap bitmap = getBitmapFromMemoryCaches(url);
        if (bitmap == null) {
            imageView.setImageResource(R.drawable.ic_launcher);
        } else {
            imageView.setImageBitmap(bitmap);
        }
    }

    public Bitmap getBitmapFromMemoryCaches(String url) {
        return mMemoryCaches.get(url);
    }

    public void addBitmapToMemoryCaches(String url, Bitmap bitmap) {
        if (getBitmapFromMemoryCaches(url) == null) {
            mMemoryCaches.put(url, bitmap);
        }
    }

    public void loadImages(int start, int end) {
        for (int i = start; i < end; i++) {
            String url = Images.IMAGE_URLS[i];
            Bitmap bitmap = getBitmapFromMemoryCaches(url);
            if (bitmap == null){
                ASyncDownloadImage2 task = new ASyncDownloadImage2(url);
                mTasks.add(task);
                task.execute(url);
            }else {
                ImageView imageView =(ImageView) mListView.findViewWithTag(url);
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    private static Bitmap getBitmapFromUrl(String urlString) {
        Bitmap bitmap;
        InputStream is = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            is = new BufferedInputStream(conn.getInputStream());
            bitmap = BitmapFactory.decodeStream(is);
            conn.disconnect();
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
            }
        }
        return null;
    }

    public void cancelAllTasks(){
        if (mTasks != null){
            for (ASyncDownloadImage2 task : mTasks){
                task.cancel(false);
            }
        }
    }

    class ASyncDownloadImage2 extends AsyncTask<String, Void, Bitmap> {
        private String url;

        public ASyncDownloadImage2(String url) {
            this.url = url;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            url = params[0];
            Bitmap bitmap = getBitmapFromUrl(url);
            if (bitmap != null){
                addBitmapToMemoryCaches(url, bitmap);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            ImageView imageView =(ImageView) mListView.findViewWithTag(url);
            if (imageView != null && bitmap != null){
                imageView.setImageBitmap(bitmap);
            }
            mTasks.remove(this);
        }
    }

}
