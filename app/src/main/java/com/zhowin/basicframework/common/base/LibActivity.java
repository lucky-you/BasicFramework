package com.zhowin.basicframework.common.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.zhowin.basicframework.R;
import com.zhowin.basicframework.common.dialog.NetworkChangedDialog;
import com.zhowin.basicframework.common.model.EventNotice;
import com.zhowin.basicframework.common.model.NetworkChangeEvent;
import com.zhowin.basicframework.common.utils.KeyboardUtils;
import com.zhowin.basicframework.common.utils.NetworkUtils;
import com.zhowin.basicframework.common.utils.ToastUtils;
import com.zhowin.basicframework.common.view.LoadProgressDialog;
import com.zhowin.basicframework.receiver.NetworkChangedReceiver;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public abstract class LibActivity extends AppCompatActivity implements LibBaseView {

    protected Context mContext;
    protected View mContentView;
    private long lastClickTime = 0;/*上一次点击时间*/
    private LoadProgressDialog progressDialog;/*加载进度提示框*/
    protected boolean mCheckNetwork = true;/*默认检查网络状态*/
    protected boolean mNetConnected;/*网络连接的状态，true表示有网络，flase表示无网络连接*/
    private NetworkChangedReceiver mNetWorkChangReceiver;/*网络状态变化的广播接收器*/
    private NetworkChangedDialog mNetStateChangedDialog;/*网络状态变化的提示对话框*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        ActivityManager.getAppInstance().addActivity(this);//将当前activity添加进入管理栈
        Bundle bundle = getIntent().getExtras();
        initData(bundle);
        setBaseView(loadViewLayout());
        bindViews(mContentView);
        processLogic(savedInstanceState);
        //监听网络变化的dialog
        registerEvent();
        mNetStateChangedDialog = new NetworkChangedDialog(this);
        //注册网络变化的广播
        mNetWorkChangReceiver = new NetworkChangedReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetWorkChangReceiver, filter);
    }


    protected void setBaseView(@LayoutRes int layoutId) {
        setContentView(mContentView = LayoutInflater.from(this).inflate(layoutId, null));
    }


    @Override
    protected void onResume() {
        super.onResume();
        networkStateChangedUI(NetworkUtils.isConnected());
    }

    /**
     * 网络状态发生变化时的处理
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetworkChangeEvent(NetworkChangeEvent event) {
        Log.e("xy", "网络发生变化:" + event.getNetworkType());
        mNetConnected = event.isConnected();
        networkStateChangedUI(event.isConnected());
    }

    /**
     * 根据网络状态显示或者隐藏提示对话框
     *
     * @param isConnected 是否连接
     */
    private void networkStateChangedUI(boolean isConnected) {
        if (mCheckNetwork) {
            if (isConnected) {
                if (null != mNetStateChangedDialog) mNetStateChangedDialog.dismiss();
            } else {
                if (null != mNetStateChangedDialog) mNetStateChangedDialog.show();
            }
        }
    }

    /**
     * 设置是否要检查网络状态变化
     *
     * @param checkNetWork
     */
    public void setCheckNetWork(boolean checkNetWork) {
        mCheckNetwork = checkNetWork;
    }

    @Override
    public void onClick(View view) {
        if (!isFastClick()) setClickListener(view);
    }

    /**
     * 判断是否快速点击
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    private boolean isFastClick() {
        long now = System.currentTimeMillis();
        if (now - lastClickTime >= 300) {
            lastClickTime = now;
            return false;
        }
        return true;
    }

    /**
     * 获取控件
     *
     * @param id  控件的id
     * @param <E>
     * @return
     */
    protected <E extends View> E get(int id) {
        return (E) findViewById(id);
    }

    /**
     * 界面跳转
     *
     * @param tarActivity
     */
    protected void intentToActivity(Class<? extends Activity> tarActivity) {
        Intent intent = new Intent(mContext, tarActivity);
        startActivity(intent);
    }

    /**
     * 显示Toast
     */
    protected void showToast(String msg) {
        ToastUtils.showLong(msg);
    }

    /**
     * 注册事件通知
     */
    public void registerEvent() {
        if (!EventBus.getDefault().isRegistered(this)) {//加上判断
            EventBus.getDefault().register(this);
        }
    }

    /**
     * 发送消息
     */
    public void EventPost(EventNotice notice) {
        EventBus.getDefault().post(notice);
    }

    @Override
    protected void onDestroy() {
        ActivityManager.getAppInstance().removeActivity(this);//将当前activity移除管理栈
        KeyboardUtils.fixInputMethodManagerLeak(this);
        unregisterReceiver(mNetWorkChangReceiver);
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }


    /**
     * 显示对话框
     */
    public LoadProgressDialog showLoadDialog(String hitMessage) {
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
    public void dismissLoadDialog() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.stopAnimator();
                progressDialog.dismiss();
            }
            progressDialog = null;
        }
    }
}
