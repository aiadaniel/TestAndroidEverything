package com.mvp;

import android.os.Bundle;

import com.mvp.model.NewsManager;
import com.vigorous.activities.BaseActivity;
import com.vigorous.testandroideverything.R;

import retrofit2.Retrofit;

public class MvpActivity extends BaseActivity {
    private static final String tag = "11111";


    Retrofit mRetrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);

//        mRetrofit = new Retrofit.Builder().baseUrl("https://api.github.com/").build();
//        NewsService newsService = mRetrofit.create(NewsService.class);
//        Call<List<ResponseBody>> repos = newsService.getNewsList();
//        repos.enqueue(new Callback<List<ResponseBody>>() {
//            @Override
//            public void onResponse(Call<List<ResponseBody>> call, Response<List<ResponseBody>> response) {
//                Log.d(tag, "onResponse");
//            }
//
//            @Override
//            public void onFailure(Call<List<ResponseBody>> call, Throwable t) {
//                Log.d(tag, "onFailure");
//            }
//        });
        loadNews();
    }

    private void loadNews() {
        NewsManager.getInst().getNewsList("头条","T1348647909107",0);
    }
}
