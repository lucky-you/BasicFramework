package com.zhowin.basicframework.common.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yanzhenjie.permission.runtime.Permission;
import com.zhowin.basicframework.R;
import com.zhowin.basicframework.common.activity.LoginActivity;
import com.zhowin.basicframework.common.activity.MainActivity;
import com.zhowin.basicframework.common.base.BaseFragment;
import com.zhowin.basicframework.common.download.DownLoadFragment;
import com.zhowin.basicframework.common.permission.AndPermissionListener;
import com.zhowin.basicframework.common.permission.AndPermissionUtils;
import com.zhowin.basicframework.common.utils.ActivityUtils;
import com.zhowin.viewlibrary.callback.OnAndroidDialogClickListener;
import com.zhowin.viewlibrary.dialog.AndroidDialog;
import com.zhowin.viewlibrary.dialog.HitIOSDialog;
import com.zhowin.viewlibrary.dialog.PasswordDialogUtils;

import java.util.List;

/**
 * author Z_B
 * date :2020/1/8 11:38
 * description:
 */
public class HomeFragment extends BaseFragment {


    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int loadViewLayout() {
        return R.layout.home_fragment_layout;
    }

    @Override
    public void bindViews(View contentView) {
        get(R.id.tvTypeOne).setOnClickListener(this);
        get(R.id.tvTypeTwo).setOnClickListener(this);
        get(R.id.tvTypeThree).setOnClickListener(this);
        get(R.id.tvTypeFour).setOnClickListener(this);
        get(R.id.tvTypeFive).setOnClickListener(this);
        get(R.id.tvTypeSix).setOnClickListener(this);
        get(R.id.tvTypeSeven).setOnClickListener(this);
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
                requestPermission(
                        Permission.READ_PHONE_STATE,
                        Permission.READ_EXTERNAL_STORAGE,
                        Permission.WRITE_EXTERNAL_STORAGE
                );
                break;
            case R.id.tvTypeSix:
                showMineLoadView();
                break;
            case R.id.tvTypeSeven:
                DownLoadFragment downLoadFragment = new DownLoadFragment();
                downLoadFragment.show(getChildFragmentManager(), "SS");
                break;

        }
    }

    private void requestPermission(String... permissions) {
        AndPermissionUtils.requestPermission(getActivity(), new AndPermissionListener() {
            @Override
            public void PermissionSuccess(List<String> permissions) {
                ActivityUtils.startActivity(LoginActivity.class);
            }

            @Override
            public void PermissionFailure(List<String> permissions) {

            }

            @Override
            public void OpenSystemSettings() {


            }
        }, permissions);

    }


    private void showMineLoadView() {
        showLoadDialog("加载中..");
        get(R.id.tvTypeOne).postDelayed(() -> dismissLoadDialog(), 3000);
    }


    private void setPasswordDialogOne() {
        PasswordDialogUtils.passwordDialog(mContext, password -> showToast("密码是=" + password));
    }

    private void setPasswordDialogTwo() {
        PasswordDialogUtils.passwordEditDialog(mContext, password -> showToast("密码是=" + password));
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
                .setPositiveButton("确定", view -> {
                }).show();
    }

}
