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

import com.ar.arhome.Activity.ARFrameActivity;
import com.ar.arhome.R;

public class FragmentTwo extends Fragment{
    Button ar_preview;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmenttwo, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        ar_preview = view.findViewById(R.id.ar_preview);
        ar_preview.setOnClickListener((v)->{
            Intent intent = new Intent(view.getContext(), ARFrameActivity.class);
            startActivity(intent);
        });
    }
}
