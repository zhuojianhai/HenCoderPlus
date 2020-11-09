package com.zjh.a04_okhttplearn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show();
    }
    private void show(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request originReq = chain.request();

                        Request interceptRequest = new Request.Builder().addHeader("zhuojianhai","zhuojianhai comes from jiangsu")
                                .url(originReq.url())
                                .build();
                        Log.i(TAG, "intercept: "+chain.request().toString());
                        return chain.proceed(interceptRequest);
                    }
                })
                .build();

        Request request = new Request.Builder().get()
                .url("https:www.baidu.com/")
                .build();

       Call call =  okHttpClient.newCall(request);
       call.enqueue(new Callback() {
           @Override
           public void onFailure(Call call, IOException e) {
               Log.i(TAG, "onFailure: "+e.getMessage());

           }

           @Override
           public void onResponse(Call call, Response response) throws IOException {
               Log.i(TAG, "onRequset: "+response.request().toString());
               Log.i(TAG, "onResponse: "+response.body().string());
           }
       });
    }

    private void showTCP(){
//        各占2个字节，分别写入源端口和目的端口。
        int sourcePort = 16;
        int destinationPort = 16;

        int seqNumber = 32;

        int ackNumber = 32;

        /**
         *  占4位，它指出TCP报文段的数据起始处距离TCP报文段的起始处有多远。
         *  这个字段实际上是指出TCP报文段的首部长度。
         *  由于首部中还有长度不确定的选项字段，因此数据偏移字段是必要的，
         *  但应注意，“数据偏移”的单位是32位字（即以4字节的字为计算单位）。
         *  由于4位二进制数能表示的最大十进制数字是15，
         *  因此数据偏移的最大值是60字节，这也是TCP首部的最大字节（即选项长度不能超过40字节）
         */
        int offsetHead = 4;

        //保留位
        int saveNumber = 6;

        int Urgent = 1;
        int Acknowledgment =1;
        int PSH =1;
        int RST = 1;
        int SYN = 1;
        int FIN = 1;

        int windowSize =16;

        int checknum = 16;
        int urgentPoint = 16;

        //真实传输的数据大小
        int dataSize = Integer.MAX_VALUE;
    }
}
