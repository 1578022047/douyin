package com.example.dou.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dou.R;
import com.example.dou.utils.HttpUtil;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class VerifyLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView cancel_imageView;
    private TextView help_textView;
    private EditText phone_num_editView;
    private Button login_button;
    private TextView login_with_password_textView;
    private Intent intent;
    private TextView register_textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_login);

        cancel_imageView = findViewById(R.id.cancel);
        help_textView = findViewById(R.id.help);
        phone_num_editView = findViewById(R.id.phone_num);
        login_button = findViewById(R.id.login_button);
        login_with_password_textView = findViewById(R.id.login_with_password);
        register_textView = findViewById(R.id.register);

        initListener();


    }

    private void initListener() {
        cancel_imageView.setOnClickListener(this);
        help_textView.setOnClickListener(this);
        phone_num_editView.setOnClickListener(this);
        login_button.setOnClickListener(this);
        login_with_password_textView.setOnClickListener(this);
        register_textView.setOnClickListener(this);
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()){
            case R.id.cancel:
                finish();
                break;
            case R.id.help:
                break;
            case R.id.login_button:
                login();
                break;
            case R.id.login_with_password:
                intent = new Intent(this,LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.register:
                intent = new Intent(this,RegisterActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void login() {

        HttpUtil.sendGetHttpRequest("http://10.0.2.2/getverify?phonenum="+phone_num_editView.getText(), new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                Snackbar.make(login_button,"网络请求失败",Snackbar.LENGTH_LONG).setAction("重新请求", new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        login();
                    }
                }).show();
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                String result = response.body().string();
                //跳转到验证码页面，通过intent传参数过去
                if (result.equals("false")){
                    runOnUiThread(new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(VerifyLoginActivity.this,"电话号码不存在",Toast.LENGTH_SHORT).show();
                        }
                    }));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Intent intent = new Intent(VerifyLoginActivity.this,VerifyLogin2Activity.class);
                    intent.putExtra("verify_code",result);
                    intent.putExtra("phone_num",phone_num_editView.getText().toString());
                    /*runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(VerifyLoginActivity.this,phone_num_editView.getText(),Toast.LENGTH_SHORT).show();
                        }
                    });*/
                    startActivity(intent);
                }

            }
        });
    }


}
