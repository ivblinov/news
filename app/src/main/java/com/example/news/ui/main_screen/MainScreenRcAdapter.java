package com.example.news.ui.main_screen;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.news.data.retrofit.Articles;
import com.example.news.databinding.RecyclerItemBinding;
import com.example.news.domain.App;
import com.example.news.domain.Screens;
import com.example.news.ui.news_screen.ArticleParcel;
import com.github.terrakok.cicerone.Screen;

import java.util.Formatter;
import java.util.List;
import java.util.function.Consumer;

public class MainScreenRcAdapter extends RecyclerView.Adapter<MainScreenViewHolder> {
    private List<Articles.Article> articlesList;

    public MainScreenRcAdapter(List<Articles.Article> articles) {
        articlesList = articles;
    }

    @NonNull
    @Override
    public MainScreenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerItemBinding binding = RecyclerItemBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new MainScreenViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MainScreenViewHolder holder, int position) {
        holder.binding.article.setText(articlesList.get(position).title);
        holder.binding.articleTitle.setText(articlesList.get(position).source.name);
        Glide
                .with(holder.binding.getRoot())
                .load(articlesList.get(position).urlToImage)
                .into(holder.binding.imageArticle);

        Formatter f = new Formatter();
        String urlLoad = String.valueOf(f.format("https://besticon-demo.herokuapp.com/icon?url=%s&size=50..100..500",
                articlesList.get(position).url));

        Glide
                .with(holder.binding.getRoot())
                .load(urlLoad)
                .into(holder.binding.logo);

        holder.binding.getRoot().setOnClickListener(v -> {
            ArticleParcel parceObject = new ArticleParcel(
                    articlesList.get(position).urlToImage,
                    articlesList.get(position).title,
                    articlesList.get(position).publishedAt,
                    articlesList.get(position).content,
                    articlesList.get(position).source.name,
                    articlesList.get(position).url
            );

            App.Companion.getInstance().getRouter().navigateTo(Screens.INSTANCE.newsScreenFragment(parceObject));

        });
    }

    @Override
    public int getItemCount() {
        return articlesList.size();
    }

    public void reloadListAdapter(List<Articles.Article> data) {
        articlesList = data;
    }

    public List<Articles.Article> getArticlesList() {
        return articlesList;
    }

    private static final String TAG = "MyLog";
}