package com.example.dou.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dou.R;

public class ChangeUserInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView cancle;
    private TextView save_changeUserInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_info);

        init();
        initListener();

    }

    private void initListener() {
        cancle.setOnClickListener(this);
        save_changeUserInfo.setOnClickListener(this);
    }

    private void init() {
        cancle = findViewById(R.id.cancel);
        save_changeUserInfo = findViewById(R.id.changeUserInfo);
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
