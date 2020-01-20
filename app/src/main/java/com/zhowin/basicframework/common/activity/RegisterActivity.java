package com.zhowin.basicframework.common.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.zhowin.basicframework.R;
import com.zhowin.basicframework.common.base.BaseActivity;

/**
 * 注册界面
 */
public class RegisterActivity extends BaseActivity {


    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int loadViewLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void bindViews(View contentView) {
        Log.e("xy", "初始化控件");

    }

    @Override
    public void processLogic(Bundle savedInstanceState) {
        Log.e("xy", "设置数据");
    }

    @Override
    public void onViewClick(View view) {

    }

}
