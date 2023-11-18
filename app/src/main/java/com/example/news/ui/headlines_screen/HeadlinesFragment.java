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
import com.example.news.databinding.FragmentHeadlinesBinding;
import com.example.news.ui.headlines_screen.tab_fragments.business.BusinessFragment;
import com.example.news.ui.headlines_screen.tab_fragments.entertainment.EntertainmentFragment;
import com.example.news.ui.headlines_screen.tab_fragments.general.GeneralFragment;
import com.example.news.ui.headlines_screen.tab_fragments.health.HealthFragment;
import com.example.news.ui.headlines_screen.tab_fragments.science.ScienceFragment;
import com.example.news.ui.headlines_screen.tab_fragments.sports.SportsFragment;
import com.example.news.ui.headlines_screen.tab_fragments.technology.TechnologyFragment;
import com.example.news.ui.search_screen.SearchViewModel;
import com.google.android.material.tabs.TabLayoutMediator;
import java.util.Arrays;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;

public class HeadlinesFragment extends MvpAppCompatFragment implements Headlines {
    private FragmentHeadlinesBinding binding;

    public static HeadlinesFragment newInstance() {
        return new HeadlinesFragment();
    }

    public Fragment[] tabListFragment = {
            GeneralFragment.newInstance(),
            BusinessFragment.newInstance(),
            HealthFragment.newInstance(),
            SportsFragment.newInstance(),
            EntertainmentFragment.newInstance(),
            TechnologyFragment.newInstance(),
            ScienceFragment.newInstance()
    };

    public int[] tabListTitles = {
            R.string.general_tab,
            R.string.business_tab,
            R.string.health_tab,
            R.string.sports_tab,
            R.string.entertainment_tab,
            R.string.technology_tab,
            R.string.science_tab
    };

    public int[] tabListIcon = {
            R.drawable.ic_general_tab,
            R.drawable.ic_business_tab,
            R.drawable.ic_health_tab,
            R.drawable.ic_sports_tab,
            R.drawable.ic_entertainment_tab,
            R.drawable.ic_technology_tab,
            R.drawable.ic_science_tab
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void createAdapter() {
        VpAdapterHeadlines adapter = new VpAdapterHeadlines(this, Arrays.asList(tabListFragment));
        binding.viewPagerHeadlines.setAdapter(adapter);
        new TabLayoutMediator(binding.tabHeadlines, binding.viewPagerHeadlines,
                (tab, position) -> {
                    tab.setText(tabListTitles[position]);
                    tab.setIcon(tabListIcon[position]);
                }).attach();
    }

    private static final String TAG = "MyLog";
}