package com.zhowin.basicframework.common.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhowin.basicframework.R;
import com.zhowin.viewlibrary.view.MiniLoadingView;

/**
 * author      : Z_B
 * date       : 2017/6/14
 * function  : 加载进度的Dialog
 */
public class LoadProgressDialog extends Dialog {


    private Context context = null;
    private LoadProgressDialog progressDialog = null;

    public LoadProgressDialog(Context context) {
        super(context);
        this.context = context;
    }

    public LoadProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    public LoadProgressDialog createLoadingDialog(String msg) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.include_progress_dialog_layout, null);
        LinearLayout layout = view.findViewById(R.id.dialog_view);
        MiniLoadingView miniLoadingView = view.findViewById(R.id.miniLoadingView);
        TextView tipTextView = view.findViewById(R.id.tvLoadText);
//        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context, R.anim.progress_dialog_anim); // 加载动画
//        spaceshipImage.startAnimation(hyperspaceJumpAnimation); // 使用ImageView显示动画
        miniLoadingView.start();
        tipTextView.setText(msg);
        progressDialog = new LoadProgressDialog(context, R.style.myProgressDialog);// 创建自定义样式dialog
        progressDialog.setCanceledOnTouchOutside(false);//点击外围不可消失
        progressDialog.setCancelable(false);// 不可以用“返回键”取消
        progressDialog.setContentView(layout, new LinearLayout.LayoutParams(dip2px(context, 120), dip2px(context, 110)));
        return progressDialog;
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
