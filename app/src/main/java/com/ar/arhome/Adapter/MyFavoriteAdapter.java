package com.ar.arhome.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ar.arhome.Activity.DetailActivity;
import com.ar.arhome.R;
import com.ar.arhome.Sqlite.FavoriteModel;
import com.ar.arhome.Util.FavoritePerformance;

import org.litepal.crud.DataSupport;

import java.util.List;

public class MyFavoriteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private List<FavoriteModel> data;

    public MyFavoriteAdapter(Context context, List<FavoriteModel> data){
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_favorite_model_single, parent,false);
        return new MyFavoriteAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder)holder;
        FavoriteModel favoriteModelSigle = data.get(position);
        setModelInfo(favoriteModelSigle, recyclerViewHolder);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView modelPic;
        TextView modelName;
        TextView modelAddedTime;
        ImageView modelDetail;
        ImageView modelDelete;

        RecyclerViewHolder(View view) {
            super(view);
            modelPic = view.findViewById(R.id.img_fav_pic);
            modelName = view.findViewById(R.id.text_fav_name);
            modelAddedTime = view.findViewById(R.id.text_fav_time);
            modelDetail = view.findViewById(R.id.btn_fav_detail);
            modelDelete = view.findViewById(R.id.btn_fav_delete);
        }
    }

    private void setModelInfo(FavoriteModel favoriteModel, RecyclerViewHolder recyclerViewHolder){
        switch (favoriteModel.getModelNum()){
            case 1:
                recyclerViewHolder.modelPic.setImageResource(R.drawable.barstool);
                recyclerViewHolder.modelName.setText("吧台椅");
                recyclerViewHolder.modelAddedTime.setText(favoriteModel.getCreateTime());
                recyclerViewHolder.modelDetail.setOnClickListener(v->{
                    Intent intent = new Intent();
                    intent.setClass(context, DetailActivity.class);
                    intent.putExtra("key",1);
                    context.startActivity(intent);
                });
                break;
            case 2:
                recyclerViewHolder.modelPic.setImageResource(R.drawable.bed);
                recyclerViewHolder.modelName.setText("床");
                recyclerViewHolder.modelAddedTime.setText(favoriteModel.getCreateTime());
                recyclerViewHolder.modelDetail.setOnClickListener(v->{
                    Intent intent = new Intent();
                    intent.setClass(context, DetailActivity.class);
                    intent.putExtra("key",2);
                    context.startActivity(intent);
                });
                break;
            case 3:
                recyclerViewHolder.modelPic.setImageResource(R.drawable.sofa);
                recyclerViewHolder.modelName.setText("沙发");
                recyclerViewHolder.modelAddedTime.setText(favoriteModel.getCreateTime());
                recyclerViewHolder.modelDetail.setOnClickListener(v->{
                    Intent intent = new Intent();
                    intent.setClass(context, DetailActivity.class);
                    intent.putExtra("key",3);
                    context.startActivity(intent);
                });
                break;
            case 4:
                recyclerViewHolder.modelPic.setImageResource(R.drawable.toilet);
                recyclerViewHolder.modelName.setText("马桶");
                recyclerViewHolder.modelAddedTime.setText(favoriteModel.getCreateTime());
                recyclerViewHolder.modelDetail.setOnClickListener(v->{
                    Intent intent = new Intent();
                    intent.setClass(context, DetailActivity.class);
                    intent.putExtra("key",4);
                    context.startActivity(intent);
                });
                break;
            case 5:
                recyclerViewHolder.modelPic.setImageResource(R.drawable.traditionpalette);
                recyclerViewHolder.modelName.setText("咖啡桌");
                recyclerViewHolder.modelAddedTime.setText(favoriteModel.getCreateTime());
                recyclerViewHolder.modelDetail.setOnClickListener(v->{
                    Intent intent = new Intent();
                    intent.setClass(context, DetailActivity.class);
                    intent.putExtra("key",5);
                    context.startActivity(intent);
                });
                break;
            case 6:
                recyclerViewHolder.modelPic.setImageResource(R.drawable.bear);
                recyclerViewHolder.modelName.setText("熊");
                recyclerViewHolder.modelAddedTime.setText(favoriteModel.getCreateTime());
                recyclerViewHolder.modelDetail.setOnClickListener(v->{
                    Intent intent = new Intent();
                    intent.setClass(context, DetailActivity.class);
                    intent.putExtra("key",6);
                    context.startActivity(intent);
                });
                break;
            case 7:
                recyclerViewHolder.modelPic.setImageResource(R.drawable.cat);
                recyclerViewHolder.modelName.setText("猫");
                recyclerViewHolder.modelAddedTime.setText(favoriteModel.getCreateTime());
                recyclerViewHolder.modelDetail.setOnClickListener(v->{
                    Intent intent = new Intent();
                    intent.setClass(context, DetailActivity.class);
                    intent.putExtra("key",7);
                    context.startActivity(intent);
                });
                break;
            default:
        }
        recyclerViewHolder.modelDelete.setOnClickListener(v->{
            DataSupport.deleteAll(FavoriteModel.class,
                    "owner = ? and modelNum = ?",favoriteModel.getOwner()
                    , String.valueOf(favoriteModel.getModelNum()));
            FavoritePerformance favoritePerformance
                    = new FavoritePerformance(context, favoriteModel.getOwner(), favoriteModel.getModelNum());
            favoritePerformance.updateToMyFavoriteCollection(0);
            Toast.makeText(context, "取消收藏成功",Toast.LENGTH_SHORT).show();
            data.remove(favoriteModel);
            notifyDataSetChanged();
        });
    }
}
