package com.example.news.ui.headlines_screen;

import android.util.Log;

import androidx.recyclerview.widget.DiffUtil;

import com.example.news.data.retrofit.Articles;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class ArticlesDiffUtilCallBack extends DiffUtil.Callback {
    List<Articles.Article> oldArticlesList;
    List<Articles.Article> newArticlesList;

    public ArticlesDiffUtilCallBack(
            List<Articles.Article> oldList,
            List<Articles.Article> newList
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

    private static final String TAG = "MyLog";
}
