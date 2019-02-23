package com.zhowin.basicframework.common.activity;

import android.app.Dialog;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.zhowin.basicframework.R;
import com.zhowin.basicframework.common.base.BaseActivity;
import com.zhowin.viewlibrary.callback.OnAndroidDialogClickListener;
import com.zhowin.viewlibrary.dialog.PasswordDialogUtils;
import com.zhowin.viewlibrary.view.AndroidDialog;
import com.zhowin.viewlibrary.view.PasswordEditText;

public class MainActivity extends BaseActivity {


    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int loadViewLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void bindViews(View contentView) {
        get(R.id.tvTypeOne).setOnClickListener(this);
        get(R.id.tvTypeTwo).setOnClickListener(this);
        get(R.id.tvTypeThree).setOnClickListener(this);
    }

    @Override
    public void processLogic(Bundle savedInstanceState) {
    }

    @Override
    public void setClickListener(View view) {
        switch (view.getId()) {
            case R.id.tvTypeOne:
                setPasswordDialogOne();
                break;
            case R.id.tvTypeTwo:
                setPasswordDialogTwo();
                break;

            case R.id.tvTypeThree:
                showDialog();
                break;


        }
    }

    private void setPasswordDialogOne() {
        PasswordDialogUtils.passwordDialog(mContext, new PasswordEditText.PasswordFullListener() {
            @Override
            public void passwordFull(String password) {
                showToast("密码是=" + password);
            }
        });
    }

    private void setPasswordDialogTwo() {
        PasswordDialogUtils.passwordEditDialog(mContext, new PasswordEditText.PasswordFullListener() {
            @Override
            public void passwordFull(String password) {
                showToast("密码是=" + password);
            }
        });
    }


    private void showDialog() {
        new AndroidDialog(mContext, R.style.AndroidDialogStyle, "您确定删除此信息？", new OnAndroidDialogClickListener() {
            @Override
            public void onCancelClick(Dialog dialog) {
                showToast("取消");
            }

            @Override
            public void onDetermineClick(Dialog dialog) {
                showToast("确定");
            }
        }).setTitle("友情提醒").show();


    }

}





