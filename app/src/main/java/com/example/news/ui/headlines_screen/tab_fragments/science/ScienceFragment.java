package com.example.news.ui.headlines_screen.tab_fragments.science;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.DiffUtil;
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

import java.util.ArrayList;
import java.util.List;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;


public class ScienceFragment extends MvpAppCompatFragment implements Science {

    private FragmentScienceBinding binding;
    Integer itemCount;
    View lastVisibleItem;
    Boolean loadData = false;
    RecyclerView.LayoutManager layoutManager;
    MainScreenRcAdapter adapter;

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
        layoutManager = new LinearLayoutManager(requireContext());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.orderData();

        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                itemCount = layoutManager.getItemCount();
                lastVisibleItem = layoutManager.findViewByPosition(itemCount - 1);
                if (lastVisibleItem != null && !loadData) {
                    presenter.getExtraArticles();
                    hideOrShowProgress(true);
                    loadData = true;
                }
            }
        });

        binding.swipeRefreshLayout.setOnRefreshListener(() -> {

            Log.d(TAG, "onRefresh: ");
            presenter.page = 0;
            presenter.newArticlesList = new ArrayList<>();
            presenter.getExtraArticles();
            binding.progress.setVisibility(View.INVISIBLE);
            binding.swipeRefreshLayout.setRefreshing(false);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getParentFragmentManager().setFragmentResultListener(
                "key", this, new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        String res = result.getString("1");
                        Log.d(TAG, "Science - " + res);
                    }
                });
    }

    @Override
    public void createRecycler(@NonNull List<Articles.Article> articles) {
        adapter = new MainScreenRcAdapter(articles);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
    }

    @Override
    public void hideOrShowProgress(@NonNull Boolean flag) {
        if (flag)
            binding.progress.setVisibility(View.VISIBLE);
        else
            binding.progress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void reload() {
        DiffUtil.DiffResult result = presenter.updateList();
        if (adapter != null) {
            adapter.reloadListAdapter(presenter.newArticlesList);
            result.dispatchUpdatesTo(adapter);
            presenter.oldArticlesList = presenter.newArticlesList;
            loadData = false;
            hideOrShowProgress(false);
        }
    }

    private static final String TAG = "MyLog";
}