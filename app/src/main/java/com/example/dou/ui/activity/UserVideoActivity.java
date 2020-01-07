package com.example.dou.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.dou.R;
import com.example.dou.RecyclerViewPageChangeListenerHelper;
import com.example.dou.adapter.UserVideoAdapter;
import com.example.dou.adapter.VideoAdapter;
import com.example.dou.pojo.User;
import com.example.dou.pojo.Video;

import java.util.ArrayList;
import java.util.List;

public class UserVideoActivity extends AppCompatActivity {
    RecyclerView videoList;
    List<Video> videos=new ArrayList<>();
    User user;
    LinearLayoutManager layoutManager;
    UserVideoAdapter adapter;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_video);
        initView();
        initDate();
    }

    private void initView() {
        videoList=findViewById(R.id.videoList);
    }
    private void initDate() {
        user=(User)getIntent().getSerializableExtra("user");
        videos= (List<Video>) getIntent().getSerializableExtra("videoList");
        index=getIntent().getIntExtra("index",0);
        final PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(videoList);
        layoutManager=new LinearLayoutManager(this);
        videoList.setLayoutManager(layoutManager);
        adapter=new UserVideoAdapter(videos,user,this);
        adapter.setPlay(index);
        videoList.setAdapter(adapter);
        videoList.getItemAnimator().setChangeDuration(0);
        videoList.setItemAnimator(null);
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
                    }
                }));
        layoutManager.scrollToPositionWithOffset(index, 0);
        layoutManager.setStackFromEnd(true);
    }

}
