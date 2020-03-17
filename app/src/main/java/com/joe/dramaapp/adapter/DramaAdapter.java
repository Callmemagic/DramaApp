package com.joe.dramaapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.joe.dramaapp.Listener.OnClickItemListener;
import com.joe.dramaapp.R;
import com.joe.dramaapp.bean.DramaBean;

import java.util.ArrayList;

/**
 * author: Joe Cheng
 */
public class DramaAdapter extends RecyclerView.Adapter<DramaAdapter.DramaViewHolder> {
    Context context;
    ArrayList<DramaBean> alDramaBean;
    OnClickItemListener mListener;

    public DramaAdapter(Context context, ArrayList<DramaBean> alDramaBean, OnClickItemListener listener) {
        this.context = context;
        this.alDramaBean = alDramaBean;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public DramaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.drama_item_view, parent, false);
        return new DramaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DramaViewHolder holder, final int position) {
        holder.tvName.setText(String.format(context.getResources().getString(R.string.drama_name), alDramaBean.get(position).getName()));
        holder.tvRating.setText(String.format(context.getResources().getString(R.string.drama_rating), alDramaBean.get(position).getRating().substring(0, 3)));
        holder.tvCreatedAt.setText(String.format(context.getResources().getString(R.string.drama_publish_date), alDramaBean.get(position).getCreatedAt().substring(0, 10)));
        Glide.with(context)
                .load(alDramaBean.get(position).getThumbUrl())
                .error(R.drawable.ic_signal_cellular_connected_no_internet_0_bar_black_24dp)
                .into(holder.ivThumb);

        //點擊進入戲劇資訊
        holder.llDramaItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickDramaItem(alDramaBean.get(position));
            }
        });
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
        LinearLayout llDramaItem;

        public DramaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvCreatedAt = itemView.findViewById(R.id.tvCreatedAt);
            ivThumb = itemView.findViewById(R.id.iv_thumb);
            llDramaItem = itemView.findViewById(R.id.ll_item);
        }
    }
}
