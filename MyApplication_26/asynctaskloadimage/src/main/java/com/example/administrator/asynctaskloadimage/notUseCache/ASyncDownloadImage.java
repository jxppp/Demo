package com.example.administrator.asynctaskloadimage.notUseCache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2015/12/1.
 */
public class ASyncDownloadImage extends AsyncTask<String, Void, Bitmap> {

    private ImageView imageView;
    private String url;
    private static final int targetWidth = 160;
    private static final int targetHeight = 160;

    public ASyncDownloadImage(ImageView imageView, String url){
        this.imageView = imageView;
        this.url = url;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        return getBitmapFromUrl(params[0]);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if (imageView.getTag().equals(url)){
            imageView.setImageBitmap(bitmap);
        }
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



//    private static Bitmap getBitmapFromUrl(String urlString) {
//        Bitmap bitmap;
//        InputStream is = null;
//        try {
//            URL url = new URL(urlString);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            is = new BufferedInputStream(conn.getInputStream());
//            bitmap = BitmapFactory.decodeStream(is);
//            conn.disconnect();
//            return bitmap;
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (is != null)
//                    is.close();
//            } catch (IOException e) {
//            }
//        }
//        return null;
//    }
}
