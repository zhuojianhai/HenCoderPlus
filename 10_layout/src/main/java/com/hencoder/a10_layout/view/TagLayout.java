package com.hencoder.a10_layout.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.icu.text.CollationKey;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.hencoder.a10_layout.Utils;

import java.util.ArrayList;
import java.util.List;

/***
 * 自定义标签Layout
 */
public class TagLayout extends ViewGroup {
    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    List<Rect> childrenBounds = new ArrayList<>();

    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthUsed = 0;
        int heightUsed = 0;
        int lineWidthUsed = 0;
        int lineMaxHeight = 0;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specWidth = MeasureSpec.getSize(widthMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed);
            if (specMode != MeasureSpec.UNSPECIFIED &&
                    lineWidthUsed + child.getMeasuredWidth() > specWidth) {
                lineWidthUsed = 0;
                heightUsed += lineMaxHeight;
                lineMaxHeight = 0;
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed);
            }
            Rect childBound;
            if (childrenBounds.size() <= i) {
                childBound = new Rect();
                childrenBounds.add(childBound);
            } else {
                childBound = childrenBounds.get(i);
            }
            childBound.set(lineWidthUsed, heightUsed, lineWidthUsed + child.getMeasuredWidth(), heightUsed + child.getMeasuredHeight());
            lineWidthUsed += child.getMeasuredWidth();
            widthUsed = Math.max(widthUsed, lineWidthUsed);
            lineMaxHeight = Math.max(lineMaxHeight, child.getMeasuredHeight());
        }

        int width = widthUsed;
        int height = heightUsed + lineMaxHeight;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            Rect childBounds = childrenBounds.get(i);
            child.layout(childBounds.left, childBounds.top, childBounds.right, childBounds.bottom);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        mPaint.setStrokeWidth(Utils.dpToPixel(16));
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(getWidth()/2f,getHeight()/2f, Utils.dpToPixel(50),mPaint);
        canvas.drawCircle(getWidth()/4f,getHeight()/4f, Utils.dpToPixel(50),mPaint);
        canvas.drawCircle(getWidth()/8f,getHeight()/8f, Utils.dpToPixel(50),mPaint);
        canvas.drawCircle(getWidth()/16f,getHeight()/8f, Utils.dpToPixel(50),mPaint);
    }
}
