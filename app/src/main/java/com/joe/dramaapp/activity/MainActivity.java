package com.joe.dramaapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.joe.dramaapp.adapter.DramaAdapter;
import com.joe.dramaapp.Listener.OnClickItemListener;
import com.joe.dramaapp.R;
import com.joe.dramaapp.bean.DramaBean;
import com.joe.dramaapp.databinding.ActivityMainBinding;
import com.joe.dramaapp.db.Drama;
import com.joe.dramaapp.db.DramaDatabase;
import com.joe.dramaapp.manager.DramaManager;
import com.joe.dramaapp.util.ConstantValue;
import com.joe.dramaapp.util.CustomDialogUtility;
import com.joe.dramaapp.util.ProgressDialogUtil;
import com.joe.dramaapp.util.SharedPreferenceUtil;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.joe.dramaapp.util.ConstantValue.INTENT_KEY_DRAMA_INFO;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();
    ActivityMainBinding activityMainBinding;
    ArrayList<DramaBean> alDramaBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        //先檢查網路
        if(false == CheckNetwork())
        {
            //沒有網路就拿DB的
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ArrayList<Drama> arrayList = (ArrayList<Drama>) DramaDatabase.getInstance(MainActivity.this).dramaDao().getAll();
                    alDramaBean = new ArrayList<>();
                    for(Drama drama : arrayList)
                    {
                        DramaBean dramaBean = new DramaBean(drama.getName(), drama.getRating(),
                                drama.getCreated_at(), drama.getTotal_views());
                        alDramaBean.add(dramaBean);
                    }
                    DramaManager.getInstance().setDramaBeanList(alDramaBean);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            processAdapterViews();
                        }
                    });
                }
            }).start();
        }

        activityMainBinding.ivSearch.setOnClickListener(onClickListener);
        activityMainBinding.ivRefresh.setOnClickListener(onClickListener);
    }

    private boolean CheckNetwork() {
        if(isNetworkAvailable() == false)
        {
            CustomDialogUtility.showDialogWithOKandCancel(this, getString(R.string.error_network_unavailable),
                    getString(R.string.error_no_internet), new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            switch (which)
                            {
                                case POSITIVE:
                                    CheckNetwork();
                                    break;

                                case NEGATIVE:
                                    break;
                            }
                        }
                    });
            return false;
        }
        else
        {
            getDramaDataByOkhttp();
        }
        return true;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.ivRefresh:
                    ProgressDialogUtil.showProgressDialog(MainActivity.this, false);
                    getDramaDataByOkhttp();
                    break;

                case R.id.ivSearch:
                    GoToSearch();
                    break;

            }
        }
    };

    private void GoToSearch() {
        startActivity(new Intent(this, DramaSearchActivity.class));
    }

    private void getDramaDataByOkhttp() {
        ProgressDialogUtil.showProgressDialog(MainActivity.this, false);
        //抓戲劇資料
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(getString(R.string.drama_url)).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                ProgressDialogUtil.dismiss();
                Log.d(TAG, "[RESP]onFailure: " + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, getString(R.string.error_no_data), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String json = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "[RESP]onResponse: " + json);

                        //解析中...
                        JSONArray array = null;
                        try {
                            JSONObject obj = new JSONObject(json);
                            array = obj.getJSONArray("data");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        parseGSON(array.toString());
                    }
                });
            }
        });

    }

    private void saveToDB(final ArrayList<DramaBean> alDramaBean) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(DramaBean dramaBean : alDramaBean)
                {
                    DramaDatabase.getInstance(MainActivity.this).dramaDao()
                            .insert(new Drama(dramaBean.getDramaId(), dramaBean.getName(), dramaBean.getTotalViews(),
                                    dramaBean.getCreatedAt(), dramaBean.getThumbUrl(), dramaBean.getRating()));
                }
            }
        }).start();
    }

    private void parseGSON(String json) {
        Gson gson = new Gson();
        alDramaBean = gson.fromJson(json, new TypeToken<ArrayList<DramaBean>>(){}.getType());

        //存一份後面會用
        DramaManager.getInstance().setDramaBeanList(alDramaBean);

        if(alDramaBean != null)
        {
            //有資料就塞DB
            saveToDB(alDramaBean);
        }

        processAdapterViews();

    }

    private void processAdapterViews() {
        DramaAdapter dramaAdapter = new DramaAdapter(this, alDramaBean, onClickItemListener);
        activityMainBinding.rvDramalist.setAdapter(dramaAdapter);
        activityMainBinding.rvDramalist.setHasFixedSize(true);
        activityMainBinding.rvDramalist.setLayoutManager(new LinearLayoutManager(this));

        ProgressDialogUtil.dismiss();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    OnClickItemListener onClickItemListener = new OnClickItemListener() {
        @Override
        public void onClickDramaItem(DramaBean bean) {
            GoToDramaInfo(bean);
        }
    };

    private void GoToDramaInfo(DramaBean bean) {
        Intent intent = new Intent(this, DramaInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(INTENT_KEY_DRAMA_INFO, bean);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
