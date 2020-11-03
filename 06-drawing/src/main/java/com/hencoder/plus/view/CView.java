package com.hencoder.plus.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.widget.OverScroller;

import androidx.annotation.Nullable;

import com.hencoder.plus.Utils;

public class CView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Shader shader;

    OverScroller scroller;

    public CView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    {
        paint.setColor(Color.RED);
        shader = new LinearGradient( Utils.dp2px(100), Utils.dp2px(100), Utils.dp2px(500), Utils.dp2px(500), Color.parseColor("#E91E63"),
                Color.parseColor("#2196F3"), Shader.TileMode.CLAMP);
        paint.setShader(shader);

        scroller = new OverScroller(getContext());
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(),scroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        canvas.drawColor(Color.GRAY);

        //设置画笔颜色
        paint.setColor(Color.parseColor("#f00000"));
        //设置画笔为空心  如果将这里改为Style.STROKE 将这个图中的实线圆柱体就变成空心的
        paint.setStyle(Paint.Style.FILL);
        canvas.save();
        //画圆
        canvas.drawCircle(width / 2, height / 2, Utils.dp2px(150), paint);

        paint.setColor(Color.parseColor("#ffffff"));
        for (int i = 0; i < 12; i++) {
            //中心坐标
            //x = width / 2 ;//我这里是以屏幕中心为坐标点 所以宽高直接除以2
            // y = height / 2;//我这里是以屏幕中心为坐标点 所以宽高直接除以2
            //30 代表角度，200代表半径 这些都是可以自定义的
            //公式 java中有快捷求cos sin得方法。就是这样了
            //角度转弧度 函数Math.toRadians，并不是精确的

            int x = (int) Math.round(Math.cos(Math.toRadians(30 * i)) * Utils.dp2px(150));

            int y = (int) Math.round(Math.sin(Math.toRadians(30 * i)) * Utils.dp2px(150));

            canvas.drawLine(width / 2, height / 2, width / 2 + x, height / 2 - y, paint);

            canvas.drawCircle(width/2+x,height/2-y,Utils.dp2px(10),paint);
        }


    }

    /**
     * 缓慢滑动到指定位置
     * @param dex
     * @param dey
     */
    private void smoothScroll(int dex,int dey){
        int scrollX = getScrollX();
        int delta =  dex - scrollX;
        scroller.startScroll(scrollX,0,delta,0,1000);
        invalidate();
    }
}
