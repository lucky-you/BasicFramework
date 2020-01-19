package com.zhowin.basicframework.common.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * author Z_B
 * date :2020/1/19 10:53
 * description:
 */
public class BaseTitlePageAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragments;
    public String[] titles;

    public BaseTitlePageAdapter(FragmentManager fm, List<Fragment> mFragments) {
        super(fm);
        this.mFragments = mFragments;
    }

    public BaseTitlePageAdapter(FragmentManager fm, List<Fragment> mFragments, String[] titles) {
        super(fm);
        this.mFragments = mFragments;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.isEmpty() ? 0 : mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles == null ? "" : titles[position];
    }
}
