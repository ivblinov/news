package com.example.news.ui.headlines_screen.tab_fragments.technology;

import android.util.Log;

import androidx.recyclerview.widget.DiffUtil;

import com.example.news.data.retrofit.Articles;
import com.example.news.data.retrofit.Repository;
import com.example.news.ui.headlines_screen.ArticlesDiffUtilCallBack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import moxy.InjectViewState;
import moxy.MvpPresenter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class TechnologyPresenter extends MvpPresenter<Technology> {

    Repository repository = new Repository();
    Integer pageSize = 12;
    Integer page = 1;
    ArrayList<Articles.Article> oldArticlesList = new ArrayList<>();
    ArrayList<Articles.Article> newArticlesList = new ArrayList<>();

    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    void orderData() {
        getViewState().hideOrShowProgress(true);

        Observer<Call<Articles>> observer = new Observer<Call<Articles>>() {
            @Override
            public void onError(Throwable e) {
            }
            @Override
            public void onComplete() {
            }
            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onNext(@androidx.annotation.NonNull @NonNull Call<Articles> articlesCall) {
                articlesCall.enqueue(new Callback<Articles>() {

                    @Override
                    public void onResponse(Call<Articles> call, Response<Articles> response) {
                        if (response.isSuccessful()) {
                            assert response.body() != null;
                            getViewState().createRecycler(Arrays.asList(response.body().article));
                            Collections.addAll(oldArticlesList, response.body().article);
                            Collections.addAll(newArticlesList, response.body().article);
                            getViewState().hideOrShowProgress(false);
                        }
                    }
                    @Override
                    public void onFailure(Call<Articles> call, Throwable t) {
                    }
                });
            }
        };
        repository.observableArticles("technology", pageSize, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getExtraArticles() {
        getViewState().hideOrShowProgress(true);

        Observer<Call<Articles>> observer = new Observer<Call<Articles>>() {
            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }

            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onNext(@androidx.annotation.NonNull @NonNull Call<Articles> articlesCall) {
                articlesCall.enqueue(new Callback<Articles>() {
                    @Override
                    public void onResponse(Call<Articles> call, Response<Articles> response) {
                        if (response.isSuccessful()) {
                            assert response.body() != null;
                            Collections.addAll(newArticlesList, response.body().article);
                            getViewState().reload();
                        }
                    }
                    @Override
                    public void onFailure(Call<Articles> call, Throwable t) {
                    }
                });
            }
        };

        page += 1;

        repository.observableArticles("technology", pageSize, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public DiffUtil.DiffResult updateList() {
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(
                new ArticlesDiffUtilCallBack(
                        oldArticlesList,
                        newArticlesList
                )
        );
        return result;
    }

    private static final String TAG = "MyLog";
}
