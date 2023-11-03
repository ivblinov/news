package com.example.news.ui.headlines_screen.tab_fragments.science;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.news.R;
import com.example.news.data.retrofit.Articles;
import com.example.news.databinding.FragmentScienceBinding;
import com.example.news.ui.main_screen.MainScreenRcAdapter;

import org.jetbrains.annotations.Contract;

import java.util.List;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;


public class ScienceFragment extends MvpAppCompatFragment implements Science {

    private FragmentScienceBinding binding;

    @InjectPresenter
    SciencePresenter presenter;

    @NonNull
    @Contract(" -> new")
    public static ScienceFragment newInstance() {
        return new ScienceFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentScienceBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.orderData();
    }

    @Override
    public void createRecycler(@NonNull List<Articles.Article> articles) {
        Log.d(TAG, "createRecycler: " + articles.get(0).author);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(new MainScreenRcAdapter(articles));
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
    }

    @Override
    public void hideOrShowProgress(@NonNull Boolean flag) {
        if (flag)
            binding.progress.setVisibility(View.VISIBLE);
        else
            binding.progress.setVisibility(View.INVISIBLE);
    }

    private static final String TAG = "MyLog";
}