package com.ar.arhome.Domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FavInfo {
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private FavInfo.DataBean data;
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        @SerializedName("list")
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 5
             * user_id : 17
             * post_id : 9
             * type : 1
             * create_time : 2020-02-13 12:04:53
             */

            @SerializedName("id")
            private int id;
            @SerializedName("user_id")
            private int userId;
            @SerializedName("post_id")
            private int postId;
            @SerializedName("type")
            private int typeX;
            @SerializedName("create_time")
            private String createTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public int getPostId() {
                return postId;
            }

            public void setPostId(int postId) {
                this.postId = postId;
            }

            public int getTypeX() {
                return typeX;
            }

            public void setTypeX(int typeX) {
                this.typeX = typeX;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }
        }
    }
}

