package com.example.dou.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dou.R;
import com.example.dou.RecyclerViewPageChangeListenerHelper;
import com.example.dou.VideoAdapter;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.ArrayList;
import java.util.List;

public class AttentionFragment extends Fragment {
    RecyclerView videoList;
    List<String> urls;
    LinearLayoutManager layoutManager;
    int curPosition=0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.attention_fragment,null);
        videoList=view.findViewById(R.id.videoList);
        initDate();
        initView();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private void initDate() {
        urls = new ArrayList<>();
        urls.add("http://www.liuyishou.site/video/1.mp4");
        urls.add("http://www.liuyishou.site/video/2.mp4");
        urls.add("http://www.liuyishou.site/video/3.mp4");
        urls.add("http://www.liuyishou.site/video/4.mp4");
        urls.add("http://www.liuyishou.site/video/5.mp4");
    }

    private void initView() {
        final PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(videoList);
        layoutManager=new LinearLayoutManager(getContext());
        videoList.setLayoutManager(layoutManager);
        final VideoAdapter adapter=new VideoAdapter(urls,getContext());
        videoList.setAdapter(adapter);
        videoList.getItemAnimator().setChangeDuration(0);
        videoList.setItemAnimator(null);
        videoList.addOnScrollListener(new RecyclerViewPageChangeListenerHelper(pagerSnapHelper,
                new RecyclerViewPageChangeListenerHelper.OnPageChangeListener() {


                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        adapter.setPlay(position);
                        if(urls.size()-position<=3){
                            urls.add("http://www.liuyishou.site/video/"+String.valueOf(position+3)+".mp4");
                            adapter.notifyDataSetChanged();
                            curPosition=position;
                        }
                    }
                }));
    }

    @Override
    public void onPause() {
        super.onPause();
        StandardGSYVideoPlayer videoPlayer=videoList.getChildAt(curPosition).findViewById(R.id.video);
        videoPlayer.onVideoPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
