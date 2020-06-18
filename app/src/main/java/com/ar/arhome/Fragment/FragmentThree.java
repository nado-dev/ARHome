package com.ar.arhome.Fragment;

import android.content.Intent;
import android.os.Bundle;
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

public class FragmentThree extends Fragment{
    private Button myFavorite;
    private Button logout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentthree, container, false);
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
