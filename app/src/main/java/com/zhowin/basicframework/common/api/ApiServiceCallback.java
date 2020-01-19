package com.zhowin.basicframework.common.api;


import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.observers.DisposableObserver;


/**
 * 请求结果的回调
 */
public abstract class ApiServiceCallback<T> extends DisposableObserver<ResultResponse<? extends T>> {


    public static final int JSON_ERROR = 0X777;//解析错误
    public static final int DATA_ERROR = 0X888;//数据错误

    @Override
    protected void onStart() {
        onStartRequest();
    }

    @Override
    public void onNext(ResultResponse response) {
        if (1 == response.code) {
            onSuccess((T) response.data, response.msg);
        } else {
            onFailure(response.code, response.msg);
        }
        onNextFinish(response);
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
        String error;
        if (throwable instanceof SocketTimeoutException  //网络超时,网络连接异常
                || throwable instanceof ConnectException   //均视为网络异常
                || throwable instanceof UnknownHostException) {
            error = "网络连接异常：" + throwable.getMessage();
            onErrorThrowable(error);
        } else if (throwable instanceof JsonParseException
                || throwable instanceof JSONException     //均视为解析错误
                || throwable instanceof java.text.ParseException) {
            error = "数据解析异常：" + throwable.getMessage();
            onFailure(JSON_ERROR, error);
        } else {
            error = "错误:" + throwable.getMessage();
            onFailure(DATA_ERROR, error);
        }

    }


    @Override
    public void onComplete() {
    }

    /**
     * 网络请求开始
     */
    public void onStartRequest() {
    }

    /**
     * 网络请求成功
     */
    public abstract void onSuccess(T response, String message);

    /**
     * 网络请求失败
     */
    public abstract void onFailure(int resultCode, String message);


    /**
     * 网络请求异常
     */
    public abstract void onErrorThrowable(String message);

    /**
     * 网络请求
     */
    public void onNextFinish(ResultResponse response) {
    }


}
