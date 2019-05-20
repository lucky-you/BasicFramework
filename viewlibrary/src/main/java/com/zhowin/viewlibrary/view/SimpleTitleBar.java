package com.zhowin.viewlibrary.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhowin.viewlibrary.R;

import java.lang.ref.WeakReference;

/**
 * author      : Z_B
 * date       : 2019/5/20
 * function  :  简单的Android 通用标题栏
 */
public class SimpleTitleBar extends FrameLayout implements View.OnClickListener {

    private View rlTitleBarLayout; //标题栏根布局

    private View llLeftLayout;//左侧布局
    private ImageView ivLeftIcon; //左侧icon
    private TextView tvLeftTextTitle; //左侧文字
    private boolean isShowLeftLayout;//是否显示左侧布局
    private boolean isShowLeftIcon;//是否显示左侧icon
    private boolean isShowLeftText;//是否显示左侧文本

    private int leftIconResId; // 左侧icon资源id
    private int leftTextColor;//左侧文本颜色
    private int leftTextSize;//左侧文本大小
    private String leftTextTitle;//左侧文本内容
    private boolean clickLeftIsFinish;//点击左侧是否finish


    private TextView tvTitleText; //标题
    private int titleTextColor;// 标题颜色
    private int titleTextSize;//标题字体大小
    private String titleText;//标题文本


    private View llRightLayout; //右侧布局
    private ImageView ivRightIcon;//右侧icon
    private TextView tvRightText;// 右侧文本
    private boolean isShowRightLayout;//是否显示右侧布局
    private boolean isShowRightIcon;//是否显示右侧icon
    private boolean isShowRightText;//是否显示右侧文本

    private int rightIconResId;//右侧图片资源
    private int rightTextColor;// 右侧文本颜色
    private int rightTextSize;//右侧文本大小
    private String rightTextTitle;//右侧文本内容


    private View bottomDivideLine; //底部分割线
    private boolean isShowBottomDivideLine;//是否显示底部分割线
    private int bottomDivideHeight;// 底部分割线高度
    private int bottomDivideColor;//底部分割线颜色
    private Drawable  bottomDrawable;//底部分割线默认样式


    private boolean bottomLineFitStatusBar;//是否与状态栏适配

    public SimpleTitleBar(@NonNull Context context) {
        this(context, null, 0);
    }

