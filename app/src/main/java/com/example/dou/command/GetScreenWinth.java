package com.example.dou.command;

import android.util.DisplayMetrics;

import androidx.fragment.app.FragmentActivity;

/**
 * author: wu
 * date: on 2019/3/7.
 * describe:
 */
public class GetScreenWinth {

    public static int getWinth(FragmentActivity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
//        int screenHeigh = dm.heightPixels;
        return screenWidth;
    }

    public static int getHeight(FragmentActivity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
//        int screenWidth = dm.widthPixels;
        int screenHeigh = dm.heightPixels;
        return screenHeigh;
    }

    public static int getPx(int dp, FragmentActivity activity) {
        final float scale = activity.getResources().getDisplayMetrics().density;
        int px = (int) (dp * scale + 0.5f);
        return px;
    }

    public static int getDp(int px, FragmentActivity activity) {
        final float scale = activity.getResources().getDisplayMetrics().density;
        //由30px转化来的dp
        int dp = (int) (px / scale + 0.5f);
        return dp;
    }
}
