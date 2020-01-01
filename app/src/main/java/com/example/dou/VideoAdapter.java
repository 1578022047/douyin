package com.example.dou;


import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private int play = 0;
    private List<String> urls;
    Context context;
    public void setPlay(int play) {
        this.play = play;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        StandardGSYVideoPlayer video;
        ImageView image;
        public ViewHolder(View view){
            super(view);
            video=view.findViewById(R.id.video);
            image=view.findViewById(R.id.image);
        }
    }

    public VideoAdapter(List<String> urls,Context context) {
        this.urls = urls;
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
        if (play == i) {
            viewHolder.video.setVisibility(View.VISIBLE);
            viewHolder.video.setLooping(true);
            viewHolder.video.setIsTouchWiget(false);
            viewHolder.video.getBackButton().setVisibility(View.GONE);
            viewHolder.video.getTitleTextView().setVisibility(View.GONE);
            viewHolder.video.getFullscreenButton().setVisibility(View.GONE);
            viewHolder.video.setDismissControlTime(1000);
            viewHolder.video.setNeedShowWifiTip(true);
            viewHolder.video.setUp(urls.get(i),true,urls.get(i));
            viewHolder.video.startPlayLogic();
            viewHolder.video.setReleaseWhenLossAudio(true);
            viewHolder.image.setVisibility(View.GONE);
        } else {
            viewHolder.image.setVisibility(View.VISIBLE);
            viewHolder.video.setVisibility(View.GONE);
            viewHolder.video.release();
        }

    }

    @Override
    public int getItemCount() {
        return urls.size();
    }


}
