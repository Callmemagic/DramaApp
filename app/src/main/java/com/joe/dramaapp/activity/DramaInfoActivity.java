package com.joe.dramaapp.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.joe.dramaapp.R;
import com.joe.dramaapp.bean.DramaBean;
import com.joe.dramaapp.databinding.ActivityDramaInfoBinding;

/**
 * author: Joe Cheng
 */
public class DramaInfoActivity extends AppCompatActivity {
    ActivityDramaInfoBinding activityDramaInfoBinding;
    DramaBean dramaBean;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDramaInfoBinding = ActivityDramaInfoBinding.inflate(getLayoutInflater());
        setContentView(activityDramaInfoBinding.getRoot());

        if(getIntent() != null)
        {
            Bundle bundle = getIntent().getExtras();
            dramaBean = (DramaBean) bundle.getSerializable("intent_drama_bean");
        }

        initView();
    }

    private void initView() {
        Glide.with(this).load(dramaBean.getThumbUrl()).into(activityDramaInfoBinding.imageView);
        activityDramaInfoBinding.tvName.setText(String.format(getString(R.string.drama_name), dramaBean.getName()));
        activityDramaInfoBinding.tvRating.setText(String.format(getString(R.string.drama_rating), dramaBean.getRating().substring(0, 3)));
        activityDramaInfoBinding.tvCreatedAt.setText(String.format(getString(R.string.drama_publish_date), dramaBean.getCreatedAt()));
        activityDramaInfoBinding.tvWatchTimes.setText(String.format(getString(R.string.drama_watch_times), dramaBean.getTotalViews()));

        activityDramaInfoBinding.ivBack.setOnClickListener(mOnClickListener);
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    @Override
    public void onResume() {
        super.onResume();
    }
}
