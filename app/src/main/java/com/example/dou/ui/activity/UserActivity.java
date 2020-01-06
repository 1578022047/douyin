package com.example.dou.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.dou.App;
import com.example.dou.R;
import com.example.dou.adapter.ViewPagerAdapter;
import com.example.dou.fragment.ViewPagerVideoFragment;
import com.example.dou.pojo.User;
import com.example.dou.pojo.Video;
import com.example.dou.utils.HttpUtil;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

public class UserActivity extends AppCompatActivity implements View.OnClickListener {

    private String userId;
    private User user;
    private List<Video> userVideos=new ArrayList<>();
    private List<Video> likeVideos=new ArrayList<>();
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private CollapsingToolbarLayout toolbarLayout;
    private List<ViewPagerVideoFragment> viewPagerVideoFragments = new ArrayList<>();
    ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        init();
        initListener();

//        toolbar等操作
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbarLayout.setTitle("标题可以设置成自己想要的标题");

//        viewpager及tablayout等操作
        //        设置viewpager适配器，以及添加适配器数据
        adapter = new ViewPagerAdapter(getSupportFragmentManager(),viewPagerVideoFragments);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void initListener() {
        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        toolbarLayout = findViewById(R.id.collaps);
    }

    private void init() {
        userId=getIntent().getStringExtra("userId");
        String url= HttpUtil.host+"getUserVideoAndInfo";
        HttpUtil.getUserVideoAndInfoHttp(url, userId, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Map<String, String> map = new Gson().fromJson(response.body().string(), Map.class);
                            user = new Gson().fromJson(map.get("user"), User.class);
                            userVideos = new Gson().fromJson(map.get("userVideoList"), new TypeToken<List<Video>>() {
                            }.getType());
                            likeVideos = new Gson().fromJson(map.get("likeVideoList"), new TypeToken<List<Video>>() {
                            }.getType());
                            viewPagerVideoFragments.add(new ViewPagerVideoFragment("作品", "neirong1", userVideos,user));
                            viewPagerVideoFragments.add(new ViewPagerVideoFragment("动态", "neirong2", userVideos,user));
                            viewPagerVideoFragments.add(new ViewPagerVideoFragment("喜欢", "neirong3", likeVideos,user));
                            adapter.notifyDataSetChanged();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

    }

    @Override
    public void onClick(final View v) {


    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.user_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull final MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.user_info:
                Intent intent = new Intent(this,UserInfoActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
