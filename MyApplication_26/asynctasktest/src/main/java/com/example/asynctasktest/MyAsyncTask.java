package com.example.asynctasktest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by Administrator on 2016/6/20.
 */
public class MyAsyncTask extends AsyncTask<Void, Void, String> {
    private AsyncTaskCallback mCallback;
    private ProgressDialog dialog;

    interface AsyncTaskCallback {
        void onPreExecute(ProgressDialog dialog);
        String doInBackground(Void... params);
        void onPostExecute(String result);
    }

    public MyAsyncTask(Context context, AsyncTaskCallback callback) {
        mCallback = callback;
        dialog = new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d("rd96", "MyAsyncTask -----> onPreExecute");
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                MyAsyncTask.this.cancel(true);
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        mCallback.onPreExecute(dialog);
    }

    @Override
    protected String doInBackground(Void... params) {
        Log.d("rd96", "MyAsyncTask -----> doInBackground");
        return mCallback.doInBackground(params);
    }

    @Override
    protected void onPostExecute(String s) {
        dialog.dismiss();
        super.onPostExecute(s);
        mCallback.onPostExecute(s);
        Log.d("rd96", "MyAsyncTask -----> onPostExecute");
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        Log.d("rd96", "MyAsyncTask -----> onCancelled");
    }

    //    class MyAsyncTask extends AsyncTask<Void, Integer, Boolean> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            Log.d("rd96", "MyAsyncTask -----> onPreExecute");
//        }
//
//        @Override
//        protected Boolean doInBackground(Void... params) {
//            if (isCancelled()) Log.d("rd96", "MyAsyncTask -----> isCancelled");
//            Log.d("rd96", "MyAsyncTask -----> doInBackground and status::" + getStatus());
//            try {
//                Thread.sleep(6000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            Log.d("rd96", "MyAsyncTask sleep 后");
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Boolean aBoolean) {
//            super.onPostExecute(aBoolean);
//            Log.d("rd96", "MyAsyncTask -----> onPostExecute");
//        }
//
//        @Override
//        protected void onCancelled() {
//            super.onCancelled();
//            Log.d("rd96", "MyAsyncTask -----> onCancelled");
//        }
//
//        @Override
//        protected void onCancelled(Boolean aBoolean) {
//            super.onCancelled(aBoolean);
//            Log.d("rd96", "MyAsyncTask -----> onCancelled::::aBoolean" + aBoolean);
//        }
//
//        @Override
//        protected void onProgressUpdate(Integer... values) {
//            super.onProgressUpdate(values);
//            Log.d("rd96", "MyAsyncTask -----> onProgressUpdate onProgressUpdate ::" + values);
//        }
//    }
//    class MyAsyncTask2 extends AsyncTask<Void, Integer, Boolean> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            Log.d("rd96", "MyAsyncTask2 -----> onPreExecute");
//        }
//
//        @Override
//        protected Boolean doInBackground(Void... params) {
//            if (isCancelled()) Log.d("rd96", "MyAsyncTask2 -----> isCancelled");
//            Log.d("rd96", "MyAsyncTask2 -----> doInBackground and status::" + getStatus());
//            try {
//                Thread.sleep(6000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            Log.d("rd96", "MyAsyncTask2222222222 sleep 后");
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Boolean aBoolean) {
//            super.onPostExecute(aBoolean);
//            Log.d("rd96", "MyAsyncTask2 -----> onPostExecute");
//        }
//
//        @Override
//        protected void onCancelled() {
//            super.onCancelled();
//            Log.d("rd96", "MyAsyncTask2 -----> onCancelled");
//        }
//
//        @Override
//        protected void onCancelled(Boolean aBoolean) {
//            super.onCancelled(aBoolean);
//            Log.d("rd96", "MyAsyncTask2 -----> onCancelled::::aBoolean" + aBoolean);
//        }
//
//        @Override
//        protected void onProgressUpdate(Integer... values) {
//            super.onProgressUpdate(values);
//            Log.d("rd96", "MyAsyncTask2 -----> onProgressUpdate onProgressUpdate ::" + values);
//        }
//    }

}
