package com.example.news.ui.headlines_screen;

import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news.data.retrofit.Articles;
import com.example.news.data.retrofit.Repository;
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
public class HeadlinesPresenter extends MvpPresenter<Headlines> {

    Repository repository = new Repository();

//    Context context = (Context) getViewState();


    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    public HeadlinesPresenter() {
    }

    void orderData() {

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
            public void onNext(@NonNull Call<Articles> articlesCall) {
                Log.d(TAG, "onNext: " + articlesCall);

                articlesCall.enqueue(new Callback<Articles>() {

                    @Override
                    public void onResponse(Call<Articles> call, Response<Articles> response) {
                        if (response.isSuccessful()) {
                            assert response.body() != null;
                            Log.d(TAG, "onResponse: " + response.body().article[1].author);
                            getViewState().show(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<Articles> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + t);
                    }
                });


            }
        };

        repository.observableArticles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }






    private static final String TAG = "MyLog";
}
