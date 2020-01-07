package com.example.dou.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dou.App;
import com.example.dou.R;
import com.example.dou.adapter.UserVideoAdapter;
import com.example.dou.adapter.ZuopingAdapter;
import com.example.dou.pojo.Flag;
import com.example.dou.pojo.User;
import com.example.dou.pojo.Video;
import com.example.dou.utils.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class ViewPagerVideoFragment extends Fragment {

    private String title;
    private String content;
    private User user;
    private View view;
    private List<Video> videos ;
    private List<Flag> flags ;
    RecyclerView zuoping;

    private final static String TAG = "ViewPagerVideoFragment";


    public ViewPagerVideoFragment(final String title, final String content, List<Video> videos,List<Flag> flags, User user) {
        this.title = title;
        this.content = content;
        this.videos= videos;
        this.flags=flags;
        this.user=user;
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_view_pager,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        zuoping= view.findViewById(R.id.zuoping_recycler);
        initData();
    }

    private void initData() {
        ZuopingAdapter adapter = new ZuopingAdapter(videos,user,flags,getContext());
        GridLayoutManager grid = new GridLayoutManager(getActivity(), 3);
        zuoping.setLayoutManager(grid);
        zuoping.setAdapter(adapter);
    }

    public String getTitle() {
        return title;
    }
}
