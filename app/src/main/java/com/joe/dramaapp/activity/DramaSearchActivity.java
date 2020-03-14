package com.joe.dramaapp.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.joe.dramaapp.Listener.OnInputKeyword;
import com.joe.dramaapp.R;
import com.joe.dramaapp.bean.DramaBean;
import com.joe.dramaapp.databinding.ActivityDramaSearchBinding;
import com.joe.dramaapp.fragment.DramaSearchResultFragment;
import com.joe.dramaapp.manager.DramaManager;

import java.util.ArrayList;

/**
 * author: Joe Cheng
 */
public class DramaSearchActivity extends AppCompatActivity {
    ActivityDramaSearchBinding activityDramaSearchBinding;
    OnInputKeyword onInputKeywordListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDramaSearchBinding = ActivityDramaSearchBinding.inflate(getLayoutInflater());
        setContentView(activityDramaSearchBinding.getRoot());

        initViews();
    }

    private void initViews() {
        activityDramaSearchBinding.ivBack.setOnClickListener(mOnClickListener);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //根據關鍵字過濾出查詢結果
                if(s.toString().length() != 0)
                {
                    FilterResultByKeyword(s);
                }
                else
                {
                    //顯示沒有結果
                    onInputKeywordListener.OnInputKeywordForSearch(false);
                }
            }
        };

        activityDramaSearchBinding.etDramaName.addTextChangedListener(textWatcher);
        activityDramaSearchBinding.ivClear.setOnClickListener(mOnClickListener);

        DramaSearchResultFragment dramaSearchResultFragment = DramaSearchResultFragment.getInstance();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fl_result, dramaSearchResultFragment);
        fragmentTransaction.commit();


    }

    private void FilterResultByKeyword(Editable keyword) {
        ArrayList<DramaBean> filteredList = new ArrayList<>();
        String strKeyword = keyword.toString();
        ArrayList<DramaBean> alDrama =  DramaManager.getInstance().getDramaBeanList();
        for(DramaBean bean : alDrama)
        {
            if(bean.getName().contains(strKeyword))
            {
                filteredList.add(bean);
            }
        }
        DramaManager.getInstance().setAlFilteredDramaBean(filteredList);

        //通知Framgent的列表更新結果
        onInputKeywordListener.OnInputKeywordForSearch(true);
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.ivBack:
                    finish();
                    break;
                case R.id.iv_clear:
                    activityDramaSearchBinding.etDramaName.setText("");
                    break;
            }
        }
    };

    public void updateDate(OnInputKeyword onInputKeywordListener)
    {
        this.onInputKeywordListener = onInputKeywordListener;
    }
}
