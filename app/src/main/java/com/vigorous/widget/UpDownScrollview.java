package com.vigorous.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class UpDownScrollview extends ScrollView {

    public static final String TAG_ONE = "ONE";
    public static final String TAG_TWO = "TWO";

    public interface OnScrollListerner {
        public void onScrollButtom(boolean intecept, String tag);
    }

    private LinearLayout mLl;
    private int mLlHeight;
    private OnScrollListerner mScrollListener;

    public UpDownScrollview(Context context) {
        super(context);
    }

    public UpDownScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UpDownScrollview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public UpDownScrollview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setScrollListener(OnScrollListerner listener) {
        mScrollListener = listener;
    }

    public void addFragment(Fragment fragment, FragmentManager mgr) {
        if (mLl == null)
            mLl = (LinearLayout) getChildAt(0);
        FragmentTransaction transaction = mgr.beginTransaction();
        transaction.replace(mLl.getId(), fragment);
        transaction.commit();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mLlHeight = mLl.getMeasuredHeight();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mLl = (LinearLayout) getChildAt(0);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);

        if (getTag().equals(TAG_ONE)) {
            if (isScrollButtom()) {
                scrollEdgePoint(false, true, TAG_ONE);
            }
        }
        if (getTag().equals(TAG_TWO)) {
            if (getScrollY() < 0) {
                scrollEdgePoint(false, true, TAG_TWO);
            }
        }
    }

    private void scrollEdgePoint(boolean allow, boolean intecept, String tag) {
        getParent().requestDisallowInterceptTouchEvent(allow);
        if (mScrollListener != null)
            mScrollListener.onScrollButtom(intecept, tag);
    }

    private boolean isScrollButtom() {
        return getScrollY() >= (mLlHeight - getMeasuredHeight());
    }
}
