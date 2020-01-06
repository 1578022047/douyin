package com.example.dou.adapter;

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
import com.example.dou.pojo.Video;

import java.util.List;

public class ZuopingAdapter extends RecyclerView.Adapter<ZuopingAdapter.ViewHolder> {

    private List<Video> videos;
    public ZuopingAdapter(List<Video> videos){
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
