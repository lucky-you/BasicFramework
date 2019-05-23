package com.zhowin.basicframework.common.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhowin.basicframework.R;
import com.zhowin.basicframework.common.base.BaseActivity;
import com.zhowin.basicframework.common.utils.SizeUtils;
import com.zhowin.basicframework.common.view.RefreshLayout;
import com.zhowin.viewlibrary.empty.LoadingController;
import com.zhowin.viewlibrary.empty.LoadingViewUtils;
import com.zhowin.viewlibrary.empty.OnNextClickListener;
import com.zhowin.viewlibrary.view.SimpleTitleBar;

/**
 * 登录界面，这里只是方便展示网络请求，正式项目根据不同的需求来展示不同的界面
 */
public class LoginActivity extends BaseActivity {

    private SimpleTitleBar simpleTitleBar;

    private RefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private LoadingController loadingController;

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

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
        simpleTitleBar.setTitleText("列表展示")
                .isShowRightLayout(true)
                .isShowRightText(true)
                .setRightTextColor(mContext.getResources().getColor(R.color.color_333))
                .setRightText("查看明细")
                .isShowBottomDividerLine(true)
                .setBottomDividerLineHeight(SizeUtils.dp2px(1))
                .setRightAction(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showToast("明细");
                    }
                });

        loadingController = LoadingViewUtils.showLoadingView(mContext, recyclerView, new OnNextClickListener() {
            @Override
            public void onNextClick() {
                showToast("点击了重新加载");
                loadingController.dismissLoading();
            }
        });
    }

    @Override
    public void processLogic(Bundle savedInstanceState) {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
                loadingController.showError();
            }
        });

        simpleTitleBar.postDelayed(new Runnable() {
            @Override
            public void run() {

//                loadingController.showEmpty();
                loadingController.showNetworkError();
            }
        }, 2000);

    }

    @Override
    public void setClickListener(View view) {

    }
}

