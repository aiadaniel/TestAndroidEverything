package com.vigorous.testandroideverything;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;


public class MyApplication extends Application {
    private static final String TAG = "11111";

    private static Context sContext;
    public static Context getAppContext() {
        return sContext;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        Fresco.initialize(this);
        Log.d(TAG,"app onCreate");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

    }
}
