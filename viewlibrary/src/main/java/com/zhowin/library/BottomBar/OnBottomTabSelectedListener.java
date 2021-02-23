package com.zhowin.library.BottomBar;

/**
 * author Z_B
 * date :2020/1/8 12:01
 * description:
 */
public interface OnBottomTabSelectedListener {

    void onTabSelected(int position, int prePosition);

    void onTabUnselected(int position);

    void onTabReselected(int position);
}
