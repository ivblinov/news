package com.example.news.ui.headlines_screen;

import com.example.news.data.retrofit.Articles;

import moxy.MvpView;
import moxy.viewstate.strategy.alias.AddToEndSingle;
import retrofit2.Call;

@AddToEndSingle
public interface Headlines extends MvpView {

//    void show(Call<Articles> articles);
    void show(Articles articles);
}
