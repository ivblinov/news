package com.example.news.ui.headlines_screen;

import androidx.recyclerview.widget.DiffUtil;

import com.example.news.data.retrofit.Articles;

import java.util.ArrayList;
import java.util.Objects;

public class ArticlesDiffUtilCallBack extends DiffUtil.Callback {
    ArrayList<Articles.Article> oldArticlesList;
    ArrayList<Articles.Article> newArticlesList;

    public ArticlesDiffUtilCallBack(
            ArrayList<Articles.Article> oldList,
            ArrayList<Articles.Article> newList
    ) {
        this.oldArticlesList = oldList;
        this.newArticlesList = newList;
    }


    @Override
    public int getOldListSize() {
        return oldArticlesList.size();
    }

    @Override
    public int getNewListSize() {
        return newArticlesList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return Objects.equals(oldArticlesList.get(oldItemPosition).url, newArticlesList.get(newItemPosition).url);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldArticlesList.get(oldItemPosition) == newArticlesList.get(newItemPosition);
    }
}
