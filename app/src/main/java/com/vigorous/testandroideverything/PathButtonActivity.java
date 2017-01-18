package com.vigorous.testandroideverything;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

public class PathButtonActivity extends BaseActivity {

    private static final String tag = "11111";

    private int mProgress = 0;

    static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d(tag,"recv msg");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_button);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //testHandler();
    }

    public void testHandler() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (mProgress++ < 100) {
                    try {
                        Thread.sleep(600);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message msg = mHandler.obtainMessage();
                    msg.what = 0x01;
                    msg.arg1 = mProgress;
                    //msg.sendToTarget();//其实最终都是sendMessageDelayed
                    mHandler.sendMessage(msg);
                }
            }
        }.start();
    }

}
