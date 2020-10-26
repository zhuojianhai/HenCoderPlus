package com.hencoder.a08_animation.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.hencoder.a08_animation.Utils;

public class CircleView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private float radius = Utils.dpToPixel(50);

    ObjectAnimator animator;

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint.setColor(Color.RED);
        animator = ObjectAnimator.ofFloat(this,"radius",0,Utils.dpToPixel(150));
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
