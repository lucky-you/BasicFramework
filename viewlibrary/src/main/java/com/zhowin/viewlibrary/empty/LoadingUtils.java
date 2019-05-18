package com.zhowin.viewlibrary.empty;

import android.content.Context;
import android.view.View;

import com.zhowin.viewlibrary.R;


/**
 * author      : Z_B
 * date       : 2018/12/8
 * function  : 加载的布局
 */
public class LoadingUtils {

    public static LoadingController showLoadingView(Context mContext, View rootView, final OnNextClickListener onNextClickListener) {
        LoadingController loadingController = new LoadingController.Builder(mContext, rootView)
                .setLoadingMessage(mContext.getString(R.string.LoadingController_loading_message))
                .setErrorMessage(mContext.getString(R.string.LoadingController_error_message))
                .setErrorImageResoruce(R.drawable.svg_data_error)
                .setOnNetworkErrorRetryClickListener(new LoadingInterface.OnClickListener() {
                    @Override
                    public void onClick() {
                        if (onNextClickListener != null) {
                            onNextClickListener.onNextClick();
                        }
                    }
                })
                .setOnErrorRetryClickListener(mContext.getString(R.string.LoadingController_error_retry),
                        new LoadingInterface.OnClickListener() {
                            @Override
                            public void onClick() {
                                if (onNextClickListener != null) {
                                    onNextClickListener.onNextClick();
                                }
                            }
                        })
                .build();
        loadingController.showLoading();
        return loadingController;
    }
}
