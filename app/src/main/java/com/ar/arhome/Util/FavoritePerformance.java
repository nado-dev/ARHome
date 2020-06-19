package com.ar.arhome.Util;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.ar.arhome.Domain.FavInfo;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Response;

/**
 * 收藏/取消收藏 网络行为
 * 2020.6.19 @fang
 * type:0 / 1 , 0：取消收藏 1：收藏
 *
 * updateToMyFavoriteCollection(int type) 向服务器同步收藏状态
 * refreshListFromBackend() 获取服务器上所有收藏列表
 */
public class FavoritePerformance {
    private Context context;
    private String token;
    private int model_id;
    private int post_id;
    private int type;
    private int sign;
    private HashMap<String, Integer> availableMap;

    public FavoritePerformance(Context context, String token, int model_id){
        this.context = context;
        this.token = token;
        this.model_id = model_id;
        // 映射id
        changeID();
    }

    private void changeID() {
       post_id = model_id + 35;
    }

    public void updateToMyFavoriteCollection(int type){
        final CountDownLatch latch = new CountDownLatch(1);
        this.type = type;
        // 表单
        HashMap<String,String> paramsMap=new HashMap<>();
        paramsMap.put("post_id",String.valueOf(post_id));
        paramsMap.put("type",String.valueOf(type));
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : paramsMap.keySet()) {
            //追加表单信息
            builder.add(key, paramsMap.get(key));
        }
        // Header
        Headers headers =new Headers.Builder()
                .add("Content-Type", "application/json;charset=UTF-8")
                .add("Content-Type", "application/x-www-form-urlencoded")
                .add("token",token)
                .build();

        // post请求
        HttpUtil.sendOkHttpRequestByPostMethodWithHeaderAndBody(
                ApiRequest.baseUrl + "support",
                builder.build(),
                headers,
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        latch.countDown();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        latch.countDown();
                        String res = response.body() != null ? response.body().string() : "empty body";
                        resultCheck(res);
                    }
                }
        );
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void resultCheck(String res) {
        String success = "ok";
        int result = res.indexOf(success);
        if (result == -1){

                //            Log.d("OKHTTP", res);
                Looper.prepare();
                Log.d("OKHTTP", "收藏信息上传失败");
                Toast.makeText(context, "收藏信息上传失败",Toast.LENGTH_SHORT).show();
                Looper.loop();

        }
    }

    public HashMap<String, Integer> refreshListFromBackend(){
        final CountDownLatch latch = new CountDownLatch(1);
        availableMap = new HashMap<>();
            // 表单
            HashMap<String,String> paramsMap=new HashMap<>();
            paramsMap.put("post_id",String.valueOf(1));
            paramsMap.put("type",String.valueOf(1));
            FormBody.Builder builder = new FormBody.Builder();
            for (String key : paramsMap.keySet()) {
                //追加表单信息
                builder.add(key, paramsMap.get(key));
            }
            // Header
            Headers headers =new Headers.Builder()
                    .add("Content-Type", "application/json;charset=UTF-8")
                    .add("Content-Type", "application/x-www-form-urlencoded")
                    .add("token",token)
                    .build();

            // post请求
            HttpUtil.sendOkHttpRequestByPostMethodWithHeaderAndBody(
                    ApiRequest.baseUrl + "support/list",
                    builder.build(),
                    headers,
                    new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            latch.countDown();
                        }

                        @Override

                        public void onResponse(Call call, Response response) throws IOException {
                            String res = response.body() != null ? response.body().string() : "empty body";
                           // Gson
                            loadData(res);
                            latch.countDown();
                        }
                    }
            );
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return availableMap;
    }

    private void loadData(String res) {
//        Log.d("INFO", res);
        Gson gson = new Gson();
        FavInfo favInfo = gson.fromJson(res, FavInfo.class);
        FavInfo.DataBean data = favInfo.getData();
        List<FavInfo.DataBean.ListBean> list = data.getList();
        for (FavInfo.DataBean.ListBean item : list){
            if (item.getPostId() > 35 && item.getPostId() < 43){
//                Log.d("INFO POSTID", String.valueOf(item.getPostId()));
                availableMap.put(item.getCreateTime(),item.getPostId() - 35);
            }
        }
    }
}
