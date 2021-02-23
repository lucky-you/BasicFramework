package com.zhowin.library.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhowin.library.R;
import com.zhowin.library.view.PasswordEditText;


/**
 * Created by ZhouBin on 2017/8/2.
 * Effect: 密码输入的dialog(使用系统的软键盘)
 */

public class PasswordEditTextDialog extends Dialog implements View.OnClickListener {
    private Context mContext;
    private Window window;
    private Display display;
    private ImageView ivCloseDialog;
    private TextView tvTitle, tvHitMess, tvForgotPassword;
    private PasswordEditText passwordEdit;


    public PasswordEditTextDialog(Context context) {
        super(context);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        initViews(context);
    }

    //初始化控件
    private void initViews(Context context) {
        mContext = context;
        View passwordView = View.inflate(context, R.layout.include_edittext_password_dialog_view, null);
        ivCloseDialog = passwordView.findViewById(R.id.iv_cancel);
        tvTitle = passwordView.findViewById(R.id.tv_title);
        tvHitMess = passwordView.findViewById(R.id.tv_withdrawal_money);
        tvForgotPassword = passwordView.findViewById(R.id.tv_Forgot_password);
        passwordEdit = passwordView.findViewById(R.id.password_edit_text);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(passwordView);
        window = getWindow();
        window.setWindowAnimations(android.R.style.Animation_InputMethod);
        window.getDecorView().setBackgroundResource(android.R.color.transparent);
        setWidth((int) (display.getWidth() * 0.9));
        PasswordEditTextDialog.this.show();

    }

    /**
     * 设置标题
     *
     * @param title 标题信息
     */
    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }
    }

    /**
     * 设置提示信息
     *
     * @param hitMessage 提示信息
     */
    public void setHitMessage(String hitMessage) {
        if (!TextUtils.isEmpty(hitMessage)) {
            tvHitMess.setVisibility(View.VISIBLE);
            tvHitMess.setText(mContext.getString(R.string.dialog_withdrawal_money, hitMessage));
        }
    }

    /**
     * 是否显示取消按钮的view
     * 默认不显示
     *
     * @param isShow 是否显示
     */
    public void setShowCancelView(boolean isShow) {
        if (isShow == true) {
            ivCloseDialog.setVisibility(View.VISIBLE);
            ivCloseDialog.setOnClickListener(this);
        } else {
            ivCloseDialog.setVisibility(View.GONE);
        }
    }

    /**
     * 是否显示忘记密码的view
     * 默认不显示
     *
     * @param isShow 是否显示
     */
    public void showBottomTextView(boolean isShow) {
        if (isShow == true) {
            tvForgotPassword.setVisibility(View.VISIBLE);
        } else {
            tvForgotPassword.setVisibility(View.GONE);
        }
    }

    /**
     * 设置宽度
     *
     * @param width
     * @return
     */
    public PasswordEditTextDialog setWidth(int width) {
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = width;
        window.setAttributes(lp);
        return this;
    }

    /**
     * 输入框监听回调
     * @param passwordFullListener
     */
    public void setPasswordClickListeners(PasswordEditText.PasswordFullListener passwordFullListener) {
        passwordEdit.setOnPasswordFullListener(passwordFullListener);
    }

    /**
     * 忘记密码
     *
     * @param onClickListener
     */
    public void setForgotPasswordClickListener(View.OnClickListener onClickListener) {
        tvForgotPassword.setOnClickListener(onClickListener);
    }

    @Override
    public void onClick(View view) {
        PasswordEditTextDialog.this.dismiss();
    }
}
