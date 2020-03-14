package com.joe.dramaapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.joe.dramaapp.ConstantValue;
import com.joe.dramaapp.Listener.OnInputKeyword;
import com.joe.dramaapp.R;
import com.joe.dramaapp.activity.DramaSearchActivity;
import com.joe.dramaapp.adapter.DramaSearchAdapter;
import com.joe.dramaapp.bean.DramaBean;
import com.joe.dramaapp.databinding.ActivityDramaSearchResultBinding;
import com.joe.dramaapp.manager.DramaManager;

import java.util.ArrayList;

/**
 * author: Joe Cheng
 */
public class DramaSearchResultFragment extends Fragment {
    private static DramaSearchResultFragment instance;
    ActivityDramaSearchResultBinding dramaSearchResultBinding;
    ArrayList<DramaBean> alDramaBean;
    DramaSearchAdapter dramaSearchAdapter;
    Context context;

    public static DramaSearchResultFragment getInstance() {
        if(instance == null)
        {
            instance = new DramaSearchResultFragment();
        }
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dramaSearchResultBinding = ActivityDramaSearchResultBinding.inflate(getLayoutInflater(), container, false);

        dramaSearchAdapter = new DramaSearchAdapter(context, alDramaBean);
        dramaSearchResultBinding.rvSearchResult.setAdapter(dramaSearchAdapter);
        dramaSearchResultBinding.rvSearchResult.setLayoutManager(new LinearLayoutManager(context));
        dramaSearchResultBinding.btnConnectAuthor.setOnClickListener(mOnClickListener);

        ((DramaSearchActivity)getActivity()).updateDate(onInputKeywordListener);

        return dramaSearchResultBinding.getRoot();
    }

    OnInputKeyword onInputKeywordListener = new OnInputKeyword() {
        @Override
        public void OnInputKeywordForSearch(boolean bHasKeyword) {
        ArrayList<DramaBean> dramaBeans = DramaManager.getInstance().getAlFilteredDramaBean();
        dramaSearchAdapter.updateResult(dramaBeans);
        dramaSearchAdapter.notifyDataSetChanged();

            //若沒有關鍵字就沒有資料
            if(!bHasKeyword)
            {
                dramaSearchResultBinding.rlNodata.setVisibility(View.VISIBLE);
                dramaSearchResultBinding.rlResult.setVisibility(View.GONE);
                return;
            }

            if((dramaBeans == null || dramaBeans.size() == 0))
            {
                dramaSearchResultBinding.rlNodata.setVisibility(View.VISIBLE);
                dramaSearchResultBinding.rlResult.setVisibility(View.GONE);
            }
            else {
                dramaSearchResultBinding.rlNodata.setVisibility(View.GONE);
                dramaSearchResultBinding.rlResult.setVisibility(View.VISIBLE);
            }
        }
    };

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.btn_connect_author:
                    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto",
                            ConstantValue.EMAIL, null));
                    intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_title));
                    intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.email_text));
                    startActivity(Intent.createChooser(intent, "SEND...."));
                    break;
            }
        }
    };

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
