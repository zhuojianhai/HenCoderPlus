package com.hencoder.a12_scalable.s;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.OverScroller;

import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;

import com.hencoder.a12_scalable.Utils;

public class ScalableImageView extends View {
    private static final float IMAGE_WIDTH = Utils.dpToPixel(300);
    private static final float OVER_SCALE_FACTOR = 1.5f;

    Bitmap bitmap;
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);


    float offsetX;
    float offsetY;
    float originalOffsetX;
    float originalOffsetY;
    float smallScale;
    float bigScale;

    boolean big;//当前是不是大图

    float currentScale;
    ObjectAnimator scaleAnimator;

    GestureDetectorCompat detector;

    ScalableImageView.HenGestureListener gestureListener = new ScalableImageView.HenGestureListener();

    ScalableImageView.HenFlingRunner henFlingRunner = new ScalableImageView.HenFlingRunner();

    ScaleGestureDetector scaleDetector;
    ScalableImageView.HenScaleListener henScaleListener = new ScalableImageView.HenScaleListener();
    OverScroller scroller;


    public ScalableImageView(Context context) {
        this(context,null);
    }

    public ScalableImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ScalableImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        bitmap = Utils.getAvatar(getResources(), (int) IMAGE_WIDTH);
        detector = new GestureDetectorCompat(context, gestureListener);

        //长按事件禁止掉
//        detector.setIsLongpressEnabled(false);

        scroller = new OverScroller(context);
        scaleDetector = new ScaleGestureDetector(context, henScaleListener);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //一开始让图片居中显示，
        offsetX = (getWidth() - bitmap.getWidth())/2f;
        offsetY = (getHeight() - bitmap.getHeight())/2f;

        //宽图,宽度缩放比例就小
        if ((float) bitmap.getWidth()/bitmap.getHeight() >(float)getWidth()/getHeight()){
            smallScale = (float) getWidth() /bitmap.getWidth();
            bigScale = (float)getHeight()/bitmap.getHeight();
        }else{//长图，高度缩放比例就小
            smallScale = (float)getHeight()/bitmap.getHeight();
            bigScale = (float)getWidth()/bitmap.getWidth();
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = scaleDetector.onTouchEvent(event);
        //如果缩放手势没有在处理，就交由 detector去处理
        if (!scaleDetector.isInProgress()){
            result = detector.onTouchEvent(event);
        }

        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //缩放
        canvas.scale(currentScale,currentScale,getWidth()/2f,getHeight()/2f);
    }



    @SuppressLint("ObjectAnimatorBinding")
    private ObjectAnimator getScaleAnimator() {
        if (scaleAnimator == null) {
            scaleAnimator = ObjectAnimator.ofFloat(this, "currentScale", 0);
        }
        scaleAnimator.setFloatValues(smallScale, bigScale);
        return scaleAnimator;
    }


    /**
     * 只有onDown返回值有用，其余都没有用
     *
     * implements   OnGestureListener,
     *              OnDoubleTapListener,
     *              OnContextClickListener
     *
     */
    class HenGestureListener extends GestureDetector.SimpleOnGestureListener {

        //收到action_down 事件，会回调这个方法。决定当前事件序列是不是需要处理，true需要。false是不需要
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {

            //预按下状态结束回调方法
        }

        //手抬起就回调
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent down, MotionEvent event, float distanceX, float distanceY) {
            if (big) {
                offsetX -= distanceX;
                offsetY -= distanceY;
                fixOffsets();
                invalidate();
            }
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        //返回值无用
        @Override
        public boolean onFling(MotionEvent down, MotionEvent event, float velocityX, float velocityY) {
            if (big) {
                scroller.fling((int) offsetX, (int) offsetY, (int) velocityX, (int) velocityY,
                        - (int) (bitmap.getWidth() * bigScale - getWidth()) / 2,
                        (int) (bitmap.getWidth() * bigScale - getWidth()) / 2,
                        - (int) (bitmap.getHeight() * bigScale - getHeight()) / 2,
                        (int) (bitmap.getHeight() * bigScale - getHeight()) / 2);

                postOnAnimation(henFlingRunner);

            }
            return false;
        }

        //单击确认回调  （单击-->等待一定时间-->抬起）
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return false;
        }

        //300s 毫秒内之内才会触发
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            big = !big;
            if (big) {
                offsetX = (e.getX() - getWidth() / 2f) - (e.getX() - getWidth() / 2) * bigScale / smallScale;
                offsetY = (e.getY() - getHeight() / 2f) - (e.getY() - getHeight() / 2) * bigScale / smallScale;
                fixOffsets();
                getScaleAnimator().start();
            } else {
                getScaleAnimator().reverse();
            }
            return false;
        }

        //300s 毫秒内之内才会触发
        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            return false;
        }
    }

    private void fixOffsets() {
        offsetX = Math.min(offsetX, (bitmap.getWidth() * bigScale - getWidth()) / 2);
        offsetX = Math.max(offsetX, - (bitmap.getWidth() * bigScale - getWidth()) / 2);
        offsetY = Math.min(offsetY, (bitmap.getHeight() * bigScale - getHeight()) / 2);
        offsetY = Math.max(offsetY, - (bitmap.getHeight() * bigScale - getHeight()) / 2);
    }

    class HenFlingRunner implements Runnable {

        @Override
        public void run() {
            if (scroller.computeScrollOffset()) {
                offsetX = scroller.getCurrX();
                offsetY = scroller.getCurrY();
                invalidate();
                postOnAnimation(this);
            }
        }
    }

    class HenScaleListener implements ScaleGestureDetector.OnScaleGestureListener {
        float initialScale;

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            // detector.getScaleFactor() 与上次事件相比，得到的比例因子
            currentScale = initialScale * detector.getScaleFactor();
            System.out.println("HenScaleListener--onScale--currentScale"+currentScale);
            invalidate();
            return false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            initialScale = currentScale;
            System.out.println("HenScaleListener--onScaleBegin--initialScale"+initialScale);
            //一定要返回true才会进入onScale()这个函数
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {

        }
    }

}
