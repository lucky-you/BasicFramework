package com.zhowin.basicframework.common.lib;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.zhowin.basicframework.R;
import com.zhowin.basicframework.common.base.BaseApplication;
import com.zhowin.basicframework.common.model.EventNotice;
import com.zhowin.basicframework.common.utils.ToastUtils;
import com.zhowin.basicframework.common.view.LoadProgressDialog;

import org.greenrobot.eventbus.EventBus;

public abstract class LibFragment extends Fragment implements BaseView {
    private static final String TAG = "LibFragment";
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";
    protected Activity mContext;
    protected BaseApplication application;
    protected View mRootView;
    protected boolean isFirst = true;//是否第一次加载
    private long lastClick = 0;
    private boolean isViewCreate = false;//view是否创建
    private boolean isViewVisible = false;//view是否可见
    private LoadProgressDialog  progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isSupportHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commitAllowingStateLoss();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setBaseView(inflater, loadViewLayout());
        return mRootView;
    }

    protected void setBaseView(@NonNull LayoutInflater inflater, @LayoutRes int layoutId) {
        if (layoutId <= 0) return;
        mRootView = inflater.inflate(layoutId, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        isViewCreate = true;
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        bindViews(mRootView);
        processLogic(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        application = (BaseApplication) getActivity().getApplication();
        mRootView = view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Log.d(TAG, "onSaveInstanceState: ");
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        isViewVisible = isVisibleToUser;
        if (isVisibleToUser && isViewCreate) {
            visibleToUser();
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onResume() {
        if (isViewVisible) {
            visibleToUser();
        }
        super.onResume();
    }

    /**
     * 懒加载
     * 让用户可见
     * 第一次加载
     */
    protected void firstLoad() {

    }

    /**
     * 懒加载
     * 让用户可见
     */
    protected void visibleToUser() {
        if (isFirst) {
            firstLoad();
            isFirst = false;
        }
    }


    protected void intentToActivity(Class<? extends Activity> tarActivity) {
        Intent intent = new Intent(mContext, tarActivity);
        startActivity(intent);
    }

    protected void showToast(String msg) {
        ToastUtils.showToast(msg);
    }


    private boolean isFastClick() {
        long now = System.currentTimeMillis();
        if (now - lastClick >= 800) {
            lastClick = now;
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        if (!isFastClick()) setClickListener(view);
    }


    protected <E extends View> E get(int id) {
        return (E) mRootView.findViewById(id);
    }

    public void registerEvent() {
        EventBus.getDefault().register(this);
    }

    public void EventPost(EventNotice notice) {
        EventBus.getDefault().post(notice);
    }


    @Override
    public void onDestroyView() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (mRootView != null) {
            ((ViewGroup) mRootView.getParent()).removeView(mRootView);
        }
        super.onDestroyView();
    }


    /**
     * 显示对话框
     */
    public LoadProgressDialog showDialog(String hitMessage) {
        if (progressDialog == null) {
            progressDialog = new LoadProgressDialog(mContext);
            if (TextUtils.isEmpty(hitMessage)) {
                progressDialog = progressDialog.createLoadingDialog(mContext.getString(R.string.In_the_load));
            } else {
                progressDialog = progressDialog.createLoadingDialog(hitMessage);
            }
            progressDialog.show();
        } else if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
        return progressDialog;
    }

    /**
     * 关闭提示框
     */
    public void dismissDialog() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            progressDialog = null;
        }
    }
}
