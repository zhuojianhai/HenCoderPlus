package com.zjh.a05_hookactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zjh.a05_hookactivity.utils.HookHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button amsHook;
    Button handlerHook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        amsHook = findViewById(R.id.button1);
        handlerHook = findViewById(R.id.button2);
        amsHook.setOnClickListener(this);
        handlerHook.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId){
            case R.id.button1:
                HookHelper.hookAMSInterceptStartActivity();
                HookHelper.hookH();
//                Intent intent = new Intent(this,TargetActivity.class);
                Intent intent = new Intent(this,TargetActivity.class);
                startActivity(intent);
                break;
            case R.id.button2:
                break;
        }
    }
}
