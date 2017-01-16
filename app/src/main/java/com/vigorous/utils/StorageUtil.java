package com.vigorous.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;

public class StorageUtil {

    public static boolean isExternalStorageExists() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public static File getRootDir(Context context) {
        File file;
        if (isExternalStorageExists()) {
            file = context.getExternalCacheDir();
        } else {
            file = context.getCacheDir();
        }
        if (file != null && !file.exists()) {
            file.mkdirs();//no mkdir
        }
        return file;
    }

    public static String getRootPath(Context context) {
        if (isExternalStorageExists()) {
            return context.getExternalCacheDir().getAbsolutePath();
        }
        return context.getCacheDir().getAbsolutePath();
    }

    public static void justLogDir(Context context) {
        String tag = "11111";
        Log.d(tag,context.getExternalCacheDir().getAbsolutePath());
        Log.d(tag,context.getCacheDir().getAbsolutePath());
    }
}
