package com.joe.dramaapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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
        holder.tvName.setText(String.format(context.getResources().getString(R.string.drama_name), alDramaBean.get(position).getName()));
        holder.tvRating.setText(String.format(context.getResources().getString(R.string.drama_rating), alDramaBean.get(position).getRating().substring(0, 3)));
        holder.tvCreatedAt.setText(String.format(context.getResources().getString(R.string.drama_publish_date), alDramaBean.get(position).getCreatedAt().substring(0, 10)));
        Glide.with(context).load(alDramaBean.get(position).getThumbUrl()).into(holder.ivThumb);
    }

    @Override
    public int getItemCount() {
        if(alDramaBean != null)
        {
            return alDramaBean.size();
        }
        return 0;
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
            ivThumb = itemView.findViewById(R.id.iv_thumb);
        }
    }
}
