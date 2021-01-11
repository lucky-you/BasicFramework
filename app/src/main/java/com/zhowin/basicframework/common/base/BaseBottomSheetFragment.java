package com.zhowin.basicframework.common.base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.blankj.utilcode.util.ScreenUtils;
import com.zhowin.basicframework.R;


/**
 * author Z_B
 * date :2019/11/25 8:47
 * description:BottomSheet 的基类
 */
public abstract class BaseBottomSheetFragment extends BottomSheetDialogFragment {
    protected Context mContext;
    protected View rootView;
    protected BottomSheetBehavior mBehavior;
    protected Dialog dialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mBehavior != null)
            mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //解除缓存View和当前ViewGroup的关联
        ((ViewGroup) (rootView.getParent())).removeView(rootView);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (isRemoveMask()) {
            //设置弹起时去掉背景的遮罩效果
            setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.DialogFragmentStyle);
        }
        //每次打开都调用该方法 类似于onCreateView 用于返回一个Dialog实例
        dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        if (rootView == null) {
            //缓存下来的View 当为空时才需要初始化 并缓存
            rootView = View.inflate(mContext, getLayoutResId(), null);
            initView();
        }
        if (isFixedHeight()) {
            //固定高度
            int screenHeight = ScreenUtils.getScreenHeight();
            int height = (screenHeight * 3) >> 2;//屏幕高的75%
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, height);
            dialog.setContentView(rootView, layoutParams);//设置View 并带有布局参数的
        } else {
            //自适应高度,设置View重新关联
            setContentView(dialog);
        }
        mBehavior = BottomSheetBehavior.from((View) rootView.getParent());
        mBehavior.setHideable(true);
        //让父View背景透明 是圆角边的关键
        ((View) rootView.getParent()).setBackgroundColor(Color.TRANSPARENT);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        rootView.post(new Runnable() {
            @Override
            public void run() {
                /**
                 * PeekHeight默认高度256dp 会在该高度上悬浮
                 * 设置等于view的高 就不会卡住
                 */
                mBehavior.setPeekHeight(rootView.getHeight());
            }
        });
        resetView();
        return dialog;
    }


    /**
     * 设置显示的View到Dialog中
     * 抽象方法 子类可重写
     * 默认添加的View 高度为Wrap 某些场景需要固定高度
     */
    protected void setContentView(Dialog dialog) {
        dialog.setContentView(rootView);
    }

    /**
     * 获取资源id
     */
    public abstract int getLayoutResId();

    /**
     * 初始化View和设置数据等操作的方法
     */
    public abstract void initView();

    /**
     * 高度是否固定,默认不固定
     */
    public boolean isFixedHeight() {
        return false;
    }

    /**
     * 是否去掉遮罩,默认不去掉
     */
    public boolean isRemoveMask() {
        return false;
    }

    /**
     * 重置的View和数据的空方法 子类可以选择实现
     * 为避免多次inflate 父类缓存rootView
     * 所以不会每次打开都调用{@link #initView()}方法
     * 但是每次都会调用该方法 给子类能够重置View和数据
     */
    public void resetView() {

    }

    /**
     * 是否开启
     */
    public boolean isShowing() {
        return dialog != null && dialog.isShowing();
    }

    /**
     * 使用关闭弹框 是否使用动画可选
     * 使用动画 同时切换界面Aty会卡顿 建议直接关闭
     */
    public void close(boolean isAnimation) {
        if (isAnimation) {
            if (mBehavior != null) {
                mBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        } else {
            dismiss();
        }
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if (!this.isAdded()) {
            super.show(manager, tag);
        } else {
            Log.d("xy", this + " has add to FragmentManager");
        }
    }
}
