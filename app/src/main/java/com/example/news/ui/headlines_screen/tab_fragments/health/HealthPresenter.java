package com.example.news.ui.headlines_screen.tab_fragments.health;

import android.util.Log;
import com.example.news.data.retrofit.Articles;
import com.example.news.data.retrofit.Repository;
import java.util.Arrays;
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
public class HealthPresenter extends MvpPresenter<Health> {

    Repository repository = new Repository();

    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    void orderData() {
        getViewState().hideOrShowProgress(true);

        Observer<Call<Articles>> observer = new Observer<Call<Articles>>() {
            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(@androidx.annotation.NonNull @NonNull Call<Articles> articlesCall) {
                articlesCall.enqueue(new Callback<Articles>() {

                    @Override
                    public void onResponse(Call<Articles> call, Response<Articles> response) {
                        if (response.isSuccessful()) {
                            assert response.body() != null;
                            getViewState().createRecycler(Arrays.asList(response.body().article));
                            getViewState().hideOrShowProgress(false);
                        }
                        else Log.d(TAG, "not successful");
                    }

                    @Override
                    public void onFailure(Call<Articles> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + t);
                    }
                });
            }
        };

        repository.observableArticles("health")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private static final String TAG = "MyLog";
}
