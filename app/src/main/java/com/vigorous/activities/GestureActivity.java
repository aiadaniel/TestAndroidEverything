package com.vigorous.activities;

import android.gesture.Gesture;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.vigorous.testandroideverything.R;

/**
 * Created by lxm.
 */

public class GestureActivity extends BaseActivity implements GestureDetector.OnGestureListener,View.OnTouchListener{

    private static final String TAG = "11111";

    private static final int FLING_MIN_DISTANCE = 100;//PX
    private static final int FLING_MIN_VELOCITY = 10;

    Gesture mGesture;
    GestureDetector mGestureDetector;
    LinearLayout mLinearLayoutRoot;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture);
        mLinearLayoutRoot = (LinearLayout) findViewById(R.id.ll_root);
        mLinearLayoutRoot.setOnTouchListener(this);
        mLinearLayoutRoot.setLongClickable(true);//缺少这句根本捕捉不到onfling
        mGestureDetector = new GestureDetector(this,this);
    }

    //============================================gesture listener
    @Override
    public boolean onDown(MotionEvent e) {
        Log.d(TAG,"ondown");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.d(TAG,"onShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.d(TAG,"onSingleTapUp");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.d(TAG,"onScroll");
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.d(TAG,"onLongPress");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d(TAG,"onFling velocity x is " + velocityX);
        if (e1.getX()-e2.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY ) {
            Log.d(TAG,"fling left");
        } else if (e2.getX()-e1.getX() > FLING_MIN_VELOCITY && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
            Log.d(TAG,"fling right");
        }
        return false;
    }

    //============================================touch listener
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //Log.d(TAG,"ontouch");
        return mGestureDetector.onTouchEvent(event);
    }
}
