package com.zhowin.viewlibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhowin.viewlibrary.R;

/**
 * author      : Z_B
 * date       : 2018/10/26
 * function  : 仿IOS样式的Dialog
 */
public class HitIOSDialog {
    private Context context;
    private Dialog dialog;
    private LinearLayout lLayoutBackground;
    private TextView txt_title;
    private TextView txt_msg;
    private EditText edittxt_result;
    private LinearLayout dialog_Group;
    private ImageView dialog_marBottom;
    private TextView btn_neg;
    private TextView btn_pos;
    private ImageView img_line;
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
        txt_title = view.findViewById(R.id.txt_title);
        txt_title.setVisibility(View.GONE);
        txt_msg = view.findViewById(R.id.txt_msg);
        txt_msg.setVisibility(View.GONE);
        edittxt_result = view.findViewById(R.id.edittxt_result);
        edittxt_result.setVisibility(View.GONE);
        dialog_Group = view.findViewById(R.id.dialog_Group);
        dialog_Group.setVisibility(View.GONE);
        dialog_marBottom = view.findViewById(R.id.dialog_marBottom);
        btn_neg = view.findViewById(R.id.btn_neg);
        btn_neg.setVisibility(View.GONE);
        btn_pos = view.findViewById(R.id.btn_pos);
        btn_pos.setVisibility(View.GONE);
        img_line = view.findViewById(R.id.img_line);
        img_line.setVisibility(View.GONE);
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
            txt_title.setText(context.getString(R.string.The_title));
        } else {
            txt_title.setText(title);
        }
        return this;
    }

    public HitIOSDialog setEditHint(String hint) {
        showEditText = true;
        edittxt_result.setHint(hint);
        return this;
    }

    public HitIOSDialog setEditHeight(int height) {
        showEditText = true;
        edittxt_result.setHeight(height);
        return this;
    }

    public HitIOSDialog setEditMinLine(int minLine) {
        showEditText = true;
        edittxt_result.setMinLines(minLine);
        return this;
    }

    public HitIOSDialog setEditMaxLine(int maxLine) {
        showEditText = true;
        edittxt_result.setMaxLines(maxLine);
        return this;
    }

    public HitIOSDialog setEditMaxLength(int maxLength) {
        showEditText = true;
        edittxt_result.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        return this;
    }

    public HitIOSDialog setEditGravity(int gravity) {
        showEditText = true;
        edittxt_result.setGravity(gravity);
        return this;
    }

    public HitIOSDialog setEditTextSize(int spSize) {
        showEditText = true;
        edittxt_result.setTextSize(TypedValue.COMPLEX_UNIT_SP, spSize);
        return this;
    }

    public HitIOSDialog setEditText(String msg) {
        showEditText = true;
        edittxt_result.setText(msg);
        edittxt_result.setSelection(msg.length());
        return this;
    }

    public HitIOSDialog setEditInputType(int type) {
        showEditText = true;
        edittxt_result.setInputType(type);
        return this;
    }

    public HitIOSDialog setEditSelection(int index) {
        showEditText = true;
        edittxt_result.setSelection(index);
        return this;
    }

    public boolean isShowing() {
        return dialog.isShowing();
    }

    public String getResult() {
        return edittxt_result.getText().toString();
    }

    public EditText getEditText() {
        return edittxt_result;
    }

    public HitIOSDialog setMsg(String msg) {
        showMsg = true;
        if (TextUtils.equals("", msg)) {
            txt_msg.setText(context.getString(R.string.content));
        } else {
            txt_msg.setText(msg);
        }
        return this;
    }

    public HitIOSDialog setMsgTextColor(int color) {
        txt_msg.setTextColor(color);
        return this;
    }

    public HitIOSDialog setMsgOnClickListener(View.OnClickListener listener) {
        txt_msg.setOnClickListener(listener);
        return this;
    }

    public HitIOSDialog setView(View view) {
        showLayout = true;
        if (view == null) {
            showLayout = false;
        } else
            dialog_Group.addView(view,
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
            btn_pos.setText(context.getString(R.string.determine));
        } else {
            btn_pos.setText(text);
        }
        btn_pos.setOnClickListener(new View.OnClickListener() {
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
        btn_pos.setTextColor(color);
        return this;
    }

    public HitIOSDialog setNegativeButton(String text,
                                          final View.OnClickListener listener) {
        showNegBtn = true;
        if (TextUtils.isEmpty(text)) {
            btn_neg.setText(context.getString(R.string.cancel));
        } else {
            btn_neg.setText(text);
        }
        btn_neg.setOnClickListener(new View.OnClickListener() {
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
            txt_title.setText(context.getString(R.string.prompt));
            txt_title.setVisibility(View.VISIBLE);
        }
        if (showTitle) {
            txt_title.setVisibility(View.VISIBLE);
        }
        if (showEditText) {
            edittxt_result.setVisibility(View.VISIBLE);
        }
        if (showMsg) {
            txt_msg.setVisibility(View.VISIBLE);
        }
        if (showLayout) {
            dialog_Group.setVisibility(View.VISIBLE);
            dialog_marBottom.setVisibility(View.GONE);
        }
        if (!showPosBtn && !showNegBtn) {
            btn_pos.setText(context.getString(R.string.determine));
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.alertdialog_single_selector);
            btn_pos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
        if (showPosBtn && showNegBtn) {
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.alertdialog_right_selector);
            btn_neg.setVisibility(View.VISIBLE);
            btn_neg.setBackgroundResource(R.drawable.alertdialog_left_selector);
            img_line.setVisibility(View.VISIBLE);
        }
        if (showPosBtn && !showNegBtn) {
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.alertdialog_single_selector);
        }

        if (!showPosBtn && showNegBtn) {
            btn_neg.setVisibility(View.VISIBLE);
            btn_neg.setBackgroundResource(R.drawable.alertdialog_single_selector);
        }
    }

    public void show() {
        setLayout();
        dialog.show();
    }
}
