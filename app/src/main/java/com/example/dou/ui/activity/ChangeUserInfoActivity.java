package com.example.dou.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dou.App;
import com.example.dou.R;
import com.example.dou.pojo.User;

import java.security.PrivateKey;

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
        save_changeUserInfo = findViewById(R.id.changeUserInfo);

        name = findViewById(R.id.name);
        brief = findViewById(R.id.brief);
        sex = findViewById(R.id.sex);
        birthday = findViewById(R.id.birthday);

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
            case R.id.changeUserInfo:

                break;

        }
    }
}
