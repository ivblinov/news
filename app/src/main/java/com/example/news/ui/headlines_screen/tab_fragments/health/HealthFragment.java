package com.example.news.ui.headlines_screen.tab_fragments.health;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.news.data.retrofit.Articles;
import com.example.news.databinding.FragmentHealthBinding;
import com.example.news.ui.main_screen.MainScreenRcAdapter;
import org.jetbrains.annotations.Contract;
import java.util.List;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;

public class HealthFragment extends MvpAppCompatFragment implements Health {

    private FragmentHealthBinding binding;

    @InjectPresenter
    HealthPresenter presenter;

    @NonNull
    @Contract(" -> new")
    public static HealthFragment newInstance() { return new HealthFragment(); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHealthBinding.inflate(inflater, container, false);
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