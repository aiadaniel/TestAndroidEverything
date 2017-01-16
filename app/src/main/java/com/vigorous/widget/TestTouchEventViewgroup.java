package com.vigorous.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by admin.
 */

public class TestTouchEventViewgroup extends RelativeLayout {
    private static final String tag = "11111"+TestTouchEventViewgroup.class.getSimpleName();
    public TestTouchEventViewgroup(Context context) {
        super(context);
    }

    public TestTouchEventViewgroup(Context context, AttributeSet attr) {
        super(context,attr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(tag,"dispatchTouchEvent");
        //return false;//这种情况表示事件没有派发成功，子View的touch事件都没有了
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(tag,"onTouchEvent");
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.d(tag,"action down");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(tag,"action move");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(tag,"action up");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d(tag,"action cancel");
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(tag,"onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
    }
}
