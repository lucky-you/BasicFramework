package com.zhowin.viewlibrary.callback;

import android.support.v4.widget.NestedScrollView;

/**
 * author      : Z_B
 * date       : 2018/7/15
 * function  : 滑动监听
 */
public interface NestedScrollViewListener {

    /*** 在滑动的时候调用*/
    void onScrollChanged(NestedScrollView scrollView, int x, int y, int oldX, int oldY);

    /*** 在滑动的时候调用，scrollY为已滑动的距离*/
    void onScroll(int scrollY);
}
