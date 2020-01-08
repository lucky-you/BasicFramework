package com.zhowin.basicframework.common.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.zhowin.basicframework.R;
import com.zhowin.basicframework.common.base.BaseActivity;
import com.zhowin.basicframework.common.download.DownLoadFragment;
import com.zhowin.basicframework.common.fragment.HomeFragment;
import com.zhowin.viewlibrary.BottomBar.BottomBarLayout;
import com.zhowin.viewlibrary.BottomBar.BottomBarTab;
import com.zhowin.viewlibrary.BottomBar.OnBottomTabSelectedListener;

public class MainActivity extends BaseActivity implements OnBottomTabSelectedListener {


    private BottomBarLayout mBottomBarLayout;

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int loadViewLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void bindViews(View contentView) {
        mBottomBarLayout = get(R.id.bottomBarTab);

    }

    @Override
    public void processLogic(Bundle savedInstanceState) {
        mBottomBarLayout
                .addItem(new BottomBarTab(mContext, R.drawable.ic_account_circle_white_24dp, "首页"))
                .addItem(new BottomBarTab(mContext, R.drawable.ic_account_circle_white_24dp, "消息"))
                .addItem(new BottomBarTab(mContext, R.drawable.ic_account_circle_white_24dp, "发现"))
                .addItem(new BottomBarTab(mContext, R.drawable.ic_account_circle_white_24dp, "我的"));


        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.flTabContainer, HomeFragment.newInstance())
                .commit();

        mBottomBarLayout.getItem(0).setUnreadCount(3);
        mBottomBarLayout.setOnTabSelectedListener(this);
    }


    @Override
    public void setClickListener(View view) {

    }


    @Override
    public void onTabSelected(int position, int prePosition) {
        BottomBarTab tab = mBottomBarLayout.getItem(0);
        if (position == 2) {
            tab.setUnreadCount(0);
            DownLoadFragment downLoadFragment = new DownLoadFragment();
            downLoadFragment.show(getSupportFragmentManager(), "SS");
        } else {
            tab.setUnreadCount(tab.getUnreadCount() + 1);
        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}





