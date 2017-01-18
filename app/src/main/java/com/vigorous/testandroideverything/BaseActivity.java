package com.vigorous.testandroideverything;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.vigorous.widget.ViewServer;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BuildConfig.DEBUG)
            ViewServer.get(this).addWindow(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (BuildConfig.DEBUG)
            ViewServer.get(this).removeWindow(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (BuildConfig.DEBUG)
            ViewServer.get(this).setFocusedWindow(this);
    }
}
