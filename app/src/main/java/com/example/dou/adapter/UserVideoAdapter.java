package com.example.dou.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
    int numberLike;
    public void setPlay(int play) {
        this.play = play;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        StandardGSYVideoPlayer video;
        ImageView heart;
        CircleImageView userImage;
        ImageView attention;
        TextView likeNum;
        TextView remarkNum;

        public ViewHolder(View view){
            super(view);
            video=view.findViewById(R.id.video);
            heart=view.findViewById(R.id.heart);
            userImage=view.findViewById(R.id.user_image);
            attention=view.findViewById(R.id.attention);
            likeNum=view.findViewById(R.id.likeNum);
            remarkNum=view.findViewById(R.id.remarkNum);
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
        String url=HttpUtil.host+"getLikeVideoNum";
        HttpUtil.getLikeVideoNumHttp(url,videos.get(i).getVideoId().toString(), new okhttp3.Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {

            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                try {
                    numberLike=Integer.parseInt(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                new Handler(context.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if(numberLike!=0){
                            viewHolder.likeNum.setText(String.valueOf(numberLike));
                        }

                    }
                });
            }
        });

        if(flags.get(i).isLikeFlag()){
            viewHolder.heart.setImageResource(R.drawable.redheart);
        }
        if(flags.get(i).isAttentionFlag()){
            viewHolder.attention.setVisibility(View.GONE);
        }
        if(user==null) {
            viewHolder.heart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context,VerifyLoginActivity.class));
                }
            });
        }else {
            viewHolder.heart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if(flags.get(i).isLikeFlag()){
                        numberLike--;
                        if(numberLike==0){
                            viewHolder.likeNum.setText("赞");
                        }else {
                            viewHolder.likeNum.setText(String.valueOf(numberLike));
                        }
                        flags.get(i).setLikeFlag(false);
                        viewHolder.heart.setImageResource(R.drawable.heart);
                        String url=HttpUtil.host+"cancelLikeVideo";
                        HttpUtil.CancelLikeVideoHttp(url,user.getUserId(),videos.get(i).getVideoId().toString(), new okhttp3.Callback() {
                            @Override
                            public void onFailure(final Call call, final IOException e) {

                            }

                            @Override
                            public void onResponse(final Call call, final Response response) throws IOException {

                            }
                        });
                    }else{
                        numberLike++;
                        viewHolder.likeNum.setText(String.valueOf(numberLike));
                        flags.get(i).setLikeFlag(true);
                        viewHolder.heart.setImageResource(R.drawable.redheart);
                        String url=HttpUtil.host+"likeVideo";
                        HttpUtil.likeVideoHttp(url,user.getUserId(),videos.get(i).getVideoId().toString(), new okhttp3.Callback() {
                            @Override
                            public void onFailure(final Call call, final IOException e) {

                            }

                            @Override
                            public void onResponse(final Call call, final Response response) throws IOException {

                            }
                        });
                    }
                }
            });
        }
        if(user==null) {
            viewHolder.attention.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context,VerifyLoginActivity.class));
                }
            });
        }else {
            viewHolder.attention.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    flags.get(i).setAttentionFlag(true);
                    viewHolder.attention.setVisibility(View.GONE);
                    String url=HttpUtil.host+"attentionUser";

                    HttpUtil.attentionUserHttp(url,me.getUserId(),user.getUserId(), new okhttp3.Callback() {
                        @Override
                        public void onFailure(final Call call, final IOException e) {

                        }

                        @Override
                        public void onResponse(final Call call, final Response response) throws IOException {

                        }
                    });
                }
            });
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

