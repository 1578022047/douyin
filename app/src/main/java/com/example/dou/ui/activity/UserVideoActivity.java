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
        videos= (List<Video>) getIntent().getSerializableExtra("videos");
        final PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(videoList);
        layoutManager=new LinearLayoutManager(this);
        videoList.setLayoutManager(layoutManager);
        adapter=new UserVideoAdapter(videos,user,this);
        videoList.setAdapter(adapter);
        videoList.getItemAnimator().setChangeDuration(0);
        videoList.setItemAnimator(null);
    }

}
