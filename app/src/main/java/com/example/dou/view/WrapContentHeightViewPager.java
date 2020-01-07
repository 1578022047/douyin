package com.example.dou.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

public class WrapContentHeightViewPager extends ViewPager {
 
    public WrapContentHeightViewPager(Context context) {
        super(context);
    }
 
    public WrapContentHeightViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
 
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = 0;
        //下面遍历所有child的高度
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec,
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            //采用最大的view的高度。
            if (h > height) {
                height = h;
            }
        }
        setMeasuredDimension(getMeasuredWidth(), height);
    }
}