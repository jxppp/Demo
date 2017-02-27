package com.example.administrator.asynctaskloadimage.notUseCache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2015/11/30.
 */
public class ImageLoaderWithoutCaches {

    private Handler mHandler;
    private static final int targetWidth = 160;
    private static final int targetHeight = 160;

    /**
     * Using Thread
     * @param imageView
     * @param url
     */
    public void showImageByThread(final ImageView imageView, final String url) {

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                ImgHolder holder = (ImgHolder) msg.obj;
                if (holder.imageView.getTag().equals(holder.url)) {
                    holder.imageView.setImageBitmap(holder.bitmap);
                }
            }
        };
        new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = getBitmapFromUrl(url);
                Message message = Message.obtain();
                message.obj = new ImgHolder(imageView, bitmap, url);
                mHandler.sendMessage(message);
            }
        }.start();
    }

    /**
     * Using ASyncTask
     * @param imageView
     * @param url
     */
    public void showImageByASync(ImageView imageView, String url) {
        ASyncDownloadImage task = new ASyncDownloadImage(imageView, url);
        task.execute(url);
    }

    private static Bitmap getBitmapFromUrl(String urlString) {
        Bitmap bitmap;
        InputStream is = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            is = new BufferedInputStream(conn.getInputStream());
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = getInSampleSize(urlString, targetWidth, targetHeight);
            options.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeStream(is, null, options);
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

    public static int getInSampleSize(String url,
                                      int targetWidth, int targetHeight) throws Exception{
        InputStream is = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        URL myUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) myUrl.openConnection();
        is = new BufferedInputStream(conn.getInputStream());
        BitmapFactory.decodeStream(is, null, options);
        // 原始图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > targetHeight || width > targetWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRate = Math.round((float) height / (float) targetHeight);
            final int widthRate = Math.round((float) width / (float) targetWidth);
            inSampleSize = heightRate < widthRate ? heightRate : widthRate;
        }
        options.inJustDecodeBounds = false;
        return inSampleSize;
    }

    private class ImgHolder {
        public Bitmap bitmap;
        public ImageView imageView;
        public String url;

        public ImgHolder(ImageView iv, Bitmap bm,String url) {
            this.imageView = iv;
            this.bitmap = bm;
            this.url = url;
        }
    }
}
