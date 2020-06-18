package com.ar.arhome.Sqlite;

import org.litepal.crud.DataSupport;

/**
 * 用于Litepal的JavaBean
 * 模型收藏表
 * FavoriteModel(id(PK), owner, modelNum, createTime)
 * 2020.6.18 @Fang
 */
public class FavoriteModel extends DataSupport {
    private String owner;
    private int modelNum;
    private String createTime;


    public int getModelNum() {
        return modelNum;
    }

    public void setModelNum(int modelNum) {
        this.modelNum = modelNum;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
