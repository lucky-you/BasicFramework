package com.zhowin.basicframework.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * author      : Z_B
 * date       : 2018/11/16
 * function  : 基础接口，全局通用
 */
public interface LibBaseView extends View.OnClickListener {

    /**
     * 初始化数据
     *
     * @param bundle 传递过来的 bundle
     */
    void initData(@Nullable final Bundle bundle);

    /**
     * 绑定布局
     *
     * @return 布局 Id
     */
    int loadViewLayout();

    /**
     * 初始化 view
     */
    void bindViews(final View contentView);

    /**
     * 业务操作
     */
    void processLogic(final Bundle savedInstanceState);

    /**
     * 视图点击事件
     *
     * @param view 视图
     */
    void onViewClick(final View view);

}
