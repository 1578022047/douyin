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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.dou.App;
import com.example.dou.R;
import com.example.dou.RecyclerViewPageChangeListenerHelper;
import com.example.dou.adapter.VideoAdapter;
import com.example.dou.pojo.Flag;
import com.example.dou.pojo.User;
import com.example.dou.pojo.Video;
import com.example.dou.utils.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

public class AttentionFragment extends AddMethodFragment {
    private SwipeRefreshLayout swipeRefresh;
    RecyclerView videoList;
    List<Video> videos=new ArrayList<>();
    List<Flag> flags=new ArrayList<>();
    List<User> users=new ArrayList<>();
    LinearLayoutManager layoutManager;
    VideoAdapter adapter;
    private String title;
    User user;
    int curPosition=0;

    public String getTitle() {
        return "关注";
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.attention_fragment,null);
        videoList=view.findViewById(R.id.videoList);
        swipeRefresh=view.findViewById(R.id.swipe_refresh);
        swipeRefresh=view.findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorButtomRed);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                users.clear();
                videos.clear();
                flags.clear();
                getVideo();
            }
        });
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
        user=((App)getActivity().getApplication()).getUser();
    }
    private void getVideo() {
        String url = HttpUtil.host + "getFiveVideo";
        String userId;
        if(user!=null) {
            userId=user.getUserId();
        }else{
            userId="";
        }
        HttpUtil.getFiveVideoHttp(url,userId, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Map<String, String> map = null;
                try {
                    map = new Gson().fromJson(response.body().string(), Map.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                users.addAll(new Gson().fromJson(map.get("userList"), new TypeToken<List<User>>() {
                }.getType()));
                videos.addAll(new Gson().fromJson(map.get("videoList"), new TypeToken<List<Video>>() {
                }.getType()));
                flags.addAll(new Gson().fromJson(map.get("flagList"),new TypeToken<List<Flag>>() {
                }.getType()));
                new Handler(getActivity().getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefresh.setRefreshing(false);
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
        adapter=new VideoAdapter(videos,users,flags,getContext());
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
