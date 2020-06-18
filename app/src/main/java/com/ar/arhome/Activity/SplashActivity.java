package com.ar.arhome.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ar.arhome.Domain.LoginUserInfo;
import com.ar.arhome.R;

import org.litepal.LitePal;

import java.util.Timer;
import java.util.TimerTask;

import static com.xuexiang.xui.XUI.getContext;

public class SplashActivity extends AppCompatActivity {
    private SharedPreferences userInfo;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // 初始化数据库
        // 2020.6.18 @fang
        LitePal.getDatabase();

        //尝试取出已经保存的用户信息
        userInfo = LoginUserInfo.loadLoginUserInfo(this);
        this.token = userInfo.getString("Token","undefined");
        // 展示并开始倒计时
        TimerTask timeTask = new TimerTask() {
            @Override
            public void run() {
                // 如果去除的Token是undefined 说明还没有用户登录过 跳转到用户登录界面
                if (token.equals("undefined")) {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
                // 否测直接进入主界面
                else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(timeTask, 1000);
    }
}
