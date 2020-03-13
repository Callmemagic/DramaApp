package com.joe.dramaapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.joe.dramaapp.bean.DramaBean;

import java.util.ArrayList;

/**
 * author: Joe Cheng
 */
public class DramaAdapter extends RecyclerView.Adapter<DramaAdapter.DramaViewHolder> {
    Context context;
    ArrayList<DramaBean> alDramaBean;

    public DramaAdapter(Context context, ArrayList<DramaBean> alDramaBean) {
        this.context = context;
        this.alDramaBean = alDramaBean;
    }

    @NonNull
    @Override
    public DramaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.drama_item_view, parent, false);
        return new DramaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DramaViewHolder holder, int position) {
        //  TODO:
        holder.tvName.setText(alDramaBean.get(position).getName());
        holder.tvRating.setText(alDramaBean.get(position).getRating());
        holder.tvCreatedAt.setText(alDramaBean.get(position).getCreatedAt());
    }

    @Override
    public int getItemCount() {
        return alDramaBean.size();
    }

    public class DramaViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvName;
        TextView tvRating;
        TextView tvCreatedAt;
        ImageView ivThumb;

        public DramaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvCreatedAt = itemView.findViewById(R.id.tvCreatedAt);
            //TODO: 圖片
        }
    }
}
