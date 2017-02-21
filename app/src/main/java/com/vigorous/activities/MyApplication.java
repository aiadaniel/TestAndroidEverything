package com.vigorous.activities;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.mapbox.mapboxsdk.MapboxAccountManager;


public class MyApplication extends Application {
    private static final String TAG = "11111";
    private static final String token = "pk.eyJ1Ijoibmlja3M5OTk5IiwiYSI6ImNpemY3aXg5bTAwYTYyd255amlrM3llZXgifQ.t5UuGuB9v6WoatZYuYXNGQ";

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
        MapboxAccountManager.start(this,token);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

    }
}
