package com.vigorous.widget;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class TwoScrollviewLayout extends LinearLayout {

    private static final String tag = "11111";

    private boolean mIntercept;
    private UpDownScrollview mUpScrollView, mBottomScrollView;
    private int mUpSVMarginTop = 0;
    private int mInitMarginTop = 0;
    private float mInitY;
    private int mTouchSlop;
    private String mCurrentTag = "";
    private int halfHeight;

    public TwoScrollviewLayout(Context context) {
        super(context);
    }

    public TwoScrollviewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public TwoScrollviewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TwoScrollviewLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        setOrientation(LinearLayout.VERTICAL);
        mUpScrollView = inflateScrollView(context, UpDownScrollview.TAG_ONE, 1000);
        mBottomScrollView = inflateScrollView(context, UpDownScrollview.TAG_TWO, 2000);
        addView(mUpScrollView);
        addView(mBottomScrollView);
    }

    private UpDownScrollview inflateScrollView(Context context, String tag, int id) {
        UpDownScrollview upDownScrollview = new UpDownScrollview(context);
        upDownScrollview.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        upDownScrollview.setTag(tag);
        upDownScrollview.setScrollListener(new UpDownScrollview.OnScrollListerner() {
            @Override
            public void onScrollButtom(boolean intercept, String tag) {
                mIntercept = intercept;
                mCurrentTag = tag;
            }
        });
        upDownScrollview.addView(inflateLl(context, id));
        return upDownScrollview;
    }

    private LinearLayout inflateLl(Context context, int id) {
        LinearLayout ll = new LinearLayout(context);
        ll.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setId(id);
        return ll;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mIntercept)
            return true;
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight() * 2);
        Log.d(tag,"onMesaure");
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        halfHeight = getMeasuredHeight() / 2;
        mUpScrollView.layout(0, mUpSVMarginTop, getMeasuredWidth(), mUpSVMarginTop + halfHeight);
        mBottomScrollView.layout(0, mUpSVMarginTop + halfHeight, getMeasuredWidth(), mUpSVMarginTop + getMeasuredHeight());
        Log.d(tag,"onLayout");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (mInitY == 0) {
                    mInitY = event.getY();
                } else {
                    int offset = (int) Math.abs(event.getY() - mInitY);
                    if (offset > mTouchSlop) {
                        int delayOffset = offset * 7 / 10;
                        if (mCurrentTag.equals(UpDownScrollview.TAG_ONE)) {
                            mUpSVMarginTop = mInitMarginTop - delayOffset;
                        } else {
                            mUpSVMarginTop = -halfHeight + delayOffset;
                        }
                        if (mUpSVMarginTop > 0) mUpSVMarginTop = 0;
                        requestLayout();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                mIntercept = false;
                mInitY = 0;
                if (mCurrentTag.equals(UpDownScrollview.TAG_ONE)) {
                    if (Math.abs(mUpSVMarginTop) > halfHeight / 3) {
                        startAnimation(mUpSVMarginTop, halfHeight - Math.abs(mUpSVMarginTop), false);
                    } else {
                        startAnimation(mUpSVMarginTop, Math.abs(mUpSVMarginTop), true);
                    }
                } else {
                    if (Math.abs(mUpSVMarginTop) < halfHeight * 2 / 3) {
                        startAnimation(mUpSVMarginTop, Math.abs(mUpSVMarginTop), true);
                    } else {
                        startAnimation(mUpSVMarginTop, halfHeight - Math.abs(mUpSVMarginTop), false);
                    }
                }
                requestLayout();
                break;
        }
        return true;
    }

    public void addFragment(Fragment[] fragmentArray, FragmentManager fragmentManager) {
        if (fragmentArray == null || fragmentArray.length < 2 || fragmentManager == null || mBottomScrollView == null || mUpScrollView == null)
            return;
        mUpScrollView.addFragment(fragmentArray[0], fragmentManager);
        mBottomScrollView.addFragment(fragmentArray[1], fragmentManager);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void startAnimation(final int start, final int moveOffset, final boolean isPlus) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int progressOffset = (int) (moveOffset * ((float) animation.getAnimatedValue()));
                progressOffset = isPlus ? -progressOffset : progressOffset;
                mUpSVMarginTop = start - progressOffset;
                requestLayout();
            }
        });
        valueAnimator.setDuration(moveOffset / mTouchSlop * 10);
        valueAnimator.start();
    }
}
