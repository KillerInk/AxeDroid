package com.osum.axedroid.api;

@FunctionalInterface
public interface ApiCallBack<T> {
    void onResponse(T response);
    default void onFailure(Throwable cause) {}
}
