package com.hencoder.a07_change_camera_animation.view.innerintercept;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class ListViewExInnerIntercepter extends ListView {
    private int lastOntouchX;
    private int lastOntouchY;

    public ListViewExInnerIntercepter(Context context) {
        super(context);
    }

    public ListViewExInnerIntercepter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewExInnerIntercepter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
                int delatX = currentX - lastOntouchX;
                int delatY = currentY - lastOntouchY;
                if (Math.abs(delatX)>Math.abs(delatY)){
                    getParent().requestDisallowInterceptTouchEvent(false);
                }

                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        lastOntouchX = currentX;
        lastOntouchY = currentX;
        return super.dispatchTouchEvent(ev);
    }
}
