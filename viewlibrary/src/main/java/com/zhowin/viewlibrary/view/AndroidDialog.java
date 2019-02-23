package com.zhowin.viewlibrary.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

    public AndroidDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public AndroidDialog(Context context, int themeResId, String content) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
    }

    public AndroidDialog(Context context, int themeResId, String content, OnAndroidDialogClickListener listener) {
        super(context, themeResId);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.include_android_dialog_layout);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        initView();
    }

    private void initView() {
        tvTitleTxt = findViewById(R.id.tvTitle);
        tvContentTxt = findViewById(R.id.tvContent);
        tvDetermineTxt = findViewById(R.id.tvDetermine);
        tvDetermineTxt.setOnClickListener(this);
        tvCancelTxt = findViewById(R.id.tvCancel);
        tvCancelTxt.setOnClickListener(this);
        tvContentTxt.setText(content);
        if (!TextUtils.isEmpty(positiveName)) {
            tvDetermineTxt.setText(positiveName);
        }
        if (!TextUtils.isEmpty(negativeName)) {
            tvCancelTxt.setText(negativeName);
        }
        if (!TextUtils.isEmpty(title)) {
            tvTitleTxt.setText(title);
        }
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.tvCancel) {
            if (onAndroidDialogClickListener != null) {
                onAndroidDialogClickListener.onCancelClick(this);
            }
        } else if (i == R.id.tvDetermine) {
            if (onAndroidDialogClickListener != null) {
                onAndroidDialogClickListener.onDetermineClick(this);
            }
        }
        this.dismiss();
    }
}
