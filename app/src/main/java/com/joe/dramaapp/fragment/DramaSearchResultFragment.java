package com.joe.dramaapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.joe.dramaapp.Listener.OnResultFragmentReadyListener;
import com.joe.dramaapp.util.ConstantValue;
import com.joe.dramaapp.Listener.OnClickItemListener;
import com.joe.dramaapp.Listener.OnInputKeywordListener;
import com.joe.dramaapp.R;
import com.joe.dramaapp.activity.DramaInfoActivity;
import com.joe.dramaapp.activity.DramaSearchActivity;
import com.joe.dramaapp.adapter.DramaSearchAdapter;
import com.joe.dramaapp.bean.DramaBean;
import com.joe.dramaapp.databinding.ActivityDramaSearchResultBinding;
import com.joe.dramaapp.manager.DramaManager;
import com.joe.dramaapp.util.SharedPreferenceUtil;

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
    static OnResultFragmentReadyListener mOnResultFragmentReadyListener;

    public static DramaSearchResultFragment getInstance(OnResultFragmentReadyListener onResultFragmentReadyListener) {
        if(instance == null)
        {
            instance = new DramaSearchResultFragment();
            mOnResultFragmentReadyListener = onResultFragmentReadyListener;
        }
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((DramaSearchActivity)getActivity()).updateData(onInputKeywordListener);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dramaSearchResultBinding = ActivityDramaSearchResultBinding.inflate(getLayoutInflater(), container, false);

        dramaSearchAdapter = new DramaSearchAdapter(context, onClickItemListener);
        dramaSearchResultBinding.rvSearchResult.setAdapter(dramaSearchAdapter);
        dramaSearchResultBinding.rvSearchResult.setLayoutManager(new LinearLayoutManager(context));
        dramaSearchResultBinding.btnConnectAuthor.setOnClickListener(mOnClickListener);

        mOnResultFragmentReadyListener.OnFragmentReady();

        return dramaSearchResultBinding.getRoot();
    }

    OnInputKeywordListener onInputKeywordListener = new OnInputKeywordListener() {
        @Override
        public void OnInputKeywordForSearch(boolean bHasKeyword) {
        alDramaBean = DramaManager.getInstance().getAlFilteredDramaBean();
        dramaSearchAdapter.updateResult(alDramaBean);
        dramaSearchAdapter.notifyDataSetChanged();

            //若沒有關鍵字就沒有資料
            if(!bHasKeyword)
            {
                dramaSearchResultBinding.rlNodata.setVisibility(View.VISIBLE);
                dramaSearchResultBinding.rlResult.setVisibility(View.GONE);
                return;
            }

            if((alDramaBean == null || alDramaBean.size() == 0))
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

    OnClickItemListener onClickItemListener = new OnClickItemListener() {
        @Override
        public void onClickDramaItem(DramaBean bean) {
            Intent intent = new Intent(getContext(), DramaInfoActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(ConstantValue.INTENT_KEY_DRAMA_INFO, bean);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        Log.d("onPause()", "onPause: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("onDestroy()", "onDestroy: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("onDestroyView()", "onDestroyView: ");
    }

    @Override
    public void onDetach() {
        SharedPreferenceUtil.getInstance(getContext()).writePref(ConstantValue.PREF_DRAMA, ConstantValue.PREF_KEY_KEYWORD, null);
        super.onDetach();
        Log.d("onDetach()", "onDetach: ");

    }
}
