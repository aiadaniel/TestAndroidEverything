package com.vigorous.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Process;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.vigorous.FileDownload.DownloadMgr;
import com.vigorous.testandroideverything.R;

/**
 * Created by admin.
 */

public class LoadingImageView extends ImageView implements DownloadMgr.SimpleCallback{
    private static final int SWIPE_VETICAL = 1;
    private static final int SWIPE_HORIZONTAL = 2;

    private Paint mLayerPaint;
    private Paint mTextPaint;
    private Rect mRect;
    private float mProgress = 0.0f;
    private int mSwipeMode = SWIPE_VETICAL;
    private float mWidth,mHeight;

    public LoadingImageView(Context context) {
        super(context);
    }

    public LoadingImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        getCustomAttrs(context,attrs);
    }

    public LoadingImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        getCustomAttrs(context,attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LoadingImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void getCustomAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.loadingOrient);
        mSwipeMode = array.getInt(R.styleable.loadingOrient_loadingOriention,1);
        array.recycle();
    }

    private void init(Context context) {
        mLayerPaint = new Paint();
        mLayerPaint.setColor(Color.LTGRAY);
        mLayerPaint.setAlpha(100);
        mTextPaint = new Paint();
        mTextPaint.setColor(Color.DKGRAY);
        mTextPaint.setTextSize(25);
        mRect = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (Math.abs(1.0-mProgress) < 0.000001)
            return;
        String perStr = (int) (mProgress*100) + "%";
        mTextPaint.getTextBounds(perStr,0,perStr.length(),mRect);
        mWidth = getWidth();
        mHeight = getHeight()*(1- mProgress);
        if (mSwipeMode == SWIPE_VETICAL) {
            canvas.drawRect(0, mHeight , mWidth, getHeight(), mLayerPaint);
        } else {
            canvas.drawRect(0,0,mWidth*mProgress,getHeight(),mLayerPaint);
        }
        canvas.drawText(perStr,(mWidth-mRect.width())/2,(getHeight()+mRect.height())/2,mTextPaint);
    }

    public void setProgress(float cur) {
        mProgress = cur;
        postInvalidate();
    }

    @Override
    public void onProgress(float progress) {
        setProgress(progress);
    }

    @Override
    public void onSuccess(final Bitmap file) {
        post(new Runnable() {
            @Override
            public void run() {
                //invalidate();
                setImageBitmap(file);
            }
        });
    }
}
