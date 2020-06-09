package com.ar.arhome.Domain;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.annotations.SerializedName;

public class LoginUserInfo {
    /**
     * msg : 登录成功
     * data : {"username":"18718200003","phone":"18718200003","password":false,"create_time":"2020-05-02 15:49:05","update_time":"2020-05-02 15:49:05","id":"31","logintype":"phone","token":"efa27f920e4e9d690b8be2a324174182640ad14e","userinfo":{"id":21,"user_id":31,"age":0,"sex":2,"qg":0,"job":"xxx","path":"xxx","birthday":"xxx"},"email":"xxx"}
     */

    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private DataBean data;

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
        /**
         * username : 18718200003
         * phone : 18718200003
         * password : false
         * create_time : 2020-05-02 15:49:05
         * update_time : 2020-05-02 15:49:05
         * id : 31
         * logintype : phone
         * token : efa27f920e4e9d690b8be2a324174182640ad14e
         * userinfo : {"id":21,"user_id":31,"age":0,"sex":2,"qg":0,"job":"xxx","path":"xxx","birthday":"xxx"}
         * email : xxx
         */

        @SerializedName("username")
        private String username;
        @SerializedName("phone")
        private String phone;
        @SerializedName("password")
        private String password;
        @SerializedName("create_time")
        private String createTime;
        @SerializedName("update_time")
        private String updateTime;
        @SerializedName("id")
        private String id;
        @SerializedName("logintype")
        private String logintype;
        @SerializedName("token")
        private String token;
        @SerializedName("userinfo")
        private UserinfoBean userinfo;
        @SerializedName("email")
        private String email;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String  getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLogintype() {
            return logintype;
        }

        public void setLogintype(String logintype) {
            this.logintype = logintype;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public UserinfoBean getUserinfo() {
            return userinfo;
        }

        public void setUserinfo(UserinfoBean userinfo) {
            this.userinfo = userinfo;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public static class UserinfoBean {
            /**
             * id : 21
             * user_id : 31
             * age : 0
             * sex : 2
             * qg : 0
             * job : xxx
             * path : xxx
             * birthday : xxx
             */

            @SerializedName("id")
            private int id;
            @SerializedName("user_id")
            private int userId;
            @SerializedName("age")
            private int age;
            @SerializedName("sex")
            private int sex;
            @SerializedName("qg")
            private int qg;
            @SerializedName("job")
            private String job;
            @SerializedName("path")
            private String path;
            @SerializedName("birthday")
            private String birthday;

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

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public int getQg() {
                return qg;
            }

            public void setQg(int qg) {
                this.qg = qg;
            }

            public String getJob() {
                return job;
            }

            public void setJob(String job) {
                this.job = job;
            }

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }
        }
    }

    public static void saveLoginUserInfo(Context context , LoginUserInfo loginUserInfo){
        DataBean data = loginUserInfo.getData();
        SharedPreferences.Editor editor
                = context.getSharedPreferences("LoginUserInfo", Context.MODE_PRIVATE)
                .edit();
        editor.putString("UserName", data.getUsername());
        editor.putString("Phone", data.getPhone());
        editor.putString("Password",data.getPassword());
        editor.putString("ID", data.getId());
        editor.putString("LoginType",data.getLogintype());
        editor.putString("Token", data.getToken());
        editor.putString("Email",data.getEmail());

        DataBean.UserinfoBean userinfoBean= loginUserInfo.getData().getUserinfo();

        editor.putInt("Age", userinfoBean.getAge());
        editor.putInt("Gender", userinfoBean.getSex());
        editor.putInt("QingganStatus", userinfoBean.getQg());
        editor.putString("Job", userinfoBean.getJob());
        editor.putString("Location",userinfoBean.getPath());
        editor.putString("Birthday", userinfoBean.getBirthday());

        editor.apply();
    }

    public static SharedPreferences  loadLoginUserInfo(Context context){
        return context.getSharedPreferences("LoginUserInfo", Context.MODE_PRIVATE);
    }

    public static void deleteLoginInfo(Context context){
        SharedPreferences preferences = context.getSharedPreferences("LoginUserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}
