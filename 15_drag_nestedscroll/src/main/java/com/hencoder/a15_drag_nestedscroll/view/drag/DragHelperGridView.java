package com.hencoder.a15_drag_nestedscroll.view.drag;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

public class DragHelperGridView extends ViewGroup {
    private static final int COLUMNS = 2;
    private static final int ROWS = 3;

    ViewDragHelper dragHelper;

    public DragHelperGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        dragHelper = ViewDragHelper.create(this, new DragCallback());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int specWidth = MeasureSpec.getSize(widthMeasureSpec);
        int specHeight = MeasureSpec.getSize(heightMeasureSpec);
        int childWidth = specWidth / COLUMNS;
        int childHeight = specHeight / ROWS;

        measureChildren(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY));

        setMeasuredDimension(specWidth, specHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        int childLeft;
        int childTop;
        int childWidth = getWidth() / COLUMNS;
        int childHeight = getHeight() / ROWS;
        for (int index = 0; index < count; index++) {
            View child = getChildAt(index);
            childLeft = index % 2 * childWidth;
            childTop = index / 2 * childHeight;
            child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
        }
    }

    // 使用ViewDragHelper必须要重写这个方法
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return dragHelper.shouldInterceptTouchEvent(ev);
    }

    // 使用ViewDragHelper必须要重写这个方法
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }

    /**
     * 调用invalidate()方法
     * 会回调这个方法，一直计算
     * 然后在invalidate 达到界面刷新
     */
    @Override
    public void computeScroll() {
        if (dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    /**
     * viewDragHelper 事件回调
     */
    private class DragCallback extends ViewDragHelper.Callback {
        float capturedLeft;
        float capturedTop;

        //尝试抓住view
        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return true;
        }

        @Override
        public void onViewDragStateChanged(int state) {
            if (state == ViewDragHelper.STATE_IDLE) {
                View capturedView = dragHelper.getCapturedView();
                if (capturedView != null) {
                    capturedView.setElevation(capturedView.getElevation() - 1);
                }
            }
        }

        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            return left;
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            return top;
        }

        @Override
        public void onViewCaptured(@NonNull View capturedChild, int activePointerId) {
            capturedChild.setElevation(getElevation() + 1);
            capturedLeft = capturedChild.getLeft();
            capturedTop = capturedChild.getTop();
        }

        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            dragHelper.settleCapturedViewAt((int) capturedLeft, (int) capturedTop);
            postInvalidateOnAnimation();
        }
    }
}
