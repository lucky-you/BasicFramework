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
    private TextView tvDetermineTxt;
    private TextView tvCancelTxt;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.include_android_dialog_layout);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        initViews();
    }

    private void initViews() {
        tvTitleTxt = findViewById(R.id.tvTitleText);
        tvContentTxt = findViewById(R.id.tvContent);
        tvDetermineTxt = findViewById(R.id.tvDetermine);
        tvDetermineTxt.setOnClickListener(this);
        tvCancelTxt = findViewById(R.id.tvCancel);
        tvCancelTxt.setOnClickListener(this);
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
            tvDetermineTxt.setText(positiveName);
        }
        if (!TextUtils.isEmpty(negativeName)) {
            tvCancelTxt.setText(negativeName);
        }
        if (titleTextColor != 0) {
            tvTitleTxt.setTextColor(titleTextColor);
        }
        if (contentTextColor != 0) {
            tvContentTxt.setTextColor(contentTextColor);
        }
        if (positiveTextColor != 0) {
            tvDetermineTxt.setTextColor(positiveTextColor);
        }
        if (negativeTextColor != 0) {
            tvCancelTxt.setTextColor(negativeTextColor);
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
