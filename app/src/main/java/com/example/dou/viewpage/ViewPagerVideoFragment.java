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

import com.example.dou.App;
import com.example.dou.R;
import com.example.dou.adapter.ZuopingAdapter;
import com.example.dou.pojo.Video;
import com.example.dou.recycler_pojo.ZuopingPojo;
import com.example.dou.utils.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class ViewPagerVideoFragment extends Fragment {

    private String title;
    private String content;
    private View view;
    private List<Video> videos = new ArrayList<>();
    RecyclerView zuoping;

    private final static String TAG = "ViewPagerVideoFragment";


    public ViewPagerVideoFragment(final String title, final String content) {
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
        String url="";
        String userId=((App)getActivity().getApplication()).getUser().getUserId();
        HttpUtil.getUserVideoAndInfoHttp(url,userId, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ZuopingAdapter zuopingAdapter = new ZuopingAdapter(videos);
                GridLayoutManager grid = new GridLayoutManager(getActivity(),3);
                zuoping.setLayoutManager(grid);
                zuoping.setAdapter(zuopingAdapter);
            }
        });
    }

    public String getTitle() {
        return title;
    }
}
