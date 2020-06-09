package com.ar.arhome.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ar.arhome.Domain.LoginUserInfo;
import com.ar.arhome.Domain.PhoneLoginSentMsgInfo;
import com.ar.arhome.R;
import com.ar.arhome.Util.ApiRequest;
import com.ar.arhome.Util.FormatCheck;
import com.ar.arhome.Util.HttpUtil;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 登录Activity
 */
public class LoginActivity extends AppCompatActivity {

    private Button btn_loadCode;
    private EditText edt_phone;
    private EditText edt_code;
    private Button btn_login;
    private String phoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_loadCode = findViewById(R.id.loadCode);
        btn_login = findViewById(R.id.login);
        edt_phone = findViewById(R.id.phoneNum);
        edt_code = findViewById(R.id.code);

        btn_login.setEnabled(false);
        btn_loadCode.setEnabled(false);

        // 发送验证码，设置倒计时防止过快发送
        btn_loadCode.setOnClickListener((v) -> {
            this.phoneNum = edt_phone.getText().toString();
            btn_loadCode.setEnabled(false);
            CountDownTimer countDownTimer = new CountDownTimer(5000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    String s = String.valueOf(millisUntilFinished / 1000);
                    btn_loadCode.setText(s);
                }

                @Override
                public void onFinish() {
                    btn_loadCode.setEnabled(true);
                    btn_loadCode.setText("获取验证码");
                }
            };
            countDownTimer.start();

            // 发送http请求
            HttpUtil.sendOkHttpRequestByPostMethod(ApiRequest.baseUrl + "user/sendcode?phone=" + phoneNum
                    , new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            showFailureMsg();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String res = response.body() != null ? response.body().string() : "empty body";
                            Log.d("Okhttp", res);
                            showLoadCodeSuccess(res);
                        }
                    });
        });

        // 监听edittext变化，当手机号空时禁用发送验证码按钮
        edt_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (FormatCheck.isPhoneNum(s.toString())) {
                    btn_loadCode.setEnabled(true);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (FormatCheck.isPhoneNum(s.toString())) {
                    btn_loadCode.setEnabled(true);
                }
            }
        });

        // 监听edittext变化，当验证码空时禁用登录按钮
        edt_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s != null && !s.toString().isEmpty() && Integer.parseInt(s.toString()) <= 9999
                        && Integer.parseInt(s.toString()) >= 1000) {
                    btn_login.setEnabled(true);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && !s.toString().isEmpty() && Integer.parseInt(s.toString()) <= 9999
                        && Integer.parseInt(s.toString()) >= 1000) {
                    btn_login.setEnabled(true);
                }
            }
        });

        //登录操作
        btn_login.setOnClickListener(v -> {
            String code = edt_code.getText().toString();
            HttpUtil.sendOkHttpRequestByPostMethod(ApiRequest.baseUrl + "user/phonelogin?phone=" + phoneNum + "&code=" + code,
                    new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            showFailureMsg();
                            Log.d("Okhttp", "fall");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String res = response.body() != null ? response.body().string() : "empty body";
                            Log.d("Okhttp", res);
                            showLoginSuccess(res);
                        }
                    });
        });
    }

    // 登录成功后操作
    private void showLoginSuccess(String res) {
        Gson gson = new Gson();
        LoginUserInfo loginUserInfo = gson.fromJson(res, LoginUserInfo.class);
        if (loginUserInfo.getMsg().equals("登录成功")) {
            Looper.prepare();
            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
            // 保存信息
            LoginUserInfo.saveLoginUserInfo(this, loginUserInfo);
            startActivity(new Intent(this, MainActivity.class));
            finish();
            Looper.loop();

        } else {
            Looper.prepare();
            Toast.makeText(this, "登录失败" + loginUserInfo.getMsg(), Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    }

    private void showLoadCodeSuccess(String res) {
        Gson gson = new Gson();
        PhoneLoginSentMsgInfo phoneLoginSentMsgInfo = gson.fromJson(res, PhoneLoginSentMsgInfo.class);
        int statusCode = phoneLoginSentMsgInfo.getErrorCode();
        if (statusCode == 30005) {
            String msg = phoneLoginSentMsgInfo.getMsg();
            Looper.prepare();
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            Looper.loop();
        } else {
            Looper.prepare();
            Toast.makeText(this, phoneLoginSentMsgInfo.getMsg(), Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    }

    private void showFailureMsg() {
        Looper.prepare();
        Toast.makeText(this, "请求失败", Toast.LENGTH_SHORT).show();
        Looper.loop();
    }
}
