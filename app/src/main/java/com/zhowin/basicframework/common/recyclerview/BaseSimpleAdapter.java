package com.zhowin.basicframework.common.recyclerview;

import android.support.annotation.LayoutRes;

import java.util.List;

/**
 * blog  : http://blankj.com
 */
public abstract class BaseSimpleAdapter<M> extends BaseAdapter<M> {

    private final int mLayoutId;

    public BaseSimpleAdapter(List<M> list, @LayoutRes int layoutId) {
        setData(list);
        mLayoutId = layoutId;
    }

    @Override
    protected int bindLayout(final int viewType) {
        return mLayoutId;
    }

}
