package com.hencoder.plus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

import com.hencoder.HandlerLearnActivity;

public class SportsViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports_view);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_POINTER_UP:
                Intent intent = new Intent(SportsViewActivity.this, HandlerLearnActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }
}