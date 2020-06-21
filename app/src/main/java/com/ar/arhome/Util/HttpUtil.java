package com.ar.arhome.Util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {

    public static void sendHttpRequestByGetMethod(final String address, final HttpCallbackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(address);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    // 设置超时时间
                    connection.setConnectTimeout(10000);
                    // 设置方法
                    connection.setRequestMethod("GET");
                    // 设置headers

                    connection.connect();
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    if (null != listener) {
                        // onFinish
                        listener.onFinish(response.toString());
                    }

                } catch (Exception e) {
                    if (listener != null) {
                        listener.onError(e);
                    }
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void sendOkHttpRequestByGetMethod(String address, Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .headers(new Headers.Builder()
                            .add("Content-Type", "application/json;charset=UTF-8")
                            .add("Content-Type", "application/x-www-form-urlencoded")
                            .build())
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public static void sendOkHttpRequestByGetMethodWithHeaders(String address, Callback callback, Headers headers){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .headers(headers)
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * 空body post
     * @param address
     * @param callback
     */
    public static void sendOkHttpRequestByPostMethod(String address, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .headers(new Headers.Builder()
                        .add("Content-Type", "application/json;charset=UTF-8")
                        .add("Content-Type", "application/x-www-form-urlencoded")
                        .build())
                .url(address)
                .post(okhttp3.internal.Util.EMPTY_REQUEST )
                .build();
        client.newCall(request).enqueue(callback);
    }

    public static void sendOkHttpRequestByPostMethodWithHeaderAndBody(String address
            , RequestBody requestBody, Headers headers, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .headers(headers)
                .url(address)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public static void sendOkHttpRequestByPostMethodWithHeader(String address
            , Headers headers, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .headers(headers)
                .url(address)
                .post(okhttp3.internal.Util.EMPTY_REQUEST )
                .build();
        client.newCall(request).enqueue(callback);
    }
}
