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
import com.example.dou.ui.activity.UserVideoActivity;
import com.example.dou.ui.activity.VerifyLoginActivity;
import com.example.dou.utils.HttpUtil;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

public class UserVideoAdapter extends RecyclerView.Adapter<UserVideoAdapter.ViewHolder> {
    User user;
    User me;
    List<Flag> flags;
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
        CircleImageView userImage;
        ImageView attention;

        public ViewHolder(View view){
            super(view);
            video=view.findViewById(R.id.video);
            heart=view.findViewById(R.id.heart);
            userImage=view.findViewById(R.id.user_image);
            attention=view.findViewById(R.id.attention);
        }
    }

    public UserVideoAdapter(List<Video> videos, User user ,List<Flag> flags,Context context) {
        this.videos=videos;
        this.user=user;
        this.context=context;
        this.flags=flags;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.vedio_item,viewGroup,false);
        ViewHolder holder=new ViewHolder(view);
        me=((App)context.getApplicationContext()).getUser();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if(me==null) {
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
                        String url=HttpUtil.host+"cancelLikeVideo";
                        viewHolder.heart.setImageResource(R.drawable.heart);
                        flags.get(i).setLikeFlag(false);
                        HttpUtil.CancellikeVideoHttp(url,me.getUserId(),videos.get(i).getVideoId().toString(), new okhttp3.Callback() {
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
                        String url=HttpUtil.host+"likeVideo";
                        viewHolder.heart.setImageResource(R.drawable.redheart);
                        flags.get(i).setLikeFlag(true);
                        HttpUtil.likeVideoHttp(url,me.getUserId(),videos.get(i).getVideoId().toString(), new okhttp3.Callback() {
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
        if(me==null){
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
                        String url=HttpUtil.host+"attentionUser";
                        HttpUtil.attentionUserHttp(url,me.getUserId(),user.getUserId(), new okhttp3.Callback() {
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
        Glide.with(context).load(user.getImageUrl())
                .into(viewHolder.userImage);
        viewHolder.userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ((UserVideoActivity)context).finish();
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

