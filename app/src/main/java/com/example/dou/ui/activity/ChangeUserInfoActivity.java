package com.example.dou.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dou.App;
import com.example.dou.R;
import com.example.dou.pojo.User;
import com.example.dou.utils.HttpUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.security.PrivateKey;

import javax.net.ssl.HostnameVerifier;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class ChangeUserInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView cancle;
    private TextView save_changeUserInfo;
    private EditText name;
    private EditText brief;
    private EditText sex;
    private EditText birthday;

    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_info);

        init();
        initListener();

    }

    private void init() {
        cancle = findViewById(R.id.cancel);
        save_changeUserInfo = findViewById(R.id.save_changeUserInfo);

        name = findViewById(R.id.name);
        brief = findViewById(R.id.brief);
        sex = findViewById(R.id.sex);
        birthday = findViewById(R.id.birthday);

    }

    private void initListener() {
        cancle.setOnClickListener(this);
        save_changeUserInfo.setOnClickListener(this);
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()){
            case R.id.cancel:
                finish();
                break;
            case R.id.save_changeUserInfo:
                changeInfo();
                break;
        }
    }

    private void changeInfo() {
//        该变全局app
        user = ((App)getApplication()).getUser();
        user.setName(name.getText().toString());
        user.setBrief(brief.getText().toString());
        if (sex.getText().toString().equals("男")){
            user.setSex(0);
        }
        else {
            user.setSex(1);
        }
        user.setBirthday(birthday.getText().toString());
        Intent intent = new Intent();
        setResult(RESULT_OK,intent);
//        通过http改变数据库数据
        String userJson = new Gson().toJson(user);
        String url = HttpUtil.host+"changeUserInfo?user="+userJson;
//        json数据访问出错，应该使用post方法把
        System.out.println("changeUserInfo:"+url);
        HttpUtil.sendGetHttpRequest(url, new okhttp3.Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {

            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {

            }
        });
        finish();
    }
}
