package com.example.news.ui.headlines_screen;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class HeadlinesPresenter extends MvpPresenter<Headlines> {

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    public HeadlinesPresenter() {
        getViewState().createAdapter();
    }
}
