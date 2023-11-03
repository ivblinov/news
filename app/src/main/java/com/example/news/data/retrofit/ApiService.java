package com.example.news.data.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("top-headlines?country=us&apiKey=76e65516d700446cbba1ec05b084148f")
    Call<Articles> getTopHeadlines(@Query("category") String category);
}
