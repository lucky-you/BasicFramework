package com.zhowin.library.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhowin.library.R;

/**
 * author      : Z_B
 * date       : 2018/10/26
 * function  : 仿IOS样式的Dialog
 */
public class HitIOSDialog {
    private Context context;
    private Dialog dialog;
    private LinearLayout lLayoutBackground;
    private TextView textTitle;
    private TextView textContent;
    private EditText editTextResult;
    private LinearLayout dialogGroup;
    private ImageView dialogMarBottom;
    private TextView btnNegative;
    private TextView btnPositive;
    private ImageView imgLine;
    private Display display;
    private boolean showTitle = false;
    private boolean showMsg = false;
    private boolean showEditText = false;
    private boolean showLayout = false;
    private boolean showPosBtn = false;
    private boolean showNegBtn = false;

    public HitIOSDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public HitIOSDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(R.layout.include_hit_ios_dialog_layout, null);
        // 获取自定义Dialog布局中的控件
        lLayoutBackground = view.findViewById(R.id.lLayout_bg);
        textTitle = view.findViewById(R.id.txt_title);
        textTitle.setVisibility(View.GONE);
        textContent = view.findViewById(R.id.txt_msg);
        textContent.setVisibility(View.GONE);
        editTextResult = view.findViewById(R.id.edit_text_result);
        editTextResult.setVisibility(View.GONE);
        dialogGroup = view.findViewById(R.id.dialog_Group);
        dialogGroup.setVisibility(View.GONE);
        dialogMarBottom = view.findViewById(R.id.dialog_marBottom);
        btnNegative = view.findViewById(R.id.btn_neg);
        btnNegative.setVisibility(View.GONE);
        btnPositive = view.findViewById(R.id.btn_pos);
        btnPositive.setVisibility(View.GONE);
        imgLine = view.findViewById(R.id.img_line);
        imgLine.setVisibility(View.GONE);
        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.AndroidDialogStyle);
        dialog.setContentView(view);
        // 调整dialog背景大小
        lLayoutBackground.setLayoutParams(new FrameLayout.LayoutParams((int) (display.getWidth() * 0.85),
                LinearLayout.LayoutParams.WRAP_CONTENT));
        return this;
    }

    public HitIOSDialog setTitle(String title) {
        showTitle = true;
        if (TextUtils.isEmpty(title)) {
            textTitle.setText(context.getString(R.string.The_title));
        } else {
            textTitle.setText(title);
        }
        return this;
    }

    public HitIOSDialog setEditHint(String hint) {
        showEditText = true;
        editTextResult.setHint(hint);
        return this;
    }

    public HitIOSDialog setEditHeight(int height) {
        showEditText = true;
        editTextResult.setHeight(height);
        return this;
    }

    public HitIOSDialog setEditMinLine(int minLine) {
        showEditText = true;
        editTextResult.setMinLines(minLine);
        return this;
    }

    public HitIOSDialog setEditMaxLine(int maxLine) {
        showEditText = true;
        editTextResult.setMaxLines(maxLine);
        return this;
    }

    public HitIOSDialog setEditMaxLength(int maxLength) {
        showEditText = true;
        editTextResult.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        return this;
    }

    public HitIOSDialog setEditGravity(int gravity) {
        showEditText = true;
        editTextResult.setGravity(gravity);
        return this;
    }

    public HitIOSDialog setEditTextSize(int spSize) {
        showEditText = true;
        editTextResult.setTextSize(TypedValue.COMPLEX_UNIT_SP, spSize);
        return this;
    }

    public HitIOSDialog setEditText(String msg) {
        showEditText = true;
        editTextResult.setText(msg);
        editTextResult.setSelection(msg.length());
        return this;
    }

    public HitIOSDialog setEditInputType(int type) {
        showEditText = true;
        editTextResult.setInputType(type);
        return this;
    }

    public HitIOSDialog setEditSelection(int index) {
        showEditText = true;
        editTextResult.setSelection(index);
        return this;
    }

    public boolean isShowing() {
        return dialog.isShowing();
    }

    public String getResult() {
        return editTextResult.getText().toString();
    }

    public EditText getEditText() {
        return editTextResult;
    }

    public HitIOSDialog setContent(String msg) {
        showMsg = true;
        if (TextUtils.equals("", msg)) {
            textContent.setText(context.getString(R.string.content));
        } else {
            textContent.setText(msg);
        }
        return this;
    }

    public HitIOSDialog setContentTextColor(int color) {
        textContent.setTextColor(color);
        return this;
    }

    public HitIOSDialog setContentOnClickListener(View.OnClickListener listener) {
        textContent.setOnClickListener(listener);
        return this;
    }

    public HitIOSDialog setView(View view) {
        showLayout = true;
        if (view == null) {
            showLayout = false;
        } else
            dialogGroup.addView(view,
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT);
        return this;
    }

    public HitIOSDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public HitIOSDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

    public HitIOSDialog setPositiveButton(String text,
                                          final View.OnClickListener listener) {
        showPosBtn = true;
        if (TextUtils.isEmpty(text)) {
            btnPositive.setText(context.getString(R.string.determine));
        } else {
            btnPositive.setText(text);
        }
        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dismiss();
            }
        });
        return this;
    }

    public HitIOSDialog setPositiveButtonColor(int color) {
        showPosBtn = true;
        btnPositive.setTextColor(color);
        return this;
    }

    public HitIOSDialog setNegativeButton(String text,
                                          final View.OnClickListener listener) {
        showNegBtn = true;
        if (TextUtils.isEmpty(text)) {
            btnNegative.setText(context.getString(R.string.cancel));
        } else {
            btnNegative.setText(text);
        }
        btnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    private void setLayout() {
        if (!showTitle && !showMsg) {
            textTitle.setText(context.getString(R.string.prompt));
            textTitle.setVisibility(View.VISIBLE);
        }
        if (showTitle) {
            textTitle.setVisibility(View.VISIBLE);
        }
        if (showEditText) {
            editTextResult.setVisibility(View.VISIBLE);
        }
        if (showMsg) {
            textContent.setVisibility(View.VISIBLE);
        }
        if (showLayout) {
            dialogGroup.setVisibility(View.VISIBLE);
            dialogMarBottom.setVisibility(View.GONE);
        }
        if (!showPosBtn && !showNegBtn) {
            btnPositive.setText(context.getString(R.string.determine));
            btnPositive.setVisibility(View.VISIBLE);
            btnPositive.setBackgroundResource(R.drawable.alertdialog_single_selector);
            btnPositive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
        if (showPosBtn && showNegBtn) {
            btnPositive.setVisibility(View.VISIBLE);
            btnPositive.setBackgroundResource(R.drawable.alertdialog_right_selector);
            btnNegative.setVisibility(View.VISIBLE);
            btnNegative.setBackgroundResource(R.drawable.alertdialog_left_selector);
            imgLine.setVisibility(View.VISIBLE);
        }
        if (showPosBtn && !showNegBtn) {
            btnPositive.setVisibility(View.VISIBLE);
            btnPositive.setBackgroundResource(R.drawable.alertdialog_single_selector);
        }

        if (!showPosBtn && showNegBtn) {
            btnNegative.setVisibility(View.VISIBLE);
            btnNegative.setBackgroundResource(R.drawable.alertdialog_single_selector);
        }
    }

    public void show() {
        setLayout();
        dialog.show();
    }
}
