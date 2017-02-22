package com.vigorous.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by lxm.
 */

public class PinPuView extends View {
    private static final String tag = "11111";
    private Paint mPaint;
    private RectF[] mRectFs;// = new RectF[3];

    private float mWidth;
    private int mNums;
    private int mColor;

    public PinPuView(Context context) {
        super(context);
        init(context);
    }

    public PinPuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PinPuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PinPuView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mNums = 4;//default
        mRectFs = new RectF[mNums];
//        mWidth = 20;//calc in onDraw
        mColor = Color.BLACK;//default
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        //mPaint.setStrokeWidth(10);
        //mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        mPaint.setColor(mColor);
        mPaint.setStyle(Paint.Style.FILL);
//        for (int i = 0;i < mNums;i++) {//calc in onDraw
//            float left = mWidth*(2*i+1);
//            mRectFs[i] = new RectF(left,0,left+mWidth,getRand());
//        }
    }

    private float getRand() {
        double temp = Math.random()*(getHeight()-getPaddingTop()-getPaddingBottom());
        return (float)temp;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(Math.min(w,h),Math.min(w,h));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float paddingLeft = getPaddingLeft();//记住自定义的view是怎样计算padding的
        float paddingRight = getPaddingRight();
        mWidth = (getWidth()-paddingLeft-paddingRight)/(2*mNums+1);
        for (int i = 0;i < mNums;i++) {//calc in onDraw
            float left = mWidth*(2*i+1);
            //mRectFs[i].set(left,getRand()+getPaddingTop(),left+mWidth,getHeight()-getPaddingBottom());
            canvas.drawRect(left,getRand()+getPaddingTop(),left+mWidth,getHeight()-getPaddingBottom(),mPaint);
        }

        //canvas.drawRect(0,10,10,0,mPaint);
        //canvas.drawLine(0,10,100,200,mPaint);//从这个试验发现，坐标分布如左上角（0,0） 横向x 纵向y
    }

    private Runnable mRunnable =  new Runnable() {
        @Override
        public void run() {
            invalidate();//no ui thread,gaga
            mHandler.postDelayed(this,500);
        }
    };

    private Handler mHandler = new Handler();

    private void startPlay() {
        mHandler.postDelayed(mRunnable,500);
    }

    private void stopPlay() {
        mHandler.removeCallbacks(mRunnable);
    }

    //we need to ensure remove callback
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d(tag,"onAttachedToWindow");
        startPlay();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d(tag,"onDetachedFromWindow");
        stopPlay();
    }
}
