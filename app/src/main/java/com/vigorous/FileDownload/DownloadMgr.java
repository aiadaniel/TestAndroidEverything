package com.vigorous.FileDownload;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.vigorous.utils.OkUtils;
import com.vigorous.utils.StorageUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by admin.
 */

public class DownloadMgr {
    private static final String tag = "11111";

    Context mContext;
    //Executor的接口实现
    ExecutorService mExecutor;
    //Executors

    private static DownloadMgr instance = null;
    public static DownloadMgr getInstance(Context context) {
        if (instance == null) {
            synchronized (DownloadMgr.class) {
                if (instance == null) {
                    instance = new DownloadMgr(context);
                }
            }
        }
        return instance;
    }
    private DownloadMgr(Context context) {
        mContext = context;
        mExecutor = Executors.newFixedThreadPool(5);
    }

    public void addDownloadTask(final String url, final SimpleCallback callback) {//TODO: 缓存,否则容易oom
        Future future = mExecutor.submit(new Runnable() {
            @Override
            public void run() {
                OkUtils.okAsyncGet(url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e(tag,"down res failed");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d(tag,"down res " + response.code());
                        InputStream is = null;
                        byte[] buf = new byte[2048];
                        int len = 0;
                        FileOutputStream fos = null;
                        String filename = url.substring(url.lastIndexOf('/'));
                        File downfile = new File(StorageUtil.getRootPath(mContext),filename);
                        is = response.body().byteStream();

                        long total = response.body().contentLength();
                        fos = new FileOutputStream(downfile);
                        long sum = 0;
                        while ((len = is.read(buf)) != -1) {
                            fos.write(buf,0,len);
                            sum += len;
                            float progress = sum * 1.0f / total;
                            //Log.d(tag, "progress=" + progress);
                            if (callback != null)
                                callback.onProgress(progress);//非UI线程更新ui
                        }
                        if (downfile.exists()) {
                            callback.onSuccess(BitmapFactory.decodeFile(downfile.getAbsolutePath()));//非UI线程更新ui
                        }
                    }
                });
            }
        });
        try {
            //Log.d(tag,"before get");
            future.get();//该方法是阻塞的
            //Log.d(tag,"after get");
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.e(tag,e.toString());
        } catch (ExecutionException e) {
            e.printStackTrace();
            Log.e(tag,e.toString());
        }
    }

    public interface SimpleCallback {
        void onProgress(float progress);
        void onSuccess(Bitmap file);
    }
}
