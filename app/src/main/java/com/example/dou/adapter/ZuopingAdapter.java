package com.example.dou.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.dou.ui.activity.UserActivity;
import com.example.dou.ui.activity.UserVideoActivity;

import java.io.Serializable;
import java.util.List;

public class ZuopingAdapter extends RecyclerView.Adapter<ZuopingAdapter.ViewHolder> {
    private User user;
    private Context context;
    private List<Video> videos;
    private List<Flag> flags;

    public ZuopingAdapter(List<Video> videos, User user,List<Flag> flags, Context context){
        this.user=user;
        this.context=context;
        this.flags=flags;
        this.videos = videos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.zuoping_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Glide.with(App.sApplication)
                .load(videos.get(position).getImageUrl())
                .into(holder.imageView);
        holder.textView.setText(videos.get(position).getLikeNum().toString());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent intent=new Intent(context, UserVideoActivity.class);
                intent.putExtra("user",user);
                intent.putExtra("videoList", (Serializable) videos);
                intent.putExtra("flagList", (Serializable) flags);
                intent.putExtra("index",position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.zuoping_image);
            textView = itemView.findViewById(R.id.zuoping_num);
        }
    }
}
