package com.example.news.data.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiService {

    String api_0 = "76e65516d700446cbba1ec05b084148f";
    String api_1 = "755f011367234479a6fc87c4fbd87e59";
    String api_2 = "a9201dcf799849e18275d09222d2aafa";


    @Headers("X-Api-Key: a9201dcf799849e18275d09222d2aafa")
    @GET("top-headlines?country=us")
    Call<Articles> getTopHeadlines(
            @Query("category") String category,
            @Query("pageSize") Integer pageSize,
            @Query("page") Integer page
    );
}
