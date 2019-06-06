package com.zhowin.basicframework.common.activity;

import com.zhowin.basicframework.R;
import com.zhowin.basicframework.common.base.BaseSplashActivity;
import com.zhowin.basicframework.common.utils.ActivityUtils;

/**
 * author      : Z_B
 * date       : 2019/6/6
 * function  : 启动页
 */
public class SplashActivity extends BaseSplashActivity {
    @Override
    protected void onCreateActivity() {
        initSplashView(R.drawable.ic_default_image_view);
        startSplash(false);

    }

    @Override
    protected void onSplashFinished() {
        ActivityUtils.startActivity(MainActivity.class);
        finish();
    }
}
