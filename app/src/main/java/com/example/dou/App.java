package com.example.dou;

import android.app.Application;
import android.content.Context;

import com.example.dou.pojo.User;

/**
 * @author LLhon
 * @Project Android-Video-Editor
 * @Package com.marvhong.videoeditor
 * @Date 2018/8/21 16:00
 * @description
 */
public class App extends Application {

    public static Context sApplication;
    public User user=null;

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = getApplicationContext();
    }
}
