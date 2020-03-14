package com.joe.dramaapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.joe.dramaapp.R;
import com.joe.dramaapp.bean.DramaBean;
import com.joe.dramaapp.manager.DramaManager;

import java.util.ArrayList;

/**
 * author: Joe Cheng
 */
public class DramaSearchAdapter extends RecyclerView.Adapter<DramaSearchAdapter.DramaViewHolder> {
    Context context;
    ArrayList<DramaBean> alDramaBean;
    public DramaSearchAdapter(Context context, ArrayList<DramaBean> alDramaBean) {
        this.context = context;
        this.alDramaBean = alDramaBean;
    }

    @NonNull
    @Override
    public DramaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DramaViewHolder(LayoutInflater.from(context).inflate(R.layout.item_result, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DramaViewHolder holder, int position) {
        if(alDramaBean != null && alDramaBean.size() > 0)
        {
            holder.tvName.setText(alDramaBean.get(position).getName());
            Glide.with(context).load(alDramaBean.get(position).getThumbUrl()).into(holder.ivThumb);
        }
    }

    @Override
    public int getItemCount() {
        if(alDramaBean != null)
        {
            return alDramaBean.size();
        }
        return 0;
    }

    public class DramaViewHolder extends RecyclerView.ViewHolder {
        ImageView ivThumb;
        TextView tvName;

        public DramaViewHolder(@NonNull View itemView) {
            super(itemView);
            ivThumb = itemView.findViewById(R.id.iv_thumb);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }

    public void updateResult(ArrayList<DramaBean> alDramaBean)
    {
        this.alDramaBean = alDramaBean;
    }

}
