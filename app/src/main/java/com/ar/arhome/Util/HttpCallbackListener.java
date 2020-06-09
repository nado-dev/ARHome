package com.ar.arhome.Util;

public interface HttpCallbackListener {
    void onFinish(String response);

    void onError(Exception e);
}
