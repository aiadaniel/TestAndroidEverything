package com.vigorous.cache;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.util.LruCache;

import com.vigorous.FileDownload.DownloadMgr;

public class ImageCache implements Cache<Bitmap> {
    private final static int maxMemory = (int) Runtime.getRuntime().maxMemory();// 获取当前应用程序所分配的最大内存
    private final static int cacheSize = (int) (maxMemory / 8);
    private static LruCache<String,Bitmap> mLru = new LruCache<String, Bitmap>(cacheSize){
        @Override
        protected int sizeOf(String key, Bitmap value) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
                return value.getByteCount()/1024;
            }
            return value.getRowBytes()*value.getHeight()/1024;
        }
    };
    Context mContext;

    public ImageCache(Context context) {
        mContext = context;
    }

    @Override
    public void put(String key, Bitmap value) {
        mLru.put(key,value);
    }

    @Override
    public Bitmap get(String key) {
        if (mLru.get(key) == null) {
            //DownloadMgr.getInstance(mContext).addDownloadTask(key);//TODO
        }
        return mLru.get(key);
    }
}
