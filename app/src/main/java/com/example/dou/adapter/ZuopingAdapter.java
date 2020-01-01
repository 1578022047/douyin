package com.example.dou.adapter;

import android.service.quicksettings.TileService;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MethodCallsLogger;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dou.R;
import com.example.dou.recycler_pojo.ZuopingPojo;

import java.util.List;

public class ZuopingAdapter extends RecyclerView.Adapter<ZuopingAdapter.ViewHolder> {

    private List<ZuopingPojo> list;
    public ZuopingAdapter(List<ZuopingPojo> list){
        this.list = list;
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
        ZuopingPojo zuopingPojo = list.get(position);
        holder.imageView.setImageResource(zuopingPojo.getImage());
        holder.textView.setText(zuopingPojo.getNum());
    }

    @Override
    public int getItemCount() {
        return list.size();
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
