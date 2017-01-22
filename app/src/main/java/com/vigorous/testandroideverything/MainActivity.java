package com.vigorous.testandroideverything;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.vigorous.utils.StorageUtil;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    //TextView mTv1;
    Button mBtnCustomTab,mBtnActionbar,mBtnLoadBitmap,mBtnDb,mBtnTouchEvent,mBtnScroll,mPathBtn,mSignBtn
            ,mBtnBottomTab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StorageUtil.justLogDir(this);
        //mTv1 = (TextView) findViewById(R.id.tv1);
        mBtnCustomTab = (Button) findViewById(R.id.btn_customtab);
        mBtnCustomTab.setOnClickListener(this);
        mBtnActionbar = (Button) findViewById(R.id.btn_actionbar);
        mBtnActionbar.setOnClickListener(this);
        mBtnLoadBitmap = (Button) findViewById(R.id.btn_loadbitmap);
        mBtnLoadBitmap.setOnClickListener(this);
        mBtnDb = (Button) findViewById(R.id.btn_db);
        mBtnDb.setOnClickListener(this);
        mBtnTouchEvent =(Button) findViewById(R.id.btn_touchevent);
        mBtnTouchEvent.setOnClickListener(this);
        mBtnScroll = (Button) findViewById(R.id.btn_scroll);
        mBtnScroll.setOnClickListener(this);
        mPathBtn = (Button) findViewById(R.id.btn_pathbtn);
        mPathBtn.setOnClickListener(this);
        mSignBtn = (Button) findViewById(R.id.btn_linepath);
        mSignBtn.setOnClickListener(this);
        mBtnBottomTab = (Button) findViewById(R.id.btn_bottom_tab);
        mBtnBottomTab.setOnClickListener(this);
        if (BuildConfig.DEBUG)
            Log.d("11111","IS DEBUG MODE");

    }

    @Override
    protected void onResume() {
        super.onResume();
        //子线程可以更新ui？？？ 第一次运行不会崩溃，旋转屏幕啥的就会
//        Thread thread = new Thread() {
//            @Override
//            public void run() {
//                super.run();
//                mTv1.setText("2222");
//            }
//        };
//        thread.start();
//        new Thread() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(10000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();//实测不会阻塞ui
    }

    //这个方法是activity的
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_customtab:
                Intent i = new Intent(this,CustomTabActivity.class);
                startActivity(i);
                break;
            case R.id.btn_actionbar:
                Intent ii = new Intent(this,AnimationActivity.class);
                startActivity(ii);
                break;
            case R.id.btn_loadbitmap:
                Intent iii = new Intent(this,LoadBitmapActivity.class);
                startActivity(iii);
                break;
            case R.id.btn_db:
                Intent iv = new Intent(this,DBActivity.class);
                startActivity(iv);
                break;
            case R.id.btn_touchevent:
                Intent vv = new Intent(this,TouchEventActivity.class);
                startActivity(vv);
                break;
            case R.id.btn_scroll:
                Intent vi = new Intent(this,ScrollActivity.class);
                startActivity(vi);
                break;
            case R.id.btn_pathbtn:
                Intent vii = new Intent(this,PathButtonActivity.class);
                startActivity(vii);
                break;
            case R.id.btn_linepath:
                Intent viii = new Intent(this,SignActivity.class);
                startActivity(viii);
                break;
            case R.id.btn_bottom_tab:
                Intent starter = new Intent(this, BottomTabActivity.class);
                startActivity(starter);
                break;
            default:break;
        }
    }
}
