package com.joe.dramaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.joe.dramaapp.bean.DramaBean;
import com.joe.dramaapp.databinding.ActivityMainBinding;

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

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();
    ActivityMainBinding activityMainBinding;
    ArrayList<DramaBean> alDramaBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        //抓戲劇資料
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(getString(R.string.drama_url)).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, "[RESP]onFailure: " + e.getMessage());
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

    private void parseGSON(String json) {
        Gson gson = new Gson();
        alDramaBean = gson.fromJson(json, new TypeToken<ArrayList<DramaBean>>(){}.getType());

        DramaAdapter dramaAdapter = new DramaAdapter(this, alDramaBean);
        activityMainBinding.rvDramalist.setAdapter(dramaAdapter);
        activityMainBinding.rvDramalist.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
