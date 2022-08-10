package com.superwei.utils.resp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Arrays;

/**
 * @author weidongge
 * @since 2022-08-09 11:07
 **/
public class ResultData<T> {
    private static final String MSG_DEFAULT_SUCCESS = "success";
    private static final String MSG_DEFAULT_FAIL = "error";
    private int retCode;
    private String msg;
    private T data;

    public ResultData() {
    }

    public ResultData(int retCode, T data, String msg) {
        this.retCode = retCode;
        this.data = data;
        this.msg = msg;
    }

    public int getRetCode() {
        return this.retCode;
    }

    public ResultData<T> setRetCode(int retCode) {
        this.retCode = retCode;
        return this;
    }

    public T getData() {
        return this.data;
    }

    public ResultData<T> setData(T data) {
        this.data = data;
        return this;
    }

    public String getMsg() {
        return this.msg;
    }

    public ResultData<T> setMsg(String msg) {
        this.setMsg(msg, (Object[])null);
        return this;
    }

    public void setMsg(String msg, Object[] obj) {
        if (msg == null) {
            this.msg = null;
        } else {
            String message;
            try {
                message = msg;
                if (obj != null) {
                    String[] values = message.split("\\{");

                    for(int i = 0; i < values.length; ++i) {
                        message = message.replace("{" + i + "}", obj.length - 1 < i ? "" : obj[i].toString());
                    }
                }
            } catch (Exception var6) {
                throw new RuntimeException("setMsg error" + msg + "--" + Arrays.toString(obj));
            }

            this.msg = message;
        }
    }

    public static <T> ResultData<T> success() {
        return new ResultData(1, (Object)null, "success");
    }

    public static <T> ResultData<T> success(String msg) {
        return new ResultData(1, (Object)null, msg);
    }

    public static <T> ResultData<T> success(String msg, T data) {
        return new ResultData(1, data, msg);
    }

    public static <T> ResultData<T> success(T data) {
        return new ResultData(1, data, "success");
    }

    public static <T> ResultData<T> error() {
        return new ResultData(0, (Object)null, "error");
    }

    public static <T> ResultData<T> error(String msg) {
        return new ResultData(0, (Object)null, msg);
    }

    public static <T> ResultData<T> error(T data) {
        return new ResultData(0, data, "error");
    }

    public static <T> ResultData<T> error(String msg, T data) {
        return new ResultData(0, data, msg);
    }

    @Override
    public String toString() {
        return "ReturnData [retCode=" + this.retCode + ", data=" + this.data + "]";
    }

    @JsonIgnore
    public boolean isSuccess() {
        return this.getRetCode() == 1;
    }

    @JsonIgnore
    public boolean isFail() {
        return !this.isSuccess();
    }

    @JsonIgnore
    public static boolean isSucceed(ResultData resultData) {
        if (resultData == null) {
            return false;
        } else {
            return resultData.getRetCode() == 1;
        }
    }

    @JsonIgnore
    public static boolean isFailed(ResultData resultData) {
        if (resultData == null) {
            return true;
        } else {
            return !isSucceed(resultData);
        }
    }
}
