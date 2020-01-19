package com.zhowin.viewlibrary.callback;

import android.app.Dialog;

/**
 * author      : Z_B
 * date       : 2019/2/23
 * function  : Dialog的点击事件的监听
 */
public interface OnAndroidDialogClickListener {

    /**
     * 取消
     *
     * @param dialog dialog对象
     */
    void onNegativeClick(Dialog dialog);

    /**
     * 确定
     *
     * @param dialog dialog对象
     */
    void onPositiveClick(Dialog dialog);
}
