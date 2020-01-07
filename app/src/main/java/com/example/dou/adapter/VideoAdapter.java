package com.example.dou.adapter;


import android.content.Context;

import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.dou.App;
import com.example.dou.R;
import com.example.dou.pojo.Flag;
import com.example.dou.pojo.User;
import com.example.dou.pojo.Video;
import com.example.dou.ui.activity.MainActivity;
import com.example.dou.ui.activity.UserActivity;
import com.example.dou.ui.activity.VerifyLoginActivity;
import com.example.dou.utils.HttpUtil;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private List<User> users;
    private int play = 0;
    private List<Video> videos;
    Context context;
    private User user;
    List<Flag> flags;

    public void setPlay(int play) {
        this.play = play;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        StandardGSYVideoPlayer video;
        ImageView heart;
        CircleImageView userImage;
        ImageView attention;


        public ViewHolder(View view) {
            super(view);
            video = view.findViewById(R.id.video);
            heart = view.findViewById(R.id.heart);
            userImage = view.findViewById(R.id.user_image);
            attention = view.findViewById(R.id.attention);
        }
    }

    public VideoAdapter(List<Video> videos, List<User> users, List<Flag> flags, Context context) {
        this.users = users;
        this.videos = videos;
        this.flags=flags;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.vedio_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        user = ((App) context.getApplicationContext()).getUser();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if(user==null) {
            viewHolder.heart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context,VerifyLoginActivity.class));
                }
            });
        }else {
            if (flags.get(i).isLikeFlag()) {
                viewHolder.heart.setImageResource(R.drawable.redheart);
                viewHolder.heart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url="";
                        viewHolder.heart.setImageResource(R.drawable.heart);
                        flags.get(i).setLikeFlag(false);
                        HttpUtil.CancellikeVideoHttp(url,user.getUserId(),videos.get(i).getVideoId().toString(), new okhttp3.Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {

                            }
                        });
                    }
                });
            } else {
                viewHolder.heart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url="";
                        viewHolder.heart.setImageResource(R.drawable.redheart);
                        flags.get(i).setLikeFlag(true);
                        HttpUtil.likeVideoHttp(url,user.getUserId(),videos.get(i).getVideoId().toString(), new okhttp3.Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {

                            }
                        });
                    }
                });
            }
        }
        if(user==null){
            viewHolder.attention.setVisibility(View.VISIBLE);
            viewHolder.attention.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context,VerifyLoginActivity.class));
                }
            });
        }else{
            if(flags.get(i).isAttentionFlag()){
                viewHolder.attention.setVisibility(View.GONE);
            }else{
                viewHolder.attention.setVisibility(View.VISIBLE);
                viewHolder.attention.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url="";
                        HttpUtil.attentionUserHttp(url,user.getUserId(),users.get(i).getUserId(), new okhttp3.Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        viewHolder.attention.setVisibility(View.GONE);
                                        flags.get(i).setAttentionFlag(true);
                                    }
                                });
                            }
                        });
                    }
                });
            }
        }



        viewHolder.userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent intent = new Intent(context, UserActivity.class);
                intent.putExtra("userId", users.get(i).getUserId());
                context.startActivity(intent);
            }
        });
        Glide.with(context).load(users.get(i).getImageUrl())
                .into(viewHolder.userImage);
        if (play == i) {
            viewHolder.video.setVisibility(View.VISIBLE);
            viewHolder.video.setLooping(true);
            viewHolder.video.setIsTouchWiget(false);
            viewHolder.video.getBackButton().setVisibility(View.GONE);
            viewHolder.video.getTitleTextView().setVisibility(View.GONE);
            viewHolder.video.getFullscreenButton().setVisibility(View.GONE);
            viewHolder.video.setDismissControlTime(0);
            viewHolder.video.setNeedShowWifiTip(true);
            viewHolder.video.setUp(videos.get(i).getVideoUrl(), true, videos.get(i).getVideoUrl());
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
