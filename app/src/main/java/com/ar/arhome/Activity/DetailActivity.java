package com.ar.arhome.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ar.arhome.Domain.LoginUserInfo;
import com.ar.arhome.R;
import com.ar.arhome.Sqlite.FavoriteModel;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private SharedPreferences userInfo;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ImageView btn_return = findViewById(R.id.btn_return);
        TextView text_title = findViewById(R.id.detail_text_title);
        ImageView image = findViewById(R.id.detail_image);
        TextView text_content = findViewById(R.id.detail_text_content);
        userInfo = LoginUserInfo.loadLoginUserInfo(this);
        this.token = userInfo.getString("Token","undefined");
        //仅
        Button btn_save = findViewById(R.id.btn_save);
        Button btn_show = findViewById(R.id.btn_show);
        int key = getIntent().getIntExtra("key",0);
        // 以下是新增button到AR预览界面的代码 2020.6.17 @房文宇
        btn_show.setOnClickListener(v->{
            Intent intent = new Intent(this, ARFrameActivity.class);
            intent.putExtra("ModelIndex", key);
            startActivity(intent);
        });
        // 返回按钮实装
        btn_return.setOnClickListener((v)->{
            finish();
        });
        // 以下是实现收藏功能的代码 2020.6.18 @房文宇
        btn_save.setOnClickListener(v->{
            List<FavoriteModel> favoriteModelsExists = DataSupport
                    .where("owner = ?",token)
                    .find(FavoriteModel.class);
            if (favoriteModelsExists.size() != 0){
                for (FavoriteModel item: favoriteModelsExists){
                    if (item.getModelNum() == key){
                        DataSupport.deleteAll(FavoriteModel.class,
                                "owner = ? and modelNum = ?",token, String.valueOf(key));
                        Toast.makeText(this, "取消收藏成功",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
            FavoriteModel favoriteModel = new FavoriteModel();
            favoriteModel.setOwner(token);
            favoriteModel.setModelNum(key);
            String date =
                    new SimpleDateFormat("yyyy/MM/dd/HH:mm:ss", java.util.Locale.getDefault()).format(new Date());
            favoriteModel.setCreateTime(date);
            favoriteModel.save();
            //调试
            favoriteModelsExists = DataSupport.findAll(FavoriteModel.class);
            for(FavoriteModel favoriteModel1: favoriteModelsExists){
                Log.d("DATA", favoriteModel1.getOwner()+"          "+favoriteModel1.getModelNum()+
                        "           "+favoriteModel1.getCreateTime()+"\n");
            }
            Toast.makeText(this, "收藏成功",Toast.LENGTH_SHORT).show();


        });

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
