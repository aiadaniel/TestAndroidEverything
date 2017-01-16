package com.vigorous.testandroideverything;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;

import com.vigorous.widget.TestTouchEventView;
import com.vigorous.widget.TestTouchEventViewgroup;

public class TouchEventActivity extends AppCompatActivity {
    private static final String tag = "11111" + TouchEventActivity.class.getSimpleName();

    TestTouchEventViewgroup mViewGroup;//is ViewGroup
    TestTouchEventView mEmtpyView,mEmtpyViewBelow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_event);
        mViewGroup = (TestTouchEventViewgroup) findViewById(R.id.activity_touch_event);
        mEmtpyView = (TestTouchEventView) findViewById(R.id.emtpy_view);
        //mEmtpyViewBelow = (TestTouchEventView)  findViewById(R.id.empty_view_below);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        Log.d(tag, "==dispatchTouchEvent");

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
}
