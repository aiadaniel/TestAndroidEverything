package com.vigorous.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by admin.
 */

public class TestTouchEventView extends View {
    private static final String tag = "11111"+TestTouchEventView.class.getSimpleName();

    public TestTouchEventView(Context context) {
        super(context);
    }

    public TestTouchEventView(Context context, AttributeSet attr) {
        super(context,attr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d(tag,"dispatchTouchEvent");
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(tag,"onTouchEvent");
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.d(tag,"action down");
                return true;//消费了down事件
                //break;
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
        //return true;//消费了所有事件，则顶上的viewgroup就不会有对应事件回调
        return super.onTouchEvent(event);
    }
}
