package com.zhowin.basicframework.common.base;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhowin.basicframework.R;
import com.zhowin.basicframework.common.lib.LibFragment;
import com.zhowin.basicframework.common.view.TitleBuilder;


public abstract class BaseFragment extends LibFragment {


    public TitleBuilder initTitle(Object obj) {
        if (obj instanceof String) {
            return new TitleBuilder(mRootView).setTitleText((String) obj);
        } else {
            return new TitleBuilder(mRootView).setTitleText((int) obj);
        }
    }

    public TitleBuilder initTitleNoBack(Object obj) {
        if (obj instanceof String) {
            return new TitleBuilder(mRootView).setTitleText((String) obj).noBack();
        } else {
            return new TitleBuilder(mRootView).setTitleText((int) obj).noBack();
        }
    }


    @Override
    protected void firstLoad() {
        super.firstLoad();
    }

    public RecyclerView initCommonRecyclerView(BaseQuickAdapter adapter, RecyclerView.ItemDecoration decoration) {
        RecyclerView recyclerView = (RecyclerView) mRootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (decoration != null) {
            recyclerView.addItemDecoration(decoration);
        }
        recyclerView.setAdapter(adapter);
        return recyclerView;
    }

    public RecyclerView initGridRecyclerView(BaseQuickAdapter adapter, RecyclerView.ItemDecoration decoration, int spanCount) {
        return initGridRecyclerView((RecyclerView) mRootView.findViewById(R.id.recyclerView), adapter, decoration, spanCount);
    }

    public RecyclerView initGridRecyclerView(RecyclerView recyclerView, BaseQuickAdapter adapter, RecyclerView.ItemDecoration decoration, int spanCount) {
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), spanCount));
        if (decoration != null) {
            recyclerView.addItemDecoration(decoration);
        }
        recyclerView.setAdapter(adapter);
        return recyclerView;
    }
}
