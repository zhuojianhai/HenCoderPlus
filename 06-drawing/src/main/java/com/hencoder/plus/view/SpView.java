package com.hencoder.plus.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

import androidx.annotation.Nullable;

import com.hencoder.plus.Utils;

public class SpView extends View {
    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final float RING_WIDTH = Utils.dp2px(20);
    private static final float RADIUS = Utils.dp2px(150);
    private static final int CIRCLE_COLOR = Color.parseColor("#90A4AE");
    private static final int HIGHLIGHT_COLOR = Color.parseColor("#FF4081");

    private String str = "我是绘Df制内容";

    Rect rect = new Rect();
    //计算字体
    Paint.FontMetrics fontMetrics = new Paint.FontMetrics();

    public SpView(Context context) {
        super(context);
    }

    public SpView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        mPaint.setTextSize(Utils.dp2px(100));
        mPaint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "Quicksand-Regular.ttf"));
        mPaint.getFontMetrics(fontMetrics);
        mPaint.setTextAlign(Paint.Align.CENTER);

    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }


    /**
     * 绘制圆环
     * 绘制进度条
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(CIRCLE_COLOR);
        mPaint.setStrokeWidth(RING_WIDTH);

        canvas.drawCircle(getWidth()/2,getHeight()/2,RADIUS,mPaint);

        //绘制进度条
        mPaint.setColor(HIGHLIGHT_COLOR);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        canvas.drawArc(getWidth()/2 - RADIUS,
                getHeight()/2-RADIUS,
                getWidth()/2+RADIUS,
                getHeight()/2+RADIUS,
                135,
                250,
                false,
                mPaint);
        //绘制文字
        mPaint.setTextSize(Utils.dp2px(30));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextAlign(Paint.Align.CENTER);
        float offset = (fontMetrics.ascent + fontMetrics.descent)/2;

        canvas.drawText(str,getWidth()/2,getHeight()/2 - offset,mPaint);


        canvas.save();
        mPaint.setColor(Color.GREEN);
        canvas.drawRect(0,0,Utils.dp2px(100),Utils.dp2px(100),mPaint);
        canvas.restore();
    }
}
