package com.example.dou.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dou.R;
import com.example.dou.RecyclerViewPageChangeListenerHelper;
import com.example.dou.adapter.VideoAdapter;
import com.example.dou.pojo.Video;
import com.example.dou.utils.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class RecommendFragment extends AddMethodFragment  {
    RecyclerView videoList;
    List<Video> videos=new ArrayList<>();
    LinearLayoutManager layoutManager;
    VideoAdapter adapter;
    private String title;
    int curPosition=0;

    public String getTitle() {
        return "推荐";
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.attention_fragment,null);
        videoList=view.findViewById(R.id.videoList);
        initListener();
        initDate();
        initView();
        return view;
    }

    private void initListener() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private void initDate() {

    }
    private void getVideo(){
        String url=HttpUtil.host+"getFiveVideo";
        HttpUtil.getFiveVideoHttp(url, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                new Handler(getActivity().getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {


                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }
    private void initView() {
        final PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(videoList);
        layoutManager=new LinearLayoutManager(getContext());
        videoList.setLayoutManager(layoutManager);
        adapter=new VideoAdapter(videos,getContext());
        videoList.setAdapter(adapter);
        videoList.getItemAnimator().setChangeDuration(0);
        videoList.setItemAnimator(null);
        getVideo();
        videoList.addOnScrollListener(new RecyclerViewPageChangeListenerHelper(pagerSnapHelper,
                new RecyclerViewPageChangeListenerHelper.OnPageChangeListener() {


                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        adapter.setPlay(position);
                        if(videos.size()-position<=3){
                            getVideo();
                        }
                        curPosition=position;
                    }
                }));
    }

    @Override
    public void onPause() {
        super.onPause();
        adapter.setPlay(-1);
    }

    @Override
    public void onResume() {
        super.onResume();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}

