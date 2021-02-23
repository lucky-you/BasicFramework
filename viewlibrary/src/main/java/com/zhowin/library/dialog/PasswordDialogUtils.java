package com.zhowin.library.dialog;

import android.content.Context;
import android.view.View;

import com.zhowin.library.view.CustomerKeyboard;
import com.zhowin.library.view.PasswordEditText;


/**
 * Created by ZhouBin on 2017/8/25.
 * Effect: 密码输入框的工具类
 */

public final class PasswordDialogUtils {

    public static PasswordDialogUtils alerDialogUtils;

    public PasswordDialogUtils() {
    }

    public static synchronized PasswordDialogUtils getInstance() {
        if (alerDialogUtils == null) {
            alerDialogUtils = new PasswordDialogUtils();
        }
        return alerDialogUtils;
    }

    /**
     * 默认的样式
     *
     * @param context              上下文
     * @param passwordFullListener 忘记密码按钮的监听
     * @return
     */
    public static PasswordEditTextDialog passwordDialog(Context context, PasswordEditText.PasswordFullListener passwordFullListener) {
        return passwordDialog(context, null, null, false, false, passwordFullListener, null);
    }

    /**
     * 只输入标题
     *
     * @param context              上下文
     * @param Title                标题
     * @param passwordFullListener 忘记密码按钮的监听
     * @return
     */
    public static PasswordEditTextDialog passwordDialog(Context context, String Title, PasswordEditText.PasswordFullListener passwordFullListener) {

        return passwordDialog(context, Title, null, false, false, passwordFullListener, null);
    }

    /**
     * 输入标题和提示信息
     *
     * @param context              上下文
     * @param Title                标题
     * @param hitMessage           提示信息
     * @param passwordFullListener 忘记密码按钮的监听
     * @return
     */
    public static PasswordEditTextDialog passwordDialog(Context context, String Title, String hitMessage, PasswordEditText.PasswordFullListener passwordFullListener) {

        return passwordDialog(context, Title, hitMessage, false, false, passwordFullListener, null);
    }

    /**
     * 修改标题和显示忘记密码
     *
     * @param context              上下文
     * @param Title                标题
     * @param isShowForgot         是否显示忘记密码按钮
     * @param passwordFullListener 密码输入框的监听
     * @param onClickListener      忘记密码按钮的监听
     * @return
     */
    public static PasswordEditTextDialog passwordDialog(Context context, String Title, boolean isShowForgot, PasswordEditText.PasswordFullListener passwordFullListener, View.OnClickListener onClickListener) {

        return passwordDialog(context, Title, null, false, isShowForgot, passwordFullListener, onClickListener);
    }

    /**
     * 修改标题,显示提示信息,显示忘记密码
     *
     * @param context              上下文
     * @param Title                标题
     * @param hitMessage           提示信息
     * @param isShowForgot         是否显示忘记密码按钮
     * @param passwordFullListener 密码输入框的监听
     * @param onClickListener      忘记密码按钮的监听
     * @return
     */
    public static PasswordEditTextDialog passwordDialog(Context context, String Title, String hitMessage, boolean isShowForgot, PasswordEditText.PasswordFullListener passwordFullListener, View.OnClickListener onClickListener) {

        return passwordDialog(context, Title, hitMessage, false, isShowForgot, passwordFullListener, onClickListener);
    }

    /**
     * 全部都显示
     *
     * @param context              上下文
     * @param Title                标题
     * @param hitMessage           提示信息
     * @param isShowCancel         是否显示取消按钮
     * @param isShowForgot         是否显示忘记密码按钮
     * @param passwordFullListener 密码输入框的监听
     * @param onClickListener      忘记密码按钮的监听
     * @return
     */
    public static PasswordEditTextDialog passwordDialog(Context context, String Title, String hitMessage, boolean isShowCancel, boolean isShowForgot, final PasswordEditText.PasswordFullListener passwordFullListener, final View.OnClickListener onClickListener) {
        final PasswordEditTextDialog passwordEditTextDialog = new PasswordEditTextDialog(context);
        passwordEditTextDialog.setTitle(Title);
        passwordEditTextDialog.setHitMessage(hitMessage);
        passwordEditTextDialog.setShowCancelView(isShowCancel);
        if (isShowForgot == true) {
            passwordEditTextDialog.showBottomTextView(isShowForgot);
            passwordEditTextDialog.setForgotPasswordClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onClick(v);
                    }
                    passwordEditTextDialog.dismiss();
                }
            });
        }
        passwordEditTextDialog.setPasswordClickListeners(new PasswordEditText.PasswordFullListener() {
            @Override
            public void passwordFull(String password) {
                if (passwordFullListener != null) {
                    passwordFullListener.passwordFull(password);
                }
                passwordEditTextDialog.dismiss();
            }
        });
        return passwordEditTextDialog;
    }

    /**
     * 自定义键盘的密码输入框
     *
     * @param context              上下文
     * @param passwordFullListener 密码输入框的监听
     * @return
     */
    public static PasswordEditAndKeyboardDialog passwordEditDialog(Context context, final PasswordEditText.PasswordFullListener passwordFullListener) {

        return passwordEditDialog(context, null, passwordFullListener);
    }

    /**
     * 自定义键盘的密码输入框
     *
     * @param context              上下文
     * @param title                标题
     * @param passwordFullListener 密码输入框的监听
     * @return
     */
    public static PasswordEditAndKeyboardDialog passwordEditDialog(Context context, String title, final PasswordEditText.PasswordFullListener passwordFullListener) {
        final PasswordEditAndKeyboardDialog passwordEditAndKeyboardDialog = new PasswordEditAndKeyboardDialog(context);
        passwordEditAndKeyboardDialog.setTitle(title);
        final PasswordEditText password_edit_text = passwordEditAndKeyboardDialog.getPasswordEdit();
        passwordEditAndKeyboardDialog.setPasswordClickListeners(new PasswordEditText.PasswordFullListener() {
            @Override
            public void passwordFull(String password) {
                if (passwordFullListener != null) {
                    passwordFullListener.passwordFull(password);
                }
                passwordEditAndKeyboardDialog.dismiss();
            }
        });
        passwordEditAndKeyboardDialog.customKeyBoard(new CustomerKeyboard.CustomerKeyboardClickListener() {
            @Override
            public void click(String number) {
                password_edit_text.addpassword(number);
            }

            @Override
            public void delete() {
                password_edit_text.deleteLastPassword();
            }
        });
        return passwordEditAndKeyboardDialog;
    }

}
