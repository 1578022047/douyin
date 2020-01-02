package com.example.dou.viewpage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dou.R;
import com.example.dou.adapter.ZuopingAdapter;
import com.example.dou.recycler_pojo.ZuopingPojo;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerDongtaiFragment extends Fragment {
    private String title;
    private String content;
    private View view;
    private List<ZuopingPojo> zuopingPojoList = new ArrayList<>();

    public ViewPagerDongtaiFragment(final String title, final String content) {
        this.title = title;
        this.content = content;
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_view_pager_dongtai,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RecyclerView zuoping= view.findViewById(R.id.zuoping_recycler);
        initData();

        ZuopingAdapter zuopingAdapter = new ZuopingAdapter(zuopingPojoList);
        GridLayoutManager grid = new GridLayoutManager(getActivity(),3);
        zuoping.setLayoutManager(grid);
        zuoping.setAdapter(zuopingAdapter);
    }

    private void initData() {

    }

    public String getTitle() {
        return title;
    }
}
