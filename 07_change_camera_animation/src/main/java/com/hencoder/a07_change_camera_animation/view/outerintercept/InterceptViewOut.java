package com.hencoder.a07_change_camera_animation.view.outerintercept;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewGroup;
import android.widget.Scroller;

public class InterceptViewOut extends ViewGroup {
    //上次滑动到的位置(onInterceptTouchEvent)
    private int lastInterceptX;
    private int lastInterceptY;


    //上次滑动到的位置(onToucheEvent)
    private int lastX;
    private int lastY;

    private Scroller mScroller;

    private boolean mIntercept = false;

    private VelocityTracker velocityTracker;

    public InterceptViewOut(Context context) {
        super(context);
        init();
    }

    public InterceptViewOut(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public InterceptViewOut(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mScroller = new Scroller(getContext());
        velocityTracker = VelocityTracker.obtain();
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }


    /***
     * 外部拦截法，根据具体业务逻辑决定是否需要拦截滑动事件
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptHoverEvent(MotionEvent ev) {
        int currentX = (int) ev.getX();
        int currentY = (int) ev.getY();
        switch (ev.getAction()){

            case MotionEvent.ACTION_DOWN:
                mIntercept = false;
                if (!mScroller.isFinished()){
                    mScroller.abortAnimation();
                    mIntercept = true;
                }

                break;
            case MotionEvent.ACTION_MOVE:

                int deltaX = currentX - lastInterceptX;
                int deltaY = currentY - lastInterceptY;

                if (Math.abs(deltaX)>Math.abs(deltaY)){
                    mIntercept = true;
                }else{
                    mIntercept = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                mIntercept = false;
                break;
        }

        lastInterceptX = currentX;
        lastInterceptY = currentY;

        lastX = currentX;
        lastY = currentY;

        return mIntercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        velocityTracker.addMovement(event);
        int currentX = (int) event.getX();
        int currentY = (int) event.getY();
        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:

                int deltaX = currentX - lastX;
                int deltaY = currentY - lastY;

                scrollBy(-deltaX,-deltaY);
                break;
            case MotionEvent.ACTION_UP:
               int scrollX = getScrollX();
               int scrollY = getScrollY();
               velocityTracker.computeCurrentVelocity(1000);
               int velocityX = (int) velocityTracker.getXVelocity();
               int velocityY = (int) velocityTracker.getYVelocity();

                break;
        }


        lastX = currentX;
        lastY = currentY;
        return true;
    }


    private void smoothScroll(int dx,int dy){
        mScroller.startScroll(getScrollX(),0,dx,0,500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
        }
    }
}
