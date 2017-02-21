package com.vigorous.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vigorous.BitmapEntity;
import com.vigorous.FileDownload.DownloadMgr;
import com.vigorous.adapter.BaseRecycleViewAdapter;
import com.vigorous.testandroideverything.R;
import com.vigorous.utils.OkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

//use to test OOM
public class LoadBitmapActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "11111";
    private static final String apiUrl = "http://gank.io/api/data/";//size page

    RecyclerView mRecycleView;
    SwipeRefreshLayout mSwipe;
    ProgressBar mProgressBar;
    TextView mTvEmpty;

    BaseRecycleViewAdapter mAdapter;
    List<BitmapEntity> mBitmapEntities = new ArrayList<BitmapEntity>();

    StringBuilder urlBuilder = new StringBuilder(apiUrl);
    MyHandler handler = new MyHandler(this);

    private static class MyHandler extends Handler {
        WeakReference<LoadBitmapActivity> mLoadBitmapActivityWeakReference;
        public MyHandler(LoadBitmapActivity activity) {
            mLoadBitmapActivityWeakReference = new WeakReference<LoadBitmapActivity>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LoadBitmapActivity activity = mLoadBitmapActivityWeakReference.get();
            switch (msg.what) {
                case 0x1:
                    String response = (String) msg.obj;
                    //Log.d(TAG, response);
                    activity.parseJson(response);
                    activity.mAdapter.setBitmapLists(activity.mBitmapEntities);
                    //testDownloadMgr();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_bitmap);
        mRecycleView = (RecyclerView) findViewById(R.id.recycle_view);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mSwipe = (SwipeRefreshLayout) findViewById(R.id.swipe_fresh_layout);
        mTvEmpty = (TextView) findViewById(R.id.empty_tv);
        mSwipe.setOnRefreshListener(this);
        mSwipe.setColorSchemeColors(getResources().getIntArray(R.array.gplus_colors));

        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        mAdapter = new BaseRecycleViewAdapter(this,mBitmapEntities,BaseRecycleViewAdapter.CACHE_NONE);
        mRecycleView.setAdapter(mAdapter);
        justTestLoad();
        //testOkHttp();
    }

    private void parseJson(String response) {
        try {
            JSONObject object1 = new JSONObject(response);
            JSONArray array = object1.getJSONArray("results");
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                BitmapEntity entity = new BitmapEntity();
                entity._id = object.getString("_id");
                entity._createdAt = object.getString("createdAt");
                entity._desc = object.getString("desc");
                entity._publishedAt = object.getString("publishedAt");
                entity._source = object.getString("source");
                entity._type = object.getString("type");
                entity._url = object.getString("url");
                entity._used = object.getString("used");
                entity._who = object.getString("who");
                //Log.d(TAG, entity.toString());
                mBitmapEntities.add(entity);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, e.toString());
        }
    }
    //方法1
    private void testOkHttp() {
        try {
            urlBuilder.append(URLEncoder.encode("福利", "UTF-8"));
            urlBuilder.append("/50/1");
            OkUtils.okAsyncGet(urlBuilder.toString(),new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG, e.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.d(TAG, "res is " + response.code());
                    //Log.d(TAG,response.body().string());
                    parseJson(response.body().string());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.setBitmapLists(mBitmapEntities);
                        }
                    });

                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    //方法2
    private void justTestLoad() {
        //开启线程来发起网络请求
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    urlBuilder.append(URLEncoder.encode("福利", "UTF-8"));
                    urlBuilder.append("/50/1");
                    URL url = new URL(urlBuilder.toString());
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    int code = connection.getResponseCode();
                    if (code == HttpURLConnection.HTTP_OK) {//200
                        InputStream is = connection.getInputStream();
                        StringBuilder builder = new StringBuilder();
                        String line;
                        BufferedReader bais = new BufferedReader(new InputStreamReader(is));
                        while ((line = bais.readLine()) != null) {
                            builder.append(line);
                        }
                        Message msg = new Message();
                        msg.what = 0x1;
                        msg.obj = builder.toString();
                        handler.sendMessage(msg);
                    } else {
                        Log.e(TAG, "res is  " + code);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    Log.e(TAG, e.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, e.toString());
                }
            }
        }).start();
    }

    public void testDownloadMgr() {
        for (BitmapEntity entity : mBitmapEntities) {
            DownloadMgr.getInstance(getApplicationContext()).addDownloadTask(entity._url,null);
        }
    }

    @Override
    public void onRefresh() {

    }
}
