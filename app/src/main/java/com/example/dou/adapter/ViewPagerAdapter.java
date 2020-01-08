package com.example.dou.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.dou.fragment.ZuopingViewPagerFragment;

import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<ZuopingViewPagerFragment> fragments;

    public ViewPagerAdapter(@NonNull final FragmentManager fm,List<ZuopingViewPagerFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(final int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(final int position) {
        return fragments.get(position).getTitle();
    }
}
