package com.zhowin.viewlibrary.callback;

import com.zhowin.viewlibrary.view.ActionSheet;

/**
 * author      : Z_B
 * date       : 2019/3/5
 * function  : ActionSheet的监听
 */
public interface ActionSheetListener {


    void onDismiss(ActionSheet actionSheet, boolean isCancel);


    void onOtherButtonClick(ActionSheet actionSheet, int index);
}
