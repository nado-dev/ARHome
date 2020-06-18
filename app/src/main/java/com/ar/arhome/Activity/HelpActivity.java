package com.ar.arhome.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.ar.arhome.R;

public class HelpActivity extends AppCompatActivity {
    private Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        back = findViewById(R.id.back);
        back.setOnClickListener((v)->{
            finish();
        });
    }
}
