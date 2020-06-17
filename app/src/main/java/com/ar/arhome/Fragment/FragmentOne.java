package com.ar.arhome.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ar.arhome.Activity.DetailActivity;
import com.ar.arhome.R;

public class FragmentOne extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentone, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        // 获取view对象的方法
        // Button button = (Button)view.findViewById(R.id.xxx);
        // TODO 按钮设置监听等
        Button btn1 = view.findViewById(R.id.fu_button_1);
        Button btn2 = view.findViewById(R.id.fu_button_2);
        Button btn3 = view.findViewById(R.id.fu_button_3);
        Button btn4 = view.findViewById(R.id.fu_button_4);
        Button btn5 = view.findViewById(R.id.fu_button_5);
        Button btn6 = view.findViewById(R.id.fu_button_6);
        Button btn7 = view.findViewById(R.id.fu_button_7);

        btn1.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getActivity(), DetailActivity.class);
            intent.putExtra("key",1);
            startActivity(intent);
        });
        btn2.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getActivity(), DetailActivity.class);
            intent.putExtra("key",2);
            startActivity(intent);
        });
        btn3.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getActivity(), DetailActivity.class);
            intent.putExtra("key",3);
            startActivity(intent);
        });
        btn4.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getActivity(), DetailActivity.class);
            intent.putExtra("key",4);
            startActivity(intent);
        });
        btn5.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getActivity(), DetailActivity.class);
            intent.putExtra("key",5);
            startActivity(intent);
        });
        btn6.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getActivity(), DetailActivity.class);
            intent.putExtra("key",6);
            startActivity(intent);
        });
        btn7.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getActivity(), DetailActivity.class);
            intent.putExtra("key",7);
            startActivity(intent);
        });
    }
}
