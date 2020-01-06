package com.example.dou.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.dou.R;
import com.example.dou.adapter.ViewPagerAdapter;
import com.example.dou.fragment.ViewPagerVideoFragment;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private CollapsingToolbarLayout toolbarLayout;
    private List<ViewPagerVideoFragment> viewPagerVideoFragments = new ArrayList<>();

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
        viewPagerVideoFragments.add(new ViewPagerVideoFragment("作品","neirong1",null));
        viewPagerVideoFragments.add(new ViewPagerVideoFragment("动态","neirong2",null));
        viewPagerVideoFragments.add(new ViewPagerVideoFragment("喜欢","neirong3",null));
        //        设置viewpager适配器，以及添加适配器数据
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),viewPagerVideoFragments);
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
