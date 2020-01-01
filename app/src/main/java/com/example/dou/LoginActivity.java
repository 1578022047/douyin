package com.example.dou;


import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dou.utils.HttpUtil;


import java.io.IOException;

import okhttp3.Callback;
import okhttp3.Response;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView cancel_imageView;
    private TextView help_textView;
    private EditText phone_num_editText;
    private EditText password_editText;
    private Button login_button;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        cancel_imageView = findViewById(R.id.cancel);
        help_textView = findViewById(R.id.help);
        phone_num_editText = findViewById(R.id.phone_num);
        password_editText = findViewById(R.id.password);
        login_button = findViewById(R.id.login_button);

        initListener();
    }

    private void initListener() {
        cancel_imageView.setOnClickListener(this);
        help_textView.setOnClickListener(this);
        login_button.setOnClickListener(this);
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()){
            case R.id.cancel:
                finish();
                break;
            case R.id.help:
                break;
            case R.id.verify_code:
                break;
            case R.id.login_button:
                login();
                break;
            default:
                break;
        }
    }

    private void login() {
        HttpUtil.sendGetHttpRequest("http://10.0.2.2/login?phonenum="+phone_num_editText.getText()+"&password="+password_editText.getText(), new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                String result = response.body().string();
                if (result.equals("true")){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }else {
                    runOnUiThread(new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this,"电话号码或密码错误",Toast.LENGTH_SHORT).show();
                        }
                    }));
                }
            }
        });
    }

}