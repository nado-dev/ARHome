package com.ar.arhome.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ar.arhome.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ImageButton btn_return = findViewById(R.id.btn_return);
        TextView text_title = findViewById(R.id.detail_text_title);
        ImageView image = findViewById(R.id.detail_image);
        TextView text_content = findViewById(R.id.detail_text_content);
        //仅
        Button btn_save = findViewById(R.id.btn_save);
        Button btn_show = findViewById(R.id.btn_show);

        btn_return.setOnClickListener(v -> finish());

        int key = getIntent().getIntExtra("key",0);
        switch (key){
            case 1:
                text_title.setText("吧台椅");
                image.setImageResource(R.drawable.barstool);
                text_content.setText(R.string.detail_text_1);
                break;
            case 2:
                text_title.setText("床");
                image.setImageResource(R.drawable.bed);
                text_content.setText(R.string.detail_text_2);
                break;
            case 3:
                text_title.setText("沙发");
                image.setImageResource(R.drawable.sofa);
                text_content.setText(R.string.detail_text_3);
                break;
            case 4:
                text_title.setText("马桶");
                image.setImageResource(R.drawable.toilet);
                text_content.setText(R.string.detail_text_4);
                break;
            case 5:
                text_title.setText("咖啡桌");
                image.setImageResource(R.drawable.traditionpalette);
                text_content.setText(R.string.detail_text_5);
                break;
            case 6:
                text_title.setText("棕熊");
                image.setImageResource(R.drawable.bear);
                text_content.setText(R.string.detail_text_6);
                break;
            case 7:
                text_title.setText("猫");
                image.setImageResource(R.drawable.cat);
                text_content.setText(R.string.detail_text_7);
                break;
            default:
                finish();
        }
    }
}
