package com.hencoder.a10_layout;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    private void showWindow(){
        WindowManager windowManager = getWindowManager();
        TextView textViewCompat = new TextView(this);
        textViewCompat.setText("zhuojianhai");

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                0,0, PixelFormat.TRANSPARENT
        );

        layoutParams.gravity = Gravity.LEFT|Gravity.TOP;
        windowManager.addView(textViewCompat,layoutParams);
    }
}
