package com.hencoder.a11_touch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.LogPrinter;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class MyToucheView extends View {

    private  int lastX;
    private int lastY;

    Paint mPaint;
    public MyToucheView(Context context) {
        super(context);
    }

    public MyToucheView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        int currentX = (int) event.getX();//当前水平位置
        int currentY = (int) event.getY();//当前垂直位置

        Log.e("CurrentX","action="+action+"-----"+currentX+"");
        Log.e("CurrentY","action="+action+"-----"+currentY+"");

        switch (action){
            case MotionEvent.ACTION_DOWN://同一事件序列，这个只会执行1次
                lastX = currentX;
                lastY = currentY;
                Log.e("lastX",lastX+"----");
                Log.e("lastY",lastY+"----");
                return true;
            case MotionEvent.ACTION_MOVE://同一事件序列，这个会执行多次
                int dx = (int) (event.getX() - lastX);//移动的水平距离
                int dy = (int) (event.getY() - lastY);//移动的垂直距离
                layout(getLeft()+dx,getTop()+dy,getRight()+dx,getBottom()+dy);
                return false;
            case MotionEvent.ACTION_UP:
                return false;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {

        return super.performClick();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(50,50,50,mPaint);
    }
}
