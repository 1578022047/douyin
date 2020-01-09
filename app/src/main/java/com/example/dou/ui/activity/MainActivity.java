package com.example.dou.ui.activity;



import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.fragment.app.Fragment;

import com.example.dou.App;
import com.example.dou.adapter.IndexFragmentAdapter;
import com.example.dou.pojo.User;
import com.example.dou.utils.HttpUtil;
import com.example.dou.view.NoScrollViewPager;
import com.example.dou.R;
import com.example.dou.adapter.FragmentAdapter;
import com.example.dou.fragment.IndexFragment;
import com.example.dou.fragment.MeFragment;
import com.example.dou.fragment.MessageFragment;
import com.example.dou.fragment.TongchengFragment;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private IndexFragmentAdapter adapter;
    private List<Fragment> list;
    private NoScrollViewPager mainPager;
    private ImageView add;
    private TextView index_textview;
    private TextView tongcheng_textview;
    private TextView message_textview;
    private TextView me_textview;
    private LinearLayout linearLayout;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autoLogin();
        initView();
        initListener();
        init();
        index_textview.setTextColor(Color.WHITE);
        linearLayout.getBackground().setAlpha(0);

    }

    private void autoLogin() {
        pref = getSharedPreferences("userData",MODE_PRIVATE);
        editor = pref.edit();
        if (!pref.getString("userId","0").equals("0")){
            String url=HttpUtil.host+"/getUser?userId="+pref.getString("userId","0");
            HttpUtil.sendGetHttpRequest(url, new okhttp3.Callback() {
                @Override
                public void onFailure(final Call call, final IOException e) {

                }

                @Override
                public void onResponse(final Call call, final Response response) throws IOException {
                    String result = response.body().string();
                    ((App)getApplication()).setUser(new Gson().fromJson(result,User.class));
                }
            });
        }

    }

    private void init() {
        list = new ArrayList<>();
        list.add(new IndexFragment());
        list.add(new TongchengFragment());
        list.add(new MessageFragment());
        list.add(new MeFragment());
        adapter=new IndexFragmentAdapter(getSupportFragmentManager(),list);
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
        linearLayout = findViewById(R.id.line);
        mainPager=findViewById(R.id.main_pager);
        add=findViewById(R.id.add);
        index_textview = findViewById(R.id.index);
        tongcheng_textview = findViewById(R.id.tongcheng);
        message_textview = findViewById(R.id.message);
        me_textview = findViewById(R.id.me);
    }


    @Override
    public void onClick(final View v) {
        switch (v.getId()){
            case R.id.index:
                mainPager.setCurrentItem(0);
                linearLayout.getBackground().setAlpha(0);
                index_textview.setTextColor(Color.WHITE);
                tongcheng_textview.setTextColor(Color.parseColor("#9e9e9f"));
                message_textview.setTextColor(Color.parseColor("#9e9e9f"));
                me_textview.setTextColor(Color.parseColor("#9e9e9f"));
                break;
            case R.id.tongcheng:
                mainPager.setCurrentItem(1);
                linearLayout.getBackground().setAlpha(255);
                tongcheng_textview.setTextColor(Color.WHITE);
                index_textview.setTextColor(Color.parseColor("#9e9e9f"));
                message_textview.setTextColor(Color.parseColor("#9e9e9f"));
                me_textview.setTextColor(Color.parseColor("#9e9e9f"));
                break;
            case R.id.message:
                if (((App)getApplication()).getUser()==null){
                    startActivity(new Intent(MainActivity.this,VerifyLoginActivity.class));
                }else {
                    mainPager.setCurrentItem(2);
                    linearLayout.getBackground().setAlpha(255);
                    message_textview.setTextColor(Color.WHITE);
                    index_textview.setTextColor(Color.parseColor("#9e9e9f"));
                    tongcheng_textview.setTextColor(Color.parseColor("#9e9e9f"));
                    me_textview.setTextColor(Color.parseColor("#9e9e9f"));
                }
                break;
            case R.id.me:
//                进入个人主页首先需要判断是否登陆
                if (((App)getApplication()).getUser()==null){
                    startActivity(new Intent(MainActivity.this,VerifyLoginActivity.class));
                }else {
                    mainPager.setCurrentItem(3);
                    linearLayout.getBackground().setAlpha(255);
                    me_textview.setTextColor(Color.WHITE);
                    index_textview.setTextColor(Color.parseColor("#9e9e9f"));
                    tongcheng_textview.setTextColor(Color.parseColor("#9e9e9f"));
                    message_textview.setTextColor(Color.parseColor("#9e9e9f"));
                }
                break;
            case R.id.add:
                if (((App)getApplication()).getUser()==null){
                    startActivity(new Intent(MainActivity.this,VerifyLoginActivity.class));
                }else {
                    startActivity(new Intent(MainActivity.this, RecordActivity.class));
                }
            default:
                break;
        }
    }

}
