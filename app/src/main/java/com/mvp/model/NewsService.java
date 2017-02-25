package com.mvp.model;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * Created by lxm.
 */

public interface NewsService {
    @GET("nc/article/{type}/{id}/{startPage}-20.html")
    Call<ResponseBody> getNewsList(
            @Header("Cache-Control") String cacheControl,
            @Path("type") String type,
            @Path("id") String id,
            @Path("startPage") int startPage
    );
}
