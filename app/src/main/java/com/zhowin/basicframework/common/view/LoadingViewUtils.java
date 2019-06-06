package com.zhowin.basicframework.common.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.zhowin.viewlibrary.R;
import com.zhowin.viewlibrary.empty.LoadingController;
import com.zhowin.viewlibrary.empty.OnNextClickListener;


/**
 * author      : Z_B
 * date       : 2018/12/8
 * function  : 加载的布局
 */
public class LoadingViewUtils {

    public static LoadingController showLoadingView(Context mContext, RecyclerView rootView, final OnNextClickListener onNextClickListener) {
        LoadingController loadingController = new LoadingController.Builder(mContext, rootView)
                .setLoadingMessage(mContext.getString(R.string.LoadingController_loading_message))
                .setErrorMessage(mContext.getString(R.string.LoadingController_error_message))
                .setErrorImageResoruce(R.drawable.svg_data_error)
                .setEmptyMessage(mContext.getString(R.string.not_have_data))
                .setEmptyViewImageResource(R.drawable.svg_empty)
                .setOnNetworkErrorRetryClickListener(() -> {
                    if (onNextClickListener != null) {
                        onNextClickListener.onNextClick();
                    }
                })
                .setOnErrorRetryClickListener(mContext.getString(R.string.LoadingController_error_retry),
                        () -> {
                            if (onNextClickListener != null) {
                                onNextClickListener.onNextClick();
                            }
                        })
                .setOnEmptyTodoClickListener(() -> {
                    if (onNextClickListener != null) {
                        onNextClickListener.onNextClick();
                    }
                })
                .build();
        loadingController.showLoading();
        return loadingController;
    }
}
