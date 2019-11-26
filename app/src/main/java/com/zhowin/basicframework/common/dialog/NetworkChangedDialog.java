package com.zhowin.basicframework.common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.zhowin.basicframework.R;

/**
 * author Z_B
 * date :2019/11/26 9:30
 * description: 网络发生变化的dialog
 */
public class NetworkChangedDialog extends Dialog {
    public NetworkChangedDialog(@NonNull Context context) {
        super(context, R.style.StyleNetChangedDialog);
        View contentView = LayoutInflater.from(context).inflate(R.layout.include_network_state_dailog_layout, null);
        initView(contentView);
        setContentView(contentView);
        setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.TOP;
        layoutParams.windowAnimations = R.style.StyleNetChangedDialog_Animation;
        getWindow().setAttributes(layoutParams);
        getWindow().setDimAmount(0f);/*使用时设置窗口后面的暗淡量*/
    }

    private void initView(View contentView) {

    }
}
