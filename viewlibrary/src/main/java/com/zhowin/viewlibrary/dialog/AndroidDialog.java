package com.zhowin.viewlibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zhowin.viewlibrary.R;
import com.zhowin.viewlibrary.callback.OnAndroidDialogClickListener;

/**
 * author      : Z_B
 * date       : 2019/2/23
 * function  : 自定义安卓风格的DiaLog
 */
public class AndroidDialog extends Dialog implements View.OnClickListener {

    private TextView tvTitleTxt;
    private TextView tvContentTxt;
    private TextView tvPositiveTxt;
    private TextView tvNegativeTxt;
    private Context mContext;
    private OnAndroidDialogClickListener onAndroidDialogClickListener;
    private String title;
    private String content;
    private String positiveName;
    private String negativeName;
    private int titleTextColor;
    private int contentTextColor;
    private int positiveTextColor;
    private int negativeTextColor;

    public AndroidDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public AndroidDialog(Context context, String content) {
        super(context, R.style.AndroidDialogStyle);
        this.mContext = context;
        this.content = content;
    }

    public AndroidDialog(Context context, String content, OnAndroidDialogClickListener listener) {
        super(context, R.style.AndroidDialogStyle);
        this.mContext = context;
        this.content = content;
        this.onAndroidDialogClickListener = listener;
    }

    public AndroidDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public AndroidDialog setContent(String content) {
        this.content = content;
        return this;
    }

    public AndroidDialog setPositiveButton(String name) {
        this.positiveName = name;
        return this;
    }

    public AndroidDialog setNegativeButton(String name) {
        this.negativeName = name;
        return this;
    }

    public AndroidDialog setTitleTextColor(int color) {
        this.titleTextColor = color;
        return this;
    }

    public AndroidDialog setContentTextColor(int color) {
        this.contentTextColor = color;
        return this;
    }

    public AndroidDialog setPositiveTextColor(int color) {
        this.positiveTextColor = color;
        return this;
    }

    public AndroidDialog setNegativeTextColor(int color) {
        this.negativeTextColor = color;
        return this;
    }

    public AndroidDialog setCancelableThat(boolean cancel) {
        this.setCancelable(cancel);
        this.setCanceledOnTouchOutside(cancel);
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.include_android_dialog_layout);
        initViews();
    }

    private void initViews() {
        tvTitleTxt = findViewById(R.id.tvTitleText);
        tvContentTxt = findViewById(R.id.tvContent);
        tvPositiveTxt = findViewById(R.id.tvDetermine);
        tvPositiveTxt.setOnClickListener(this);
        tvNegativeTxt = findViewById(R.id.tvCancel);
        tvNegativeTxt.setOnClickListener(this);
        if (TextUtils.isEmpty(title)) {
            tvTitleTxt.setVisibility(View.GONE);
        } else {
            tvTitleTxt.setVisibility(View.VISIBLE);
            tvTitleTxt.setText(title);
        }
        if (!TextUtils.isEmpty(content)) {
            tvContentTxt.setText(content);
        }
        if (!TextUtils.isEmpty(positiveName)) {
            tvPositiveTxt.setText(positiveName);
        }
        if (!TextUtils.isEmpty(negativeName)) {
            tvNegativeTxt.setText(negativeName);
        }
        if (titleTextColor != 0) {
            tvTitleTxt.setTextColor(titleTextColor);
        }
        if (contentTextColor != 0) {
            tvContentTxt.setTextColor(contentTextColor);
        }
        if (positiveTextColor != 0) {
            tvPositiveTxt.setTextColor(positiveTextColor);
        }
        if (negativeTextColor != 0) {
            tvNegativeTxt.setTextColor(negativeTextColor);
        }
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.tvCancel) {
            if (onAndroidDialogClickListener != null) {
                onAndroidDialogClickListener.onNegativeClick(this);
            }
        } else if (i == R.id.tvDetermine) {
            if (onAndroidDialogClickListener != null) {
                onAndroidDialogClickListener.onPositiveClick(this);
            }
        }
        this.dismiss();
    }
}
