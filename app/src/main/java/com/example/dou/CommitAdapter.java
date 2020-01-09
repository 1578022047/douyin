package com.example.dou;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

/**
 * author: wu
 * date: on 2018/5/3.
 * describe:评论
 */

public class CommitAdapter extends RecyclerView.Adapter<CommitAdapter.MyViewHolder> {

    private Context context;
    private LayoutInflater inflater;

    public interface OnItemClickListener {
        void onItemClick(int position, String Url);
    }

    public OnItemClickListener mOnItemClickListerer;

    public void setmOnItemClickListerer(OnItemClickListener listerer) {
        this.mOnItemClickListerer = listerer;
    }

    public CommitAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_video_commit, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 15;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View itemView) {
            super(itemView);

        }
    }
}
