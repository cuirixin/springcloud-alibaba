package com.phoenix.result;

import org.apache.logging.log4j.util.Strings;

import java.io.Serializable;

public final class ResultJson<T> implements Serializable {

    private static final long serialVersionUID = 2053742054576495115L;
    private Integer code;
    private String message;
    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static ResultJson ok() {
        return ok("");
    }

    public static ResultJson ok(Object o) {
        return new ResultJson(ResultEnum.SUCCESS, "", o);
    }

    public static ResultJson failure(ResultEnum code) {
        return failure(code, "");
    }

    public static ResultJson failure(ResultEnum code, String message) {
        return new ResultJson(code, message, null);
    }

    public ResultJson(ResultEnum resultCode) {
        setResultCode(resultCode);
    }

    public ResultJson(ResultEnum resultCode, String message, T data) {
        this.message = message;
        setResultCode(resultCode);
        this.data = data;
    }

    public void setResultCode(ResultEnum resultCode) {
        this.code = resultCode.getCode();
        this.message = Strings.isEmpty(this.message) ? resultCode.getValue() : this.message;
    }

    @Override
    public String toString() {
        return "{" +
                "\"code\":" + code +
                ", \"message\":\"" + message + '\"' +
                ", \"data\":\"" + data + '\"' +
                '}';
    }
}
