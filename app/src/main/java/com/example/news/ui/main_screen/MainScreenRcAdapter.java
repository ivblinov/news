package com.example.news.ui.main_screen;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.news.data.retrofit.Articles;
import com.example.news.databinding.RecyclerItemBinding;

import java.util.List;

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
//        Log.d(TAG, "onBindViewHolder: " + articlesList.get(position).source.name);
        holder.binding.article.setText(articlesList.get(position).title);
        holder.binding.articleTitle.setText(articlesList.get(position).source.name);
        Glide
                .with(holder.binding.getRoot())
                .load(articlesList.get(position).urlToImage)
                .into(holder.binding.imageArticle);
    }

    @Override
    public int getItemCount() {
        return articlesList.size();
    }

    private static final String TAG = "MyLog";
}