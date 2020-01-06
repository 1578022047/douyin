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

import com.example.dou.App;
import com.example.dou.R;
import com.example.dou.pojo.User;
import com.example.dou.utils.HttpUtil;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView cancel_imageView;
    private TextView help_textView;
    private EditText phone_num_editText;
    private EditText password_editText;
    private EditText repassword_editText;
    private Button register_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        cancel_imageView = findViewById(R.id.cancel);
        help_textView = findViewById(R.id.help);
        phone_num_editText = findViewById(R.id.phone_num);
        password_editText = findViewById(R.id.password);
        repassword_editText = findViewById(R.id.repassword);
        register_button = findViewById(R.id.register_button);

        initListener();

    }

    private void initListener() {
        cancel_imageView.setOnClickListener(this);
        help_textView.setOnClickListener(this);
        register_button.setOnClickListener(this);
    }


    @Override
    public void onClick(final View v) {
        switch (v.getId()){
            case R.id.cancel:
                finish();
                break;
            case R.id.help:
                break;
            case R.id.register_button:
                register();
                break;
            default:
                break;

        }
    }

    private void register() {
        if (password_editText.getText().toString().equals(repassword_editText.getText().toString())){
            Toast.makeText(this,"两次输入密码不一致！！！",Toast.LENGTH_SHORT).show();
//            register();
        }
        else {
            HttpUtil.sendGetHttpRequest(HttpUtil.host + "register?phonenum=" + phone_num_editText.getText() + "&password=" + password_editText.getText(), new Callback() {
                @Override
                public void onFailure(final Call call, final IOException e) {

                }

                @Override
                public void onResponse(final Call call, final Response response) throws IOException {
                    String result = response.body().string();

                    User user = new User();
                    user = new Gson().fromJson(result,User.class);
//                    返回的用户对象！！！
                    ((App)getApplication()).setUser(user);

                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);

                }
            });
        }

    }
}