    public SimpleTitleBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleTitleBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateRootView();
        initViews();
        setViewAttrs(context, attrs);

    }


    private void inflateRootView() {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        layoutInflater.inflate(R.layout.include_simple_title_layout, this, true);
    }

    private <T extends View> T get(@IdRes int id) {
        return (T) findViewById(id);
    }

    private void initViews() {
        rlTitleBarLayout = get(R.id.rlTitleBarLayout);
        llLeftLayout = get(R.id.llLeftLayout);
        ivLeftIcon = get(R.id.ivLeftIcon);
        tvLeftTextTitle = get(R.id.tvLeftTextTitle);
        tvTitleText = get(R.id.tvTitleText);
        llRightLayout = get(R.id.llRightLayout);
        ivRightIcon = get(R.id.ivRightIcon);
        tvRightText = get(R.id.tvRightText);
        bottomDivideLine = get(R.id.bottomDivideLine);
        llLeftLayout.setOnClickListener(new FinishAction((Activity) getContext()));
    }

    private void setViewAttrs(Context context, AttributeSet attrs) {
        int defaultTextColor = context.getResources().getColor(R.color.main_text_color);
        TypedArray att = context.obtainStyledAttributes(attrs, R.styleable.SimpleTitleBar);
        TypedArray typedArray = context.obtainStyledAttributes(new int[]{R.attr.colorPrimary});
        int color = typedArray.getColor(0, Color.WHITE);
        //整体背景
        final int backgroundColor = att.getColor(R.styleable.SimpleTitleBar_backgroundColor, color);
        final Drawable backgroundRes = att.getDrawable(R.styleable.SimpleTitleBar_backgroundDrawable);
        if (backgroundRes != null) {
            rlTitleBarLayout.setBackground(backgroundRes);
        } else {
            rlTitleBarLayout.setBackgroundColor(backgroundColor);
        }
        //标题布局
        titleText = att.getString(R.styleable.SimpleTitleBar_title);
        titleTextColor = att.getColor(R.styleable.SimpleTitleBar_titleColor, defaultTextColor);
        titleTextSize = att.getDimensionPixelSize(R.styleable.SimpleTitleBar_titleSize, 17);


        //左侧布局
        leftIconResId = att.getResourceId(R.styleable.SimpleTitleBar_leftIcon, R.drawable.icon_back_black);
        leftTextTitle = att.getString(R.styleable.SimpleTitleBar_leftTitle);
        leftTextColor = att.getColor(R.styleable.SimpleTitleBar_leftTextColor, defaultTextColor);
        leftTextSize = att.getDimensionPixelSize(R.styleable.SimpleTitleBar_leftTextSize, 15);
        clickLeftIsFinish = att.getBoolean(R.styleable.SimpleTitleBar_leftIsFinish, true);

        isShowLeftLayout = att.getBoolean(R.styleable.SimpleTitleBar_leftLayoutVisible, true);
        isShowLeftIcon = att.getBoolean(R.styleable.SimpleTitleBar_leftIconVisible, true);
        isShowLeftText = att.getBoolean(R.styleable.SimpleTitleBar_leftTextVisible, false);

        //右侧布局
        rightIconResId = att.getResourceId(R.styleable.SimpleTitleBar_rightIcon, -1);
        rightTextTitle = att.getString(R.styleable.SimpleTitleBar_rightTextTitle);
        rightTextColor = att.getColor(R.styleable.SimpleTitleBar_rightTextColor, defaultTextColor);
        rightTextSize = att.getDimensionPixelSize(R.styleable.SimpleTitleBar_rightTextSize, 16);

        isShowRightLayout = att.getBoolean(R.styleable.SimpleTitleBar_rightLayoutVisible, false);
        isShowRightIcon = att.getBoolean(R.styleable.SimpleTitleBar_rightIconVisible, false);
        isShowRightText = att.getBoolean(R.styleable.SimpleTitleBar_rightTextVisible, false);

        //底部分割布局
        isShowBottomDivideLine = att.getBoolean(R.styleable.SimpleTitleBar_bottomDividerLineVisible, true);
        bottomDrawable = att.getDrawable(R.styleable.SimpleTitleBar_bottomDividerLine);
        bottomDivideHeight = att.getDimensionPixelSize(R.styleable.SimpleTitleBar_bottomDividerLineHeight, 1);
        bottomDivideColor = att.getColor(R.styleable.SimpleTitleBar_bottomDividerLineColor, defaultTextColor);

        bottomLineFitStatusBar = att.getBoolean(R.styleable.SimpleTitleBar_bottomDividerLineFitStatusBar, true);

        if (bottomDrawable != null) {
            bottomDivideLine.setBackground(backgroundRes);
        } else {
            bottomDivideLine.setBackgroundColor(backgroundColor);
        }

        if (!isInEditMode()) {
            ViewGroup.LayoutParams layoutParams = bottomDivideLine.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = generateDefaultLayoutParams();
            }
            layoutParams.height = bottomDivideHeight;
        }
        att.recycle();

    }


    public SimpleTitleBar isShowLeftLayout(boolean isShow) {
        this.llLeftLayout.setVisibility(isShow ? View.VISIBLE : View.GONE);
        return this;
    }

    public SimpleTitleBar setLeftIconResId(int resId) {
        this.ivLeftIcon.setImageResource(resId);
        return this;
    }

    public SimpleTitleBar setLeftText(String text) {
        if (TextUtils.isEmpty(text)) return this;
        this.tvLeftTextTitle.setText(text);
        return this;
    }

    public SimpleTitleBar setLeftTextColor(int textColor) {
        this.tvLeftTextTitle.setTextColor(textColor);
        return this;
    }

    public SimpleTitleBar setLeftTextSize(int textSize) {
        this.tvLeftTextTitle.setTextSize(textSize);
        return this;
    }

    public SimpleTitleBar setTitleText(String text) {
        if (TextUtils.isEmpty(text)) return this;
        this.tvTitleText.setText(text);
        return this;
    }

    public SimpleTitleBar setTitleTextColor(int textColor) {
        this.tvTitleText.setTextColor(textColor);
        return this;
    }

    public SimpleTitleBar setTitleTextSize(int textSize) {
        this.tvTitleText.setTextSize(textSize);
        return this;
    }


    public SimpleTitleBar setRightIconResId(int resId) {
        this.ivRightIcon.setImageResource(resId);
        return this;
    }

    public SimpleTitleBar isShowRightLayout(boolean isShow) {
        this.llRightLayout.setVisibility(isShow ? View.VISIBLE : View.GONE);
        return this;
    }

    public SimpleTitleBar setRightText(String text) {
        if (TextUtils.isEmpty(text)) return this;
        this.tvRightText.setText(text);
        return this;
    }

    public SimpleTitleBar setRightTextColor(int textColor) {
        this.tvRightText.setTextColor(textColor);
        return this;
    }

    public SimpleTitleBar setRightTextSize(int textSize) {
        this.tvRightText.setTextSize(textSize);
        return this;
    }

    public SimpleTitleBar setBottomDividerLineHeight(int height) {
        if (!isInEditMode()) {
            ViewGroup.LayoutParams layoutParams = bottomDivideLine.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = generateDefaultLayoutParams();
            }
            layoutParams.height = height;
        }
        return this;
    }


    @Override
    public void onClick(View view) {

    }


    class FinishAction implements OnClickListener {

        private WeakReference<Activity> context;

        public FinishAction(Activity act) {
            this.context = new WeakReference<Activity>(act);
        }

        @Override
        public void onClick(View v) {
            Activity activity = context.get();
            if (activity != null) {
                activity.finish();
            }
        }
    }
}
