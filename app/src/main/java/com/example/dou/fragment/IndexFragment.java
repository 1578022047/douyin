package com.example.dou.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.dou.R;
import com.example.dou.adapter.FragmentAdapter;
import com.example.dou.viewpage.AddMethodFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class IndexFragment extends Fragment {
    private FragmentAdapter adapter;
    private List<AddMethodFragment> list;
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<>();
        list.add(new AttentionFragment());
        list.add(new RecommendFragment());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_index,container,false);
        viewPager=view.findViewById(R.id.viewPager);
        init();
        tabLayout = view.findViewById(R.id.index_tablayout);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }


    private void init() {
        adapter=new FragmentAdapter(getActivity().getSupportFragmentManager(),list);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
    }

}
