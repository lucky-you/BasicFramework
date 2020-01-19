package com.zhowin.basicframework.common.api;

/**
 * 数据结构的基类
 *
 * @param <T> 使用泛型
 */
public class ResultResponse<T> {

    public int code;
    public String msg;
    public long time;
    public T data;

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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", time=" + time +
                ", data=" + data +
                '}';
    }
}
