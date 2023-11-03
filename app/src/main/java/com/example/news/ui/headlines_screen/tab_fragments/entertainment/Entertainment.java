package com.example.news.ui.headlines_screen.tab_fragments.entertainment;

import com.example.news.data.retrofit.Articles;

import java.util.List;

import moxy.MvpView;
import moxy.viewstate.strategy.alias.AddToEnd;

@AddToEnd
public interface Entertainment extends MvpView {
    void createRecycler(List<Articles.Article> articles);

    void hideOrShowProgress(Boolean flag);
}