package com.zhowin.basicframework.common.permission;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import com.zhowin.basicframework.R;

import java.util.List;

/**
 * author      : Z_B
 * date       : 2018/8/31
 * function  : 请求权限的工具类
 */
public class AndPermissionUtils {

    /**
     * 单个权限
     * @param context               上下文
     * @param permissions           权限
     * @param andPermissionListener 回调
     */
    public static void requestPermission(final Context context, final AndPermissionListener andPermissionListener, String... permissions) {
        AndPermission.with(context)
                .runtime()
                .permission(permissions)
                .rationale(new RuntimeRationale())
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        if (andPermissionListener != null) {
                            andPermissionListener.PermissionSuccess(permissions);
                        }

                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {
                        if (andPermissionListener != null) {
                            andPermissionListener.PermissionFailure(permissions);
                        }
                        if (AndPermission.hasAlwaysDeniedPermission(context, permissions)) {
                            showSettingDialog(context, permissions, andPermissionListener);
                        }
                    }
                })
                .start();
    }

    /**
     * 用户禁止了权限，并且勾选了禁止后不在询问，显示的dialog
     *
     * @param context     上下文
     * @param permissions 权限
     */
    private static void showSettingDialog(final Context context, final List<String> permissions, AndPermissionListener andPermissionListener) {
        List<String> permissionNames = Permission.transformText(context, permissions);
        String message = context.getString(R.string.message_permission_always_failed, TextUtils.join("\n", permissionNames));

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setCancelable(false)
                .setTitle(R.string.title_dialog)
                .setMessage(message)
                .setPositiveButton(R.string.setting, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (andPermissionListener != null) {
                            andPermissionListener.OpenSystemSettings();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
        dialog.show();
        dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);

    }

}
