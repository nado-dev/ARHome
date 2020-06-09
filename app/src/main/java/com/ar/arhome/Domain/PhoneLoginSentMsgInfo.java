package com.ar.arhome.Domain;

import com.google.gson.annotations.SerializedName;

public class PhoneLoginSentMsgInfo {
    /**
     * msg : 验证码：6170
     * errorCode : 30005
     */

    @SerializedName("msg")
    private String msg;
    @SerializedName("errorCode")
    private int errorCode;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
