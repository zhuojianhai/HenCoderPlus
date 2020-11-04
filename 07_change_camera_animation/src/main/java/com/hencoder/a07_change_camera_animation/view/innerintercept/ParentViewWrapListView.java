package com.hencoder.a07_change_camera_animation.view.innerintercept;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.Scroller;

public class ParentViewWrapListView extends ViewGroup {

    private int lastX;
    private int lastY;
    private Scroller mScroller;
    public ParentViewWrapListView(Context context) {
        super(context);
        init();
    }

    public ParentViewWrapListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ParentViewWrapListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    private void init(){
        mScroller = new Scroller(getContext());

    }
    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            lastX = x;
            lastY  = y;
            if (!mScroller.isFinished()){
                mScroller.abortAnimation();
                return true;
            }
            return false;

        }else{
            return true;
        }
    }
}
