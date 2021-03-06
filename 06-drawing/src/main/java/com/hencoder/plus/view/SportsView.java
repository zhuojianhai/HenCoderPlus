package com.hencoder.plus.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.hencoder.plus.Utils;

public class SportsView extends View {
    private static final float RING_WIDTH = Utils.dp2px(20);
    private static final float RADIUS = Utils.dp2px(150);
    private static final int CIRCLE_COLOR = Color.parseColor("#90A4AE");
    private static final int HIGHLIGHT_COLOR = Color.parseColor("#FF4081");

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Rect rect = new Rect();
    Paint.FontMetrics fontMetrics = new Paint.FontMetrics();


    private int progress = 0;

    //自定义属性动画
    ObjectAnimator animator;

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    public SportsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    //游离代码块要先于当前类的构造方法执行
    {
        paint.setTextSize(Utils.dp2px(100));
        paint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "Quicksand-Regular.ttf"));
        paint.getFontMetrics(fontMetrics);
        paint.setTextAlign(Paint.Align.CENTER);

        animator = ObjectAnimator.ofInt(this,"progress",0,65);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制环
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(CIRCLE_COLOR);
        paint.setStrokeWidth(RING_WIDTH);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, RADIUS, paint);

        // 绘制进度条
        paint.setColor(HIGHLIGHT_COLOR);
        //圆角
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawArc(getWidth() / 2 - RADIUS, getHeight() / 2 - RADIUS, getWidth() / 2 + RADIUS, getHeight() / 2 + RADIUS, 135, progress * 2.7f, false, paint);

        // 绘制文字
        paint.setTextSize(Utils.dp2px(100));
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);

//        paint.getTextBounds("abab", 0, "abab".length(), rect);

        //精确测量文字的偏移量，使其能够真正的居中
        float offset = (fontMetrics.ascent + fontMetrics.descent) / 2;
        canvas.drawText("abab", getWidth() / 2, getHeight() / 2 - offset, paint);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        animator.setDuration(5000);
        animator.setStartDelay(1000);
        animator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (animator!=null){
            animator.cancel();
            animator = null;
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                int currentProgress = getProgress();
                currentProgress+=5;
                setProgress(currentProgress);
                break;
        }
        return true;
    }
}
