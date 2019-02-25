package com.zhowin.viewlibrary.callback;

import android.app.Dialog;

/**
 * author      : Z_B
 * date       : 2019/2/23
 * function  : Dialog的点击事件的监听
 */
public interface OnAndroidDialogClickListener {

    void onNegativeClick(Dialog dialog);

    void onPositiveClick(Dialog dialog);
}
