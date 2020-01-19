package com.zhowin.basicframework.common.download;

import android.view.View;
import android.widget.TextView;

import com.zhowin.basicframework.R;
import com.zhowin.basicframework.common.base.BaseDialogFragment;
import com.zhowin.basicframework.common.utils.ToastUtils;

/**
 * author Z_B
 * date :2020/1/7 11:04
 * description: 底部弹出的fragment
 */
public class BottomShowFragment extends BaseDialogFragment {
    TextView tvTypeSeven;

    @Override
    public int getLayoutId() {
        return R.layout.down_load_fragment_layout;
    }

    @Override
    public void initView() {
        tvTypeSeven = get(R.id.tvTypeSeven);
        tvTypeSeven.setOnClickListener(this::onClick);
    }

    @Override
    public void initData() {
        tvTypeSeven.setText("视频通话");
    }

    @Override
    public void onClick(View view) {
        if (isFastClick()) return;
        if (view.getId() == R.id.tvTypeSeven) {
            ToastUtils.showShort("点击事件");
        }
    }
}
