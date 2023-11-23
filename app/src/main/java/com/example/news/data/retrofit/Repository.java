package com.example.news.data.retrofit;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;

public class Repository {

    public Observable<Call<Articles>> observableArticles(String category, Integer pageSize, Integer page) {
        NewsService newsService = new NewsService();
        Call<Articles> articles = newsService.apiService.getTopHeadlines(category, pageSize, page);
        return Observable.just(articles);
    }

    public Observable<Call<Articles>> observableArticles(String category, Integer pageSize, Integer page, String q) {
        NewsService newsService = new NewsService();
        Call<Articles> articles = newsService.apiService.getTopHeadlines(category, pageSize, page, q);
        return Observable.just(articles);
    }
}
