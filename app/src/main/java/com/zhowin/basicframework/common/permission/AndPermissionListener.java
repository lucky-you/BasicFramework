package com.zhowin.basicframework.common.permission;

import java.util.List;

/**
 * author      : Z_B
 * date       : 2018/9/6
 * function  : 获取权限的回调
 */
public interface AndPermissionListener {

    /**
     * 获取权限成功
     *
     * @param permissions
     */
    void PermissionSuccess(List<String> permissions);

    /**
     * 获取权限失败
     *
     * @param permissions
     */
    void PermissionFailure(List<String> permissions);

    /**
     *打开手机设置界面，手动开启权限
     */

    void OpenSystemSettings();
}
