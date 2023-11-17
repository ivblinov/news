package com.example.news.ui.headlines_screen.tab_fragments.sports;

import com.example.news.data.retrofit.Articles;

import java.util.List;

import moxy.MvpView;
import moxy.viewstate.strategy.alias.AddToEndSingle;

@AddToEndSingle
public interface Sports extends MvpView {

    void createRecycler(List<Articles.Article> articles);

    void hideOrShowProgress(Boolean flag);

    void reload();
}
