package com.hencoder.a15_drag_nestedscroll.view.drag;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

import com.hencoder.a15_drag_nestedscroll.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

/**
 * ViewDragHelper 的使用场景
 * 必须要重写onInterceptOntouchEvent() 和 onTouchEvent() 方法
 */
public class DragUpDownLayout extends FrameLayout {

    View view;//当前操作的View

    ViewDragHelper dragHelper;

    ViewDragHelper.Callback dragListener = new DragListener();

    ViewConfiguration viewConfiguration;

    public DragUpDownLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        dragHelper = ViewDragHelper.create(this, dragListener);
        viewConfiguration = ViewConfiguration.get(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        view = findViewById(R.id.view);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return dragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    /**
     * 垂直方向拖拽事件监听
     */
    class DragListener extends ViewDragHelper.Callback {
        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return child == view;
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            return top;
        }

        //松手事件监听
        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            if (Math.abs(yvel) > viewConfiguration.getScaledMinimumFlingVelocity()) {
                if (yvel > 0) {
                    dragHelper.settleCapturedViewAt(0, getHeight() - releasedChild.getHeight());
                } else {
                    dragHelper.settleCapturedViewAt(0, 0);
                }
            } else {
                if (releasedChild.getTop() < getHeight() - releasedChild.getBottom()) {
                    dragHelper.settleCapturedViewAt(0, 0);
                } else {
                    dragHelper.settleCapturedViewAt(0, getHeight() - releasedChild.getHeight());
                }
            }
            postInvalidateOnAnimation();
        }
    }

}
