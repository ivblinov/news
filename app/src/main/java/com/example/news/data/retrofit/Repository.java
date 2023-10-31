package com.example.news.data.retrofit;

import android.util.Log;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;

public class Repository {

    public Observable<Call<Articles>> observableArticles() {
        NewsService newsService = new NewsService();
        Call<Articles> articles = newsService.apiService.getTopHeadlines();
        Log.d(TAG, "observableArticles: " + articles);

        return Observable.just(articles);
    }

    private static final String TAG = "MyLog";
}
