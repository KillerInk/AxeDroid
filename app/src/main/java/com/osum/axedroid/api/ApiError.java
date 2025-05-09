package com.osum.axedroid.api;

public class ApiError {

    /**
     * Error code.
     */
    private int code;

    /**
     * Error message.
     */
    private String msg;

    private String message;
    public String statusCode;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "code " + code + " msg " +msg + " " + message;
    }
}
