package com.hencoder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.hencoder.plus.R;

public class HandlerLearnActivity extends AppCompatActivity {

   static final  Handler mHandler = new Handler(Looper.getMainLooper()){
       @Override
       public void handleMessage(Message msg) {
           super.handleMessage(msg);

       }
   };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_learn);
    }


    private void showHandlerInf(){

        mHandler.post(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    private void showThreadLocal(){
        //这个类型的数据是线程本地存储
        ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>(){
            @Nullable
            @Override
            protected Integer initialValue() {
                return 0;
            }
        };

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                threadLocal.set(100);
            }
        });
        thread.setName("thread 1");
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                threadLocal.set(200);
            }
        });
        thread.setName("thread 2");


    }


    private void showAsyncTask(){
        new AsyncTask<Void,Void,String>(){

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                System.out.println("请求之前的准备工作");
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                System.out.println("将后台耗时任务请求的结果，回传给UI线程");
            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
                System.out.println("更新任务进度");
            }

            @Override
            protected void onCancelled(String s) {
                super.onCancelled(s);
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
            }

            @Override
            protected String doInBackground(Void... voids) {
                System.out.println("后台处理耗时任务操作，并返回任务结果");
                return "success";
            }
        };
    }


    private void shwoHandlerThread(){
        IntentService intentService = new IntentService("MyIntentService") {
            @Override
            protected void onHandleIntent(@Nullable Intent intent) {

            }
        };
    }

    private void decodeBitmapOptionsInfo(){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(),R.drawable.avatar_rengwuxian,options);

        int outWidth = options.outWidth;
        int outHeight = options.outHeight;
        int reqWidth = 100;
        int reqHeight = 100;

        int insameSize = 1;
        if (outHeight>reqHeight || outWidth >reqWidth){
            int halfW = outWidth /2;
            int halfH = outHeight /2;
            while ((halfH/insameSize)>= reqHeight && (halfW/insameSize)>=reqWidth ){
                insameSize*=2;
            }
        }
        options.inSampleSize = insameSize;
        options.inJustDecodeBounds = false;
        BitmapFactory.decodeResource(getResources(),R.drawable.avatar_rengwuxian,options);

    }

    private void shwowUncaughtExceptionHandler(){

    }

    public class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler{
        @Override
        public void uncaughtException(Thread t, Throwable e) {

        }
    }

}