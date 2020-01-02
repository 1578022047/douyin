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

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class VerifyLogin2Activity extends AppCompatActivity implements View.OnClickListener {

    private ImageView cancel_imageView;
    private TextView help_textView;
    private EditText verify_code_editText;
    private Button login_button;
    private TextView phone_num_textView;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_login2);

        cancel_imageView = findViewById(R.id.cancel);
        help_textView = findViewById(R.id.help);
        verify_code_editText = findViewById(R.id.verify_code);
        login_button = findViewById(R.id.login_button);
        phone_num_textView = findViewById(R.id.phone_num);

        Intent intent = getIntent();
        phone_num_textView.setText("验证码已通过短信发送至"+intent.getStringExtra("phone_num"));

        initListener();
    }

    private void initListener() {
        cancel_imageView.setOnClickListener(this);
        help_textView.setOnClickListener(this);
        verify_code_editText.setOnClickListener(this);
        login_button.setOnClickListener(this);
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.cancel:
                finish();
                break;
            case R.id.help:
                break;
            case R.id.login_button:
                Toast.makeText(this,"http://10.0.2.2/verify?code="+verify_code_editText.getText().toString(),Toast.LENGTH_LONG);
                login();
                break;
            default:
                break;
        }
    }

    private void login() {
        HttpUtil.sendGetHttpRequest("http://10.0.2.2/verify?code="+verify_code_editText.getText().toString(), new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                String result = response.body().string();
                System.out.println(result.toString());

                if (result.equals("true")){
                    intent = new Intent(VerifyLogin2Activity.this,MainActivity.class);
                    startActivity(intent);
                }
                else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(VerifyLogin2Activity.this,"验证码错误，亲输入正确验证码",Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }

}
