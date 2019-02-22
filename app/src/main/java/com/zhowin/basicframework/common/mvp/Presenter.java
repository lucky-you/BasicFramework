package com.zhowin.basicframework.common.mvp;

public interface Presenter<V> {

    void attachView(V view);

    void detachView();

}
