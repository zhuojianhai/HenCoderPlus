package com.hencoder.a07_change_camera_animation.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.LinearLayout;
import android.widget.Scroller;


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

        velocityTracker.addMovement(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX = event.getX();
                lastY = event.getY();
                Log.e("MotionEvent.ACTION_DOWN","getLeft() = "+getLeft()+" getTop()="+getTop());
                break;
            case MotionEvent.ACTION_MOVE:

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
                Log.e("MotionEvent.ACTION_UP","getLeft() = "+getLeft()+" getTop()="+getTop());
//                Log.e("MotionEvent.ACTION_UP","getScrollX() = "+getScrollX()+" getScrollY()="+getScrollY());
                scroller.fling(getScrollX(),getScrollY(),(int) (-0.5 * xVelocity), (int) (-0.5 * yVeloctiy), -1000, getWidth(), -1000, getHeight());
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
            Log.e("computeScroll","getScrollX() = "+getScrollX()+" getScrollY()="+getScrollY());
            scrollTo(scroller.getCurrX(),scroller.getCurrY());
            postInvalidate();
        }
    }

    private void recyclerVelocityTracker(){
        if (velocityTracker!=null){
            velocityTracker.clear();
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        return super.dispatchTouchEvent(ev);
    }
}
