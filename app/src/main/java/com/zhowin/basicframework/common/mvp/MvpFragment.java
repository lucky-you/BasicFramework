package com.zhowin.basicframework.common.mvp;


import android.os.Bundle;
import android.view.View;

import com.zhowin.basicframework.common.base.LibFragment;


public abstract class MvpFragment<P extends BasePresenter> extends LibFragment {
    protected P mvpPresenter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (mvpPresenter == null) mvpPresenter = createPresenter();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void firstLoad() {
        if (mvpPresenter == null) mvpPresenter = createPresenter();
        super.firstLoad();
    }

    protected abstract P createPresenter();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
        }
    }
}
