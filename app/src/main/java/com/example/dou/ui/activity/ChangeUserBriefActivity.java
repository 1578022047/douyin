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

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ChangeUserBriefActivity extends AppCompatActivity {

    private TextView save_changeBrief;
    private EditText text_changeBrief;
    private ImageView cancle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_brief);

        save_changeBrief = findViewById(R.id.save_changeBrief);
        text_changeBrief = findViewById(R.id.text_changeBrief);
        cancle = findViewById(R.id.cancel);

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                finish();
            }
        });
        save_changeBrief.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
//                当不为空是设置修改文本
                if (!text_changeBrief.getText().toString().equals("")){
                    User user = ((App)getApplication()).getUser();
                    Intent intent = new Intent();
                    intent.putExtra("brief",text_changeBrief.getText().toString());
                    setResult(RESULT_OK,intent);

                    String url = HttpUtil.host+"changeUserBrief?userId="+user.getUserId()+"&brief="+text_changeBrief.getText().toString();
                    HttpUtil.sendGetHttpRequest(url, new Callback() {
                        @Override
                        public void onFailure(final Call call, final IOException e) {

                        }

                        @Override
                        public void onResponse(final Call call, final Response response) throws IOException {
                            finish();
                        }
                    });
                }
            }
        });

    }
}
