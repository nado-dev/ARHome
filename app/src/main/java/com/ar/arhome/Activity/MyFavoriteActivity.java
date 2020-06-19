package com.ar.arhome.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.ar.arhome.Adapter.MyFavoriteAdapter;
import com.ar.arhome.Domain.LoginUserInfo;
import com.ar.arhome.R;
import com.ar.arhome.Sqlite.FavoriteModel;
import com.ar.arhome.Util.FavoritePerformance;

import org.litepal.crud.DataSupport;

import java.util.HashMap;
import java.util.List;

public class MyFavoriteActivity extends AppCompatActivity {
    private SharedPreferences userInfo;
    private String token;
    private RecyclerView recyclerView;
    private ImageView back;
    private ImageView refresh;
    private List<FavoriteModel> data;
    private MyFavoriteAdapter myFavoriteAdapter;

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
        refresh = findViewById(R.id.btn_fav_refresh);
        recyclerView = findViewById(R.id.recycle_view);
        // 查找信息
        searchFavModel();
        // 创建Adapter
        setRecyclerViewAdapter();
        refresh.setOnClickListener(v->{
            AlertDialog dialog;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("是否要同步服务器收藏数据？");
            builder.setMessage("同步操作可能会覆盖您的数据，这将可能导致部分数据丢失且不可逆。");
            builder.setCancelable(true);
            builder.setPositiveButton("继续", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // 从后端获取最新列表
                    FavoritePerformance favoritePerformance =
                            new FavoritePerformance(MyFavoriteActivity.this, token, 0);
                    HashMap<String,Integer> map = favoritePerformance.refreshListFromBackend();
                    if(map.isEmpty()) return;
                    else{
                        data.clear();
                        // 从网络上同步数据
                        // 1. 清空原来的数据表
                        DataSupport.deleteAll(FavoriteModel.class, "owner = ?", token);
                        map.forEach((date,modelNum)-> {
                            FavoriteModel favoriteModel = new FavoriteModel();
                            favoriteModel.setCreateTime(date);
                            favoriteModel.setModelNum(modelNum);
                            favoriteModel.setOwner(token);
                            // 2. 更新数据
                            data.add(favoriteModel);
                            // 3. 重写数据表
                            favoriteModel.save();
                            myFavoriteAdapter.notifyDataSetChanged();
                        });
                        Toast.makeText(MyFavoriteActivity.this, "已经同步服务器数据",Toast.LENGTH_SHORT).show();
                    }
                }
            });
           builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                   dialog.dismiss();
               }
           });
           builder.show();
        });
    }

    private void searchFavModel() {
         data = DataSupport
                .where("owner = ? ",token)
                .find(FavoriteModel.class);
    }

    private void setRecyclerViewAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myFavoriteAdapter = new MyFavoriteAdapter(this, data);
        recyclerView.setAdapter(myFavoriteAdapter);
    }
}
