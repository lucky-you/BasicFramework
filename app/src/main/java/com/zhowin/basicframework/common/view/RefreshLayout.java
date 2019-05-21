package com.zhowin.basicframework.common.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

import com.zhowin.basicframework.R;

/**
 * author      : Z_B
 * date       : 2019/5/21
 * function  :
 */
public class RefreshLayout extends SwipeRefreshLayout {
    public RefreshLayout(@NonNull Context context) {
        this(context,null);
    }

    public RefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setColorSchemeResources(R.color.colorPrimary,R.color.colorPrimaryDark,R.color.colorPrimary);
    }
}
