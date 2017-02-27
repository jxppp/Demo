package com.example.asynctasktest;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private AsyncTask mAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_open_task).setOnClickListener(this);
        findViewById(R.id.btn_open_task2).setOnClickListener(this);
        findViewById(R.id.btn_close_task).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_open_task:
                mAsyncTask = new MyAsyncTask(this, new MyAsyncTask.AsyncTaskCallback() {
                    @Override
                    public void onPreExecute(ProgressDialog dialog) {
                        dialog.setMessage("jxp");
                    }

                    @Override
                    public String doInBackground(Void... params) {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    public void onPostExecute(String result) {

                    }
                }).execute();
                break;
            case R.id.btn_open_task2:
                mAsyncTask = new MyAsyncTask(this, new MyAsyncTask.AsyncTaskCallback() {
                    @Override
                    public void onPreExecute(ProgressDialog dialog) {
                        dialog.setMessage("pxj");
                    }

                    @Override
                    public String doInBackground(Void... params) {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    public void onPostExecute(String result) {

                    }
                }).execute();
                break;
            case R.id.btn_close_task:
                if (mAsyncTask != null) {
                    Log.d("rd96", "AsyncTask status:::" + mAsyncTask.getStatus());
                    mAsyncTask.cancel(true);
                }
                break;
        }
    }
}
