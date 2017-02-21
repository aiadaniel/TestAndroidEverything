package com.mvp.model;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by lxm.
 */

public class NewsManager {
    private static final String tag = NewsManager.class.getSimpleName();
    private static final String NET_EASE_HOST = "http://c.m.163.com/";

    private NewsService mNewsService;
    private static volatile OkHttpClient sClient;//why volatile

    private static NewsManager instance = null;
    private NewsManager() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(NET_EASE_HOST)
                .client(getOkClient())
                //.addConverterFactory(GsonConverterFactory.create())
                //.addCallAdapterFactory(DefaultCallbackFactory.create())
                .build();
        mNewsService = retrofit.create(NewsService.class);
    }
    public static NewsManager getInst() {
        if (instance == null) {
            synchronized(NewsManager.class) {
                instance = new NewsManager();
            }
        }
        return  instance;
    }

    private OkHttpClient getOkClient() {
        if (sClient == null) {
            synchronized (NewsManager.class) {
                if (sClient == null) {
                    sClient = new OkHttpClient.Builder().build();
                }
            }
        }
        return sClient;
    }

    public Call<ResponseBody> getNewsList(String newsType, String newsId, int startPage) {
        Call<ResponseBody> call = mNewsService.getNewsList("max-age=0",newsType,newsId,startPage);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(tag,"onResponse: " + response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(tag,"onFailure");
            }
        });
        return call;
    }
}
