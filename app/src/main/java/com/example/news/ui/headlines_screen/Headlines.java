package com.example.news.ui.headlines_screen;


import moxy.MvpView;
import moxy.viewstate.strategy.alias.AddToEnd;

@AddToEnd
public interface Headlines extends MvpView {

    void createAdapter();
}
