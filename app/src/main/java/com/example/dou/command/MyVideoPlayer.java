package com.example.dou.command;

import android.content.Context;
import android.util.AttributeSet;

import cn.jzvd.JZVideoPlayerStandard;

/**
 * author: wu
 * date: on 2019/3/7.
 * describe:
 */
public class MyVideoPlayer extends JZVideoPlayerStandard {

    public interface OnItemClickListener {
        void onItemClick();
    }

    public OnItemClickListener myFinishListerer;

    public void setFinishListerer(OnItemClickListener listerer) {
        this.myFinishListerer = listerer;
    }
    public MyVideoPlayer(Context context) {
        super(context);
    }

    public MyVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onAutoCompletion() {
        super.onAutoCompletion();
        myFinishListerer.onItemClick();
    }
}
