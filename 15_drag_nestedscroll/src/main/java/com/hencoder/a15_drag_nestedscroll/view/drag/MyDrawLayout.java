package com.hencoder.a15_drag_nestedscroll.view.drag;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.customview.widget.ViewDragHelper;

public class MyDrawLayout extends FrameLayout {

    ViewDragHelper mDragHelper;
    ViewGroup leftContent;
    ViewGroup mainContent;
    //打开抽屉开关的难易程度，越小 越难打开，1.0f为normal
    private float mSensitivity = 0.8f;


    public MyDrawLayout(@NonNull Context context) {
        this(context,null);
    }

    public MyDrawLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyDrawLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDragHelper = ViewDragHelper.create(this,mSensitivity,callback);

    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        return mDragHelper.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
             mDragHelper.processTouchEvent(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // 容错性检查 (至少有俩子View, 子View必须是ViewGroup的子类)
        if (getChildCount() < 2) {
            throw new IllegalStateException("布局至少有俩孩子. Your ViewGroup must have 2 children at " +
                    "least.");
        }
        if (!(getChildAt(0) instanceof ViewGroup && getChildAt(1) instanceof ViewGroup)) {
            throw new IllegalArgumentException("子View必须是ViewGroup的子类. Your children must be an " +
                    "instance of ViewGroup");
        }
        leftContent = (ViewGroup) getChildAt(0);
        mainContent = (ViewGroup) getChildAt(1);
    }

    ViewDragHelper.Callback callback = new ViewDragHelper.Callback(){

        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return false;
        }
    };
}
