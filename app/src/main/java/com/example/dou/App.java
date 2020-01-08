package com.example.dou;

import android.app.Application;
import android.content.Context;

import com.example.dou.pojo.Flag;
import com.example.dou.pojo.User;

import java.util.ArrayList;
import java.util.List;

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
    public List<Flag> flags=new ArrayList<>();

    public List<Flag> getFlags() {
        return flags;
    }

    public void setFlags(final List<Flag> flags) {
        this.flags = flags;
    }

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
