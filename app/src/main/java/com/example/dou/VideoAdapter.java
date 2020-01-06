package com.example.dou;


import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.dou.pojo.Video;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private int play = 0;
    private List<Video> videos;
    Context context;
    public void setPlay(int play) {
        this.play = play;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        StandardGSYVideoPlayer video;
        ImageView heart;
        public ViewHolder(View view){
            super(view);
            video=view.findViewById(R.id.video);
            heart=view.findViewById(R.id.heart);
        }
    }

    public VideoAdapter(List<Video> videos,Context context) {
        this.videos=videos;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.vedio_item,viewGroup,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.heart.setImageResource(R.drawable.redheart);
            }
        });
        if (play == i) {
            viewHolder.video.setVisibility(View.VISIBLE);
            viewHolder.video.setLooping(true);
            viewHolder.video.setIsTouchWiget(false);
            viewHolder.video.getBackButton().setVisibility(View.GONE);
            viewHolder.video.getTitleTextView().setVisibility(View.GONE);
            viewHolder.video.getFullscreenButton().setVisibility(View.GONE);
            viewHolder.video.setDismissControlTime(0);
            viewHolder.video.setNeedShowWifiTip(true);
            viewHolder.video.setUp(videos.get(i).getVideoUrl(),true,videos.get(i).getVideoUrl());
            viewHolder.video.startPlayLogic();
            viewHolder.video.setReleaseWhenLossAudio(true);
        } else {
            viewHolder.video.setVisibility(View.GONE);
            viewHolder.video.release();
        }
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }


}
