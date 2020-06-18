package com.ar.arhome.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import com.ar.arhome.Adapter.MyFavoriteAdapter;
import com.ar.arhome.Domain.LoginUserInfo;
import com.ar.arhome.R;
import com.ar.arhome.Sqlite.FavoriteModel;

import org.litepal.crud.DataSupport;

import java.util.List;

public class MyFavoriteActivity extends AppCompatActivity {
    private SharedPreferences userInfo;
    private String token;
    private RecyclerView recyclerView;
    private ImageView back;
    private List<FavoriteModel> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favorite);
        userInfo = LoginUserInfo.loadLoginUserInfo(this);
        this.token = userInfo.getString("Token","undefined");

        back = (ImageView) findViewById(R.id.btn_fav_return);
        back.setOnClickListener(v->{
            finish();
        });

        recyclerView = findViewById(R.id.recycle_view);
        // 查找信息
        searchFavModel();
        // 创建Adapter
        setRecyclerViewAdapter();
    }

    private void searchFavModel() {
         data = DataSupport
                .where("owner = ? ",token)
                .find(FavoriteModel.class);
    }

    private void setRecyclerViewAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyFavoriteAdapter myFavoriteAdapter = new MyFavoriteAdapter(this, data);
        recyclerView.setAdapter(myFavoriteAdapter);
    }
}
