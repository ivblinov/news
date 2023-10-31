package com.example.news.ui.headlines_screen;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class VpAdapterHeadlines extends FragmentStateAdapter {
    List<Fragment> list;

    public VpAdapterHeadlines(@NonNull Fragment fragment, List<Fragment> list) {
        super(fragment);
        this.list = list;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    
}
