package com.hencoder.a07_change_camera_animation.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import androidx.annotation.Nullable;

import com.hencoder.a07_change_camera_animation.R;

public class BitView  extends View {
    Bitmap bitmap;
    Matrix matrix;
    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    VelocityTracker velocityTracker;

    public BitView(Context context) {
        this(context,null);
    }

    public BitView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        matrix = new Matrix();
        init(context);
    }

    private void init (Context context){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_rengwuxian,options);
        options.inSampleSize = 8;
        options.inJustDecodeBounds = false;
        bitmap =  BitmapFactory.decodeResource(context.getResources(),R.drawable.avatar_rengwuxian,options);

        velocityTracker = VelocityTracker.obtain();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
         velocityTracker.addMovement(event);
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        matrix.setRotate(90);
////        bitmap = Utils.getAvatar(getContext().getResources(),300);
//        matrix.reset();
//        canvas.drawBitmap(bitmap,matrix,mPaint);
//        matrix.postScale(2,2);
//        matrix.setRotate(90);
//        canvas.drawBitmap(bitmap,matrix,mPaint);






        canvas.drawRect(100,100,50,50,mPaint);




        canvas.save();
        mPaint.setStyle(Paint.Style.FILL);
        canvas.translate(100,100);
        canvas.drawRect(100,100,50,50,mPaint);
        canvas.translate(-100,-100);
        canvas.restore();


        canvas.save();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GREEN);
        canvas.translate(-200,-200);
        canvas.drawRect(0,0,500,500,mPaint);
        canvas.translate(200,200);
        canvas.restore();

    }
}