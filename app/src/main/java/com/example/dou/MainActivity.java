package com.example.dou;



import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.fragment.app.Fragment;

import com.example.dou.fragment.FragmentAdapter;
import com.example.dou.fragment.IndexFragment;
import com.example.dou.fragment.MeFragment;
import com.example.dou.fragment.MessageFragment;
import com.example.dou.fragment.TongchengFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FragmentAdapter adapter;
    private List<Fragment> list;
    private NoScrollViewPager mainPager;
    private ImageView add;
    private TextView index_textview;
    private TextView tongcheng_textview;
    private TextView message_textview;
    private TextView me_textview;
    //        初始化fragment，只new一次fragment增强效率

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();
        init();
        message_textview.setTextColor(Color.WHITE);

    }

    private void init() {
        list = new ArrayList<>();
        list.add(new IndexFragment());
        list.add(new TongchengFragment());
        list.add(new MessageFragment());
        list.add(new MeFragment());
        adapter=new FragmentAdapter(getSupportFragmentManager(),list);
        mainPager.setAdapter(adapter);
        mainPager.setCurrentItem(0);
        mainPager.setOffscreenPageLimit(4);
    }

    private void initListener() {
        add.setOnClickListener(this);
        index_textview.setOnClickListener(this);
        tongcheng_textview.setOnClickListener(this);
        message_textview.setOnClickListener(this);
        me_textview.setOnClickListener(this);
    }

    private void initView() {
        mainPager=findViewById(R.id.main_pager);
        add=findViewById(R.id.add);
        index_textview = findViewById(R.id.index);
        tongcheng_textview = findViewById(R.id.tongcheng);
        message_textview = findViewById(R.id.message);
        me_textview = findViewById(R.id.me);
    }

    private void isLogin() {
        startActivity(new Intent(MainActivity.this,VerifyLoginActivity.class));
    }
    @Override
    public void onClick(final View v) {
        switch (v.getId()){
            case R.id.index:
                mainPager.setCurrentItem(0);
                index_textview.setTextColor(Color.WHITE);
                tongcheng_textview.setTextColor(Color.parseColor("#9e9e9f"));
                message_textview.setTextColor(Color.parseColor("#9e9e9f"));
                me_textview.setTextColor(Color.parseColor("#9e9e9f"));
                break;
            case R.id.tongcheng:
                mainPager.setCurrentItem(1);
                tongcheng_textview.setTextColor(Color.WHITE);
                index_textview.setTextColor(Color.parseColor("#9e9e9f"));
                message_textview.setTextColor(Color.parseColor("#9e9e9f"));
                me_textview.setTextColor(Color.parseColor("#9e9e9f"));
                break;
            case R.id.message:
                isLogin();
                mainPager.setCurrentItem(2);
                message_textview.setTextColor(Color.WHITE);
                index_textview.setTextColor(Color.parseColor("#9e9e9f"));
                tongcheng_textview.setTextColor(Color.parseColor("#9e9e9f"));
                me_textview.setTextColor(Color.parseColor("#9e9e9f"));
                break;
            case R.id.me:
//                进入个人主页首先需要判断是否登陆
                isLogin();
                mainPager.setCurrentItem(3);

                /*Toolbar toolbar = findViewById(R.id.toolbar);
                CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collaps);
                setSupportActionBar(toolbar);
                ActionBar actionBar = getSupportActionBar();
                actionBar.setDisplayHomeAsUpEnabled(true);
                collapsingToolbarLayout.setTitle("hahha");*/
                me_textview.setTextColor(Color.WHITE);
                index_textview.setTextColor(Color.parseColor("#9e9e9f"));
                tongcheng_textview.setTextColor(Color.parseColor("#9e9e9f"));
                message_textview.setTextColor(Color.parseColor("#9e9e9f"));
                break;
            case R.id.add:
                startActivity(new Intent(MainActivity.this,RecordActivity.class));
            default:
                break;
        }
    }

}
