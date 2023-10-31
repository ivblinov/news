package com.example.news.ui.headlines_screen.tab_fragments.general;

import com.example.news.data.retrofit.Articles;

import java.util.List;

import moxy.MvpView;
import moxy.viewstate.strategy.alias.AddToEndSingle;

@AddToEndSingle
public interface General extends MvpView {
    void createRecycler(List<Articles.Article> asList);

/*    List<Articles.Article> orderData();

    void getData(Articles.Article[] article);*/
}
