package com.example.news.ui.headlines_screen;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.news.R;
import com.example.news.data.retrofit.Articles;
import com.example.news.databinding.FragmentHeadlinesBinding;
import com.example.news.ui.headlines_screen.tab_fragments.BusinessFragment;
import com.example.news.ui.headlines_screen.tab_fragments.general.GeneralFragment;
import com.google.android.material.tabs.TabLayoutMediator;
import java.util.Arrays;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;

public class HeadlinesFragment extends MvpAppCompatFragment implements Headlines, View.OnClickListener {
    private FragmentHeadlinesBinding binding;

    public static HeadlinesFragment newInstance() {
        return new HeadlinesFragment();
    }

    public Fragment[] tabListFragment = {
            GeneralFragment.newInstance(),
            BusinessFragment.newInstance()
    };

    public int[] tabListTitles = {
            R.string.general_tab,
            R.string.business_tab
    };

    public int[] tabListIcon = {
            R.drawable.ic_general_tab,
            R.drawable.ic_business_tab
    };

    @InjectPresenter
    HeadlinesPresenter presenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHeadlinesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        createAdapter();

//        binding.button.setOnClickListener(this);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    void createAdapter() {
        VpAdapterHeadlines adapter = new VpAdapterHeadlines(this, Arrays.asList(tabListFragment));
        binding.viewPagerHeadlines.setAdapter(adapter);
        new TabLayoutMediator(binding.tabHeadlines, binding.viewPagerHeadlines,
                (tab, position) -> {
                    tab.setText(tabListTitles[position]);
                    tab.setIcon(tabListIcon[position]);
                }).attach();
    }

    @Override
    public void show(Articles articles) {
//        binding.text.setText(articles.article[1].author);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: ");
        presenter.orderData();
    }

    private static final String TAG = "MyLog";
}