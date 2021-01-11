package com.zhowin.basicframework.common.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.zhowin.basicframework.R;
import com.zhowin.basicframework.common.base.BaseFragment;
import com.zhowin.basicframework.common.download.BottomShowFragment;
import com.zhowin.basicframework.common.download.DownloadStatusListener;
import com.zhowin.basicframework.common.download.DownloadUtil;
import com.zhowin.viewlibrary.callback.OnAndroidDialogClickListener;
import com.zhowin.viewlibrary.dialog.AndroidDialog;
import com.zhowin.viewlibrary.dialog.HitIOSDialog;
import com.zhowin.viewlibrary.dialog.PasswordDialogUtils;


/**
 * author Z_B
 * date :2020/1/8 11:38
 * description:
 */
public class HomeFragment extends BaseFragment {

    private TextView tvTypeSeven;
    private int index;

    public static HomeFragment newInstance(int index) {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        fragment.setArguments(bundle);
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
        index = getArguments().getInt("index");
        get(R.id.tvTypeOne).setOnClickListener(this);
        get(R.id.tvTypeTwo).setOnClickListener(this);
        get(R.id.tvTypeThree).setOnClickListener(this);
        get(R.id.tvTypeFour).setOnClickListener(this);
        get(R.id.tvTypeFive).setOnClickListener(this);
        get(R.id.tvTypeSix).setOnClickListener(this);
        tvTypeSeven = get(R.id.tvTypeSeven);
        tvTypeSeven.setOnClickListener(this);
        tvTypeSeven.setText("DialogFragment的基类处理" + index);
    }


    @Override
    public void processLogic(Bundle savedInstanceState) {

    }

    @Override
    public void onViewClick(View view) {
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

                break;
            case R.id.tvTypeSix:
                showMineLoadView();
                break;
            case R.id.tvTypeSeven:
                BottomShowFragment bottomShowFragment = new BottomShowFragment();
                bottomShowFragment.show(getChildFragmentManager(), "SS");
                break;

        }
    }




    private void showMineLoadView() {
        downLoadImage();
    }

    private void downLoadImage() {
        String url = "https://images5.alphacoders.com/601/thumb-1920-601569.jpg";
        DownloadUtil downloadUtil = new DownloadUtil();
        downloadUtil.downloadFile(url, new DownloadStatusListener() {
            @Override
            public void onStart() {
                showLoadDialog("加载中..");
            }

            @Override
            public void onProgress(int currentLength) {
                Log.e("xy", "currentLength：" + currentLength);
            }

            @Override
            public void onFinish(String localPath) {
                Log.e("xy", "localPath：" + localPath);
                dismissLoadDialog();
            }

            @Override
            public void onFailure(String errorInfo) {
                Log.e("xy", "errorInfo：" + errorInfo);
            }
        });
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
                .setTitle("友情提示")
                .setPositiveButton("确定")
                .setNegativeButton("取消")
                .setCancelableThat(false)
                .show();
    }

    private void shoIosDialog() {
        HitIOSDialog hitIOSDialog = new HitIOSDialog(mContext).builder();
        hitIOSDialog
                .setTitle("友情提示")
                .setContent("您确定删除此信息？")
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .show();
    }

}
