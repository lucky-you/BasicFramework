package com.zhowin.library.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhowin.library.R;
import com.zhowin.library.view.CustomerKeyboard;
import com.zhowin.library.view.PasswordEditText;


/**
 * Created by ZhouBin on 2017/8/25.
 * Effect: 密码输入框的dialog(自带软键盘)
 */

public class PasswordEditAndKeyboardDialog extends Dialog implements View.OnClickListener {

    private ImageView ivCloseDialog;
    private TextView tvPasswordDialogTitle;
    private PasswordEditText passwordEditText;
    private CustomerKeyboard customKeyBoard;
    private Context mContext;

    public PasswordEditAndKeyboardDialog(Context context) {
        super(context);
        mContext = context;
        Window window = this.getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        View popupView = View.inflate(context, R.layout.include_customer_keyboard_dialog_view, null);
        window.setContentView(popupView);
        initViews(popupView);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.windowAnimations = R.style.bottomInWindowAnim;
        layoutParams.gravity = Gravity.BOTTOM;
        window.setAttributes(layoutParams);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        PasswordEditAndKeyboardDialog.this.show();
    }

    /**
     * 初始化控件
     *
     * @param popupView view
     */
    private void initViews(View popupView) {
        ivCloseDialog = popupView.findViewById(R.id.delete_dialog);
        tvPasswordDialogTitle = popupView.findViewById(R.id.tv_password_title);
        passwordEditText = popupView.findViewById(R.id.password_edit_text);
        customKeyBoard = popupView.findViewById(R.id.custom_key_board);
        ivCloseDialog.setOnClickListener(this);

    }

    /**
     * 返回PasswordEditText
     *
     * @return PasswordEditText
     */
    public PasswordEditText getPasswordEdit() {
        if (passwordEditText == null) {
            return new PasswordEditText(mContext);
        }
        return passwordEditText;

    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            tvPasswordDialogTitle.setText(title);
        }
    }

    /**
     * 软键盘的监听回调
     *
     * @param customerKeyboardClickListener
     */
    public void customKeyBoard(CustomerKeyboard.CustomerKeyboardClickListener customerKeyboardClickListener) {
        customKeyBoard.setOnCustomerKeyboardClickListener(customerKeyboardClickListener);
    }

    /**
     * 输入框监听回调
     * @param passwordFullListener
     */
    public void setPasswordClickListeners(PasswordEditText.PasswordFullListener passwordFullListener) {
        passwordEditText.setOnPasswordFullListener(passwordFullListener);
    }

    @Override
    public void onClick(View view) {
        PasswordEditAndKeyboardDialog.this.dismiss();
    }
}
