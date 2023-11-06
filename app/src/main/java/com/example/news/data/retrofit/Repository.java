package com.example.news.data.retrofit;

import android.util.Log;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;

public class Repository {

    public Observable<Call<Articles>> observableArticles(String category) {
        NewsService newsService = new NewsService();
        Call<Articles> articles = newsService.apiService.getTopHeadlines(category);

        return Observable.just(articles);
    }

    private static final String TAG = "MyLog";
}
