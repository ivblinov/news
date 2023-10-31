package com.example.news.ui.main_screen;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news.databinding.RecyclerItemBinding;

public class MainScreenViewHolder extends RecyclerView.ViewHolder {
    RecyclerItemBinding binding;

    public MainScreenViewHolder(@NonNull RecyclerItemBinding binding) {
        super(binding.recyclerItem);
        this.binding = binding;
    }
}
