package com.ar.arhome.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ar.arhome.Activity.LoginActivity;
import com.ar.arhome.Activity.MyFavoriteActivity;
import com.ar.arhome.Domain.LoginUserInfo;
import com.ar.arhome.R;
import com.ar.arhome.Util.ApiRequest;
import com.ar.arhome.Util.HttpUtil;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Response;

public class FragmentThree extends Fragment{
    private SharedPreferences userInfo;
    private String token;
    private Button myFavorite;
    private Button logout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentthree, container, false);
        userInfo = LoginUserInfo.loadLoginUserInfo(getContext());
        this.token = userInfo.getString("Token","undefined");
        initView(view);
        return view;
    }

    private void initView(View view) {
        // 获取view对象的方法
        // Button button = (Button)view.findViewById(R.id.xxx);
        // TODO 按钮设置监听等
        myFavorite = view.findViewById(R.id.myfavorite);
        logout = view.findViewById(R.id.logout);
        // 退出登录功能实现
        logout.setOnClickListener(v->{
            final CountDownLatch latch = new CountDownLatch(1);
            // 同步服务器的退出状态
            Headers headers =new Headers.Builder()
                    .add("Content-Type", "application/json;charset=UTF-8")
                    .add("Content-Type", "application/x-www-form-urlencoded")
                    .add("token",token)
                    .build();
            HttpUtil.sendOkHttpRequestByPostMethodWithHeader(ApiRequest.baseUrl + "user/logout",
                    headers, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            latch.countDown();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String successString = "退出成功";
                            String res = response.body() != null ? response.body().string() : "empty body";
                            int index = res.indexOf(successString);
                            if (index == -1){
                                Looper.prepare();
                                Log.d("OKHTTP", "退出失败");
                                Toast.makeText(getContext(), "退出失败:"+res,Toast.LENGTH_SHORT).show();
                                Looper.loop();
                                return;
                            }
                            latch.countDown();
                        }
                    });
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LoginUserInfo.deleteLoginInfo(getContext());
            Toast.makeText(getContext(), "退出登录成功", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        });

        myFavorite.setOnClickListener(v->{
            Intent intent = new Intent(getActivity(), MyFavoriteActivity.class);
            startActivity(intent);
        });
    }
}
