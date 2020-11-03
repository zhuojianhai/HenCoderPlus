package com.hencoder.a07_change_camera_animation.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.hencoder.a07_change_camera_animation.R;

public class ScrollLinearLayout extends LinearLayout {


    Scroller scroller;
    VelocityTracker velocityTracker;

    float lastX;
    float lastY;

    public ScrollLinearLayout(Context context) {
        super(context);
        init(context);
    }

    public ScrollLinearLayout(Context context, AttributeSet attrs) {
       super(context, attrs);
        init(context);
    }

    public ScrollLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        setOrientation(HORIZONTAL);
        scroller = new Scroller(context);
        velocityTracker = VelocityTracker.obtain();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        velocityTracker.addMovement(event); 不能放在这个位置，否则会有问题
        if (velocityTracker == null){
            velocityTracker = VelocityTracker.obtain();
        }
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                velocityTracker.addMovement(event);
                lastX = event.getX();
                lastY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                velocityTracker.addMovement(event);
                float currentX = event.getX();
                float currentY = event.getY();

                float diffX = currentX - lastX;
                float diffy = currentY - lastY;

                //想要滑动方向正常，传入参数必须何实际值相反
                scrollBy(-(int)diffX,-(int)diffy);
//                scrollBy(-(int)diffX,0);


                lastY = currentY;
                lastX = currentX;
                break;
            case MotionEvent.ACTION_UP:
                //在触点抬起，继续滑动的距离
                int xVelocity = getXScrollVelocity();
                int yVeloctiy = getYScrollVelocity();
                scroller.forceFinished(true);
                scroller.fling(getScrollX(),getScrollY(),(int) (-0.5 * xVelocity), (int) (-0.5 * yVeloctiy), -2000, 2000, -2000, 2000);
                recyclerVelocityTracker();
                break;

        }
        invalidate();
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(),scroller.getCurrY());
            postInvalidate();
        }
    }

    private void recyclerVelocityTracker(){
        if (velocityTracker!=null){
            velocityTracker.recycle();
            velocityTracker = null;
        }
    }

    /**
     * 获取X方向的滑动速度,大于0向右滑动，反之向左
     * @return
     */
    private int getXScrollVelocity(){
        velocityTracker.computeCurrentVelocity(1000);
        int velocity = (int) velocityTracker.getXVelocity();
        return velocity;
    }

    private  int getYScrollVelocity(){
        velocityTracker.computeCurrentVelocity(1000);
        int yVelocity = (int) velocityTracker.getYVelocity();
        return yVelocity;
    }
}
