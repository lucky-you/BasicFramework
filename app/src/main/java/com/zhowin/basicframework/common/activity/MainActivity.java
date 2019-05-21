package com.zhowin.basicframework.common.activity;

import android.app.Dialog;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;

import com.zhowin.basicframework.R;
import com.zhowin.basicframework.common.base.BaseActivity;
import com.zhowin.basicframework.common.utils.SharedPreferenceHelper;
import com.zhowin.viewlibrary.callback.OnAndroidDialogClickListener;
import com.zhowin.viewlibrary.dialog.HitIOSDialog;
import com.zhowin.viewlibrary.dialog.PasswordDialogUtils;
import com.zhowin.viewlibrary.dialog.AndroidDialog;
import com.zhowin.viewlibrary.view.PasswordEditText;
import com.zhowin.viewlibrary.widget.TagGroupLayout;

import java.util.Arrays;
import java.util.List;

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
        get(R.id.tvTypeFour).setOnClickListener(this);
        get(R.id.tvTypeFive).setOnClickListener(this);

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
            case R.id.tvTypeFour:
                shoIosDialog();
                break;
            case R.id.tvTypeFive:
                showActionSheet();
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
        new AndroidDialog(mContext, "您确定删除此信息？", new OnAndroidDialogClickListener() {
            @Override
            public void onNegativeClick(Dialog dialog) {
                showToast("取消");
            }

            @Override
            public void onPositiveClick(Dialog dialog) {
                showToast("确定");
            }
        })
                .setTitle("友情提醒")
                .show();
    }

    private void shoIosDialog() {
        HitIOSDialog hitIOSDialog = new HitIOSDialog(mContext).builder();
        hitIOSDialog
                .setTitle("友情提示")
                .setMsg("确定要退出吗?")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).show();
    }

    private void showActionSheet() {
        LoginActivity.start(mContext);
    }
}





