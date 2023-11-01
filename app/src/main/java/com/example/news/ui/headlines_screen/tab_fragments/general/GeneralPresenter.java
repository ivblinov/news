package com.example.news.ui.headlines_screen.tab_fragments.general;

import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news.data.retrofit.Articles;
import com.example.news.data.retrofit.Repository;
import com.example.news.databinding.FragmentGeneralBinding;
import com.example.news.ui.main_screen.MainScreenRcAdapter;

import java.util.Arrays;
import java.util.List;

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
public class GeneralPresenter extends MvpPresenter<General> {

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
            public void onNext(@NonNull Call<Articles> articlesCall) {
                Log.d(TAG, "onNext: " + articlesCall);

                articlesCall.enqueue(new Callback<Articles>() {

                    @Override
                    public void onResponse(Call<Articles> call, Response<Articles> response) {
                        if (response.isSuccessful()) {
                            assert response.body() != null;

                            Log.d(TAG, "name = " + response.body().article[0].source);

                            Log.d(TAG, "onResponse: " + response.body().article[1].author);
//                            getViewState().getData(response.body().article);
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

        repository.observableArticles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private static final String TAG = "MyLog";
}
