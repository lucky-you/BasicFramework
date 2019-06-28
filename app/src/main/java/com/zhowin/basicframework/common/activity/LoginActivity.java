package com.zhowin.basicframework.common.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhowin.basicframework.R;
import com.zhowin.basicframework.common.base.BaseActivity;
import com.zhowin.basicframework.common.utils.SizeUtils;
import com.zhowin.basicframework.common.utils.SnackbarUtils;
import com.zhowin.basicframework.common.view.LoadingViewUtils;
import com.zhowin.basicframework.common.view.RefreshLayout;
import com.zhowin.viewlibrary.empty.LoadingController;
import com.zhowin.viewlibrary.view.SimpleTitleBar;

/**
 * 登录界面，这里只是方便展示网络请求，正式项目根据不同的需求来展示不同的界面
 */
public class LoginActivity extends BaseActivity {

    private SimpleTitleBar simpleTitleBar;
    private RefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private LoadingController loadingController;


    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int loadViewLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void bindViews(View contentView) {
        simpleTitleBar = get(R.id.simpleTitleBar);
        refreshLayout = get(R.id.refreshLayout);
        recyclerView = get(R.id.recyclerView);
        simpleTitleBar.setTitleText("列表展示");
        simpleTitleBar.isShowRightLayout(true);
        simpleTitleBar.isShowRightText(true);
        simpleTitleBar.setRightTextColor(mContext.getResources().getColor(R.color.color_333));
        simpleTitleBar.setRightText("明细");
        simpleTitleBar.isShowBottomDividerLine(true);
        simpleTitleBar.setBottomDividerLineHeight(SizeUtils.dp2px(1));
        simpleTitleBar.setRightAction(view -> {
//                LoginActivity.this.showToast("点击了明细");
            SnackbarUtils.with(findViewById(android.R.id.content))
                    .setMessage("点击了明细")
                    .show();

        });

        loadingController = LoadingViewUtils.showLoadingView(mContext, recyclerView, () -> {
            showToast("点击了重新加载");
            loadingController.dismissLoading();
        });

    }

    @Override
    public void processLogic(Bundle savedInstanceState) {
        refreshLayout.setOnRefreshListener(() -> {
            refreshLayout.setRefreshing(false);
            loadingController.showError();
        });

        simpleTitleBar.postDelayed(() -> {
//                loadingController.showEmpty();
            loadingController.showNetworkError();
        }, 2000);

    }


    @Override
    public void setClickListener(View view) {

    }
}

