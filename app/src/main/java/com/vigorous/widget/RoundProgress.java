package com.vigorous.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by lxm .
 * 渐变色环形进度
 */

public class RoundProgress extends View implements Runnable{
    Paint paint;
    int color;
    RectF rectF;
    float startAngle;
    float sweepAngle;

    float centerX;
    float centerY;
    float radius;

    public RoundProgress(Context context) {
        super(context);
        init(context);
    }

    public RoundProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RoundProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RoundProgress(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        paint = new Paint();
        color = Color.BLACK;
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);//这里影响空心实心
        Log.d("11111","W " + getWidth() + " H " + getHeight());//这个情况下拿到的宽高是0，要注意
        rectF = new RectF(5,5,100,100);// RectF(0,0,getWidth(),getHeight());
        startAngle = .0f;
        sweepAngle = 10.f;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d("11111","W " + getWidth() + " H " + getHeight());
        rectF.right = getWidth();
        rectF.bottom = getHeight();
        centerX = getWidth()/2;
        centerY = getHeight()/2;
        radius = getWidth()/2-10/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (sweepAngle >= 360)
            return;
        sweepAngle += 10;
        canvas.drawArc(rectF,startAngle,sweepAngle,false,paint);//true会多画两条半径
        //canvas.drawCircle(centerX,centerY,radius,paint);
    }

    public void setColor(int color) {
        this.color = color;
        invalidate();
    }

    public void startProgress() {
        post(this);
    }

    public void stopProgress() {
        removeCallbacks(this);
    }

    @Override
    public void run() {
        invalidate();
        postDelayed(this,100);
    }
}
