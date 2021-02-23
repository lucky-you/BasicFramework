package com.zhowin.basicframework.common.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhowin.basicframework.R;
import com.zhowin.basicframework.common.base.BaseActivity;
import com.zhowin.basicframework.common.view.LoadingViewUtils;
import com.zhowin.basicframework.common.view.RefreshLayout;
import com.zhowin.library.empty.LoadingController;
import com.zhowin.library.view.ZhoSimpleTitleView;

/**
 * 登录界面，这里只是方便展示网络请求，正式项目根据不同的需求来展示不同的界面
 */
public class LoginActivity extends BaseActivity {

    private RefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private LoadingController loadingController;
    private ZhoSimpleTitleView zhoTitleView;

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int loadViewLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void bindViews(View contentView) {
        refreshLayout = get(R.id.refreshLayout);
        recyclerView = get(R.id.recyclerView);
        zhoTitleView = get(R.id.zhoTitleView);
        loadingController = LoadingViewUtils.showLoadingView(mContext, recyclerView, () -> {
            showToast("点击了重新加载");
            loadingController.dismissLoading();
        });
        zhoTitleView.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingController.showEmpty();
            }
        }, 1000);
    }

    @Override
    public void processLogic(Bundle savedInstanceState) {
        refreshLayout.setOnRefreshListener(() -> {
            refreshLayout.setRefreshing(false);
            loadingController.showError();
        });
        zhoTitleView.setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, RegisterActivity.class));
            }
        });
    }


    @Override
    public void onViewClick(View view) {

    }
}

