package com.hencoder.a07_change_camera_animation.view.outerintercept;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * 内部拦截法
 */
public class InterceptViewInner extends ViewGroup {

    private int lastX;
    private int lastY;
    public InterceptViewInner(Context context) {
        super(context);
    }

    public InterceptViewInner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InterceptViewInner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int currentX = (int) ev.getX();
        int currentY = (int) ev.getY();
        switch (ev.getAction()){

            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:

                int deltaX = currentX - lastX;
                int deltaY = currentY - lastY;
                if (true){//父类需要处理滑动事件
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                lastX = currentX;
                lastY = currentY;
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
