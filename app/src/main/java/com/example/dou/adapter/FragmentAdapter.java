package com.example.dou.adapter;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.dou.fragment.AddMethodFragment;

import java.util.List;

public class FragmentAdapter extends FragmentPagerAdapter {
    private List<AddMethodFragment> list;

    public FragmentAdapter(FragmentManager fm, List<AddMethodFragment> list) {
        super(fm);
        this.list = list;
    }

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return list.get(i);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(final int position) {
        return list.get(position).getTitle();
    }
}
