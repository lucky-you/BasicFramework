package com.zhowin.basicframework.common.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhowin.basicframework.R;
import com.zhowin.basicframework.common.view.EmptyView;

/**
 * Created by : Z_B on 2017/12/21.
 * Effect : 加载空布局的帮助类
 */
public class EmptyViewUtils {

    /**
     * 绑定数据
     */
    public static void bindEmptyView(Context mContext, BaseQuickAdapter adapter) {
        bindEmptyView(mContext, adapter, 0, mContext.getString(R.string.not_have_data), true);
    }

    /**
     * 绑定空布局
     */
    public static void bindEmptyView(Context mContext, BaseQuickAdapter adapter, String emptyString) {
        bindEmptyView(mContext, adapter, 0, emptyString, true);
    }

    /**
     * 绑定空布局
     */
    public static View bindEmptyView(Context mContext, BaseQuickAdapter adapter, int imgIds, String emptyString, boolean isEmpty) {
        View emptyView = adapter.getEmptyView();
        if (emptyView == null) {
            emptyView = new EmptyView(mContext);
            adapter.setEmptyView(emptyView);
        }
        switchEmptyView(emptyView, imgIds, emptyString, isEmpty);
        return emptyView;
    }

    public static void switchEmptyView(View emptyView, int imgIds, String emptyString, boolean isEmpty) {
        ImageView imageView = emptyView.findViewById(R.id.ivHint);
        if (imgIds != 0) {
            imageView.setImageResource(imgIds);
        }
        TextView textView = emptyView.findViewById(R.id.tvHint);
        if (!TextUtils.isEmpty(emptyString)) {
            textView.setText(emptyString);
        }
        emptyView.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
    }


}
