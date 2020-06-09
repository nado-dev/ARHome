package com.ar.arhome.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ar.arhome.R;

public class FragmentThree extends Fragment{
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

    }
}
