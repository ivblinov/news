package com.example.news.ui.headlines_screen.tab_fragments.health;

import com.example.news.data.retrofit.Articles;
import java.util.List;
import moxy.MvpView;
import moxy.viewstate.strategy.alias.AddToEnd;

@AddToEnd
public interface Health extends MvpView {

    void createRecycler(List<Articles.Article> articles);

    void hideOrShowProgress(Boolean flag);
}
