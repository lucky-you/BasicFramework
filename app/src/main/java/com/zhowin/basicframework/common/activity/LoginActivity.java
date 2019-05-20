package com.zhowin.basicframework.common.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.zhowin.basicframework.R;
import com.zhowin.basicframework.common.base.BaseActivity;
import com.zhowin.viewlibrary.view.SimpleTitleBar;

/**
 * 登录界面，这里只是方便展示网络请求，正式项目根据不同的需求来展示不同的界面
 */
public class LoginActivity extends BaseActivity {

    private SimpleTitleBar simpleTitleBar;

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
    }

    @Override
    public void processLogic(Bundle savedInstanceState) {
//        simpleTitleBar.setRightIcon(R.drawable.icon_right_more)
//                .setTitleBarTitle("就是登录");
    }

    @Override
    public void setClickListener(View view) {

    }
}

