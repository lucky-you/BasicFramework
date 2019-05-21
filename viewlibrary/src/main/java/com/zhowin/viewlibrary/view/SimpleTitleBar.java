package com.zhowin.viewlibrary.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
public class SimpleTitleBar extends FrameLayout {

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

    private void initViews() {
        rlTitleBarLayout = findViewById(R.id.rlTitleBarLayout);
        llLeftLayout = findViewById(R.id.llLeftLayout);
        ivLeftIcon = findViewById(R.id.ivLeftIcon);
        tvLeftTextTitle = findViewById(R.id.tvLeftTextTitle);
        tvTitleText = findViewById(R.id.tvTitleText);
        llRightLayout = findViewById(R.id.llRightLayout);
        ivRightIcon = findViewById(R.id.ivRightIcon);
        tvRightText = findViewById(R.id.tvRightText);
        bottomDivideLine = findViewById(R.id.bottomDivideLine);
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

        setTitleText(titleText);
        setTitleTextColor(titleTextColor);
        setTitleTextSize(titleTextSize);

        //左侧布局
        leftIconResId = att.getResourceId(R.styleable.SimpleTitleBar_leftIcon, 0);
        leftTextTitle = att.getString(R.styleable.SimpleTitleBar_leftTitle);
        leftTextColor = att.getColor(R.styleable.SimpleTitleBar_leftTextColor, defaultTextColor);
        leftTextSize = att.getDimensionPixelSize(R.styleable.SimpleTitleBar_leftTextSize, 15);
        clickLeftIsFinish = att.getBoolean(R.styleable.SimpleTitleBar_leftIsFinish, true);

        isShowLeftLayout = att.getBoolean(R.styleable.SimpleTitleBar_leftLayoutVisible, true);
        isShowLeftIcon = att.getBoolean(R.styleable.SimpleTitleBar_leftIconVisible, true);
        isShowLeftText = att.getBoolean(R.styleable.SimpleTitleBar_leftTextVisible, false);
        if (isShowLeftLayout) {
            isShowLeftLayout(isShowLeftLayout);
            setLeftAction((clickLeftIsFinish && (getContext() instanceof Activity)) ? new FinishAction((Activity) getContext()) : null);
            if (!TextUtils.isEmpty(leftTextTitle) && leftIconResId == 0) {
                isShowLeftText(isShowLeftText);
                isShowLeftIcon(isShowLeftIcon);
                setLeftText(leftTextTitle);
                setLeftTextColor(leftTextColor);
                setLeftTextSize(leftTextSize);
            } else if (TextUtils.isEmpty(leftTextTitle) && leftIconResId != 0) {
                isShowLeftText(isShowLeftText);
                isShowLeftIcon(isShowLeftIcon);
                setLeftIconResId(leftIconResId);
            } else if (!TextUtils.isEmpty(leftTextTitle) && leftIconResId != 0) {
                isShowLeftText(isShowLeftText);
                isShowLeftIcon(isShowLeftIcon);
                setLeftText(leftTextTitle);
                setLeftTextColor(leftTextColor);
                setLeftTextSize(leftTextSize);
                setLeftIconResId(leftIconResId);
            }
        } else {
            isShowLeftLayout(isShowLeftLayout);
        }
        //右侧布局
        rightIconResId = att.getResourceId(R.styleable.SimpleTitleBar_rightIcon, 0);
        rightTextTitle = att.getString(R.styleable.SimpleTitleBar_rightTextTitle);
        rightTextColor = att.getColor(R.styleable.SimpleTitleBar_rightTextColor, defaultTextColor);
        rightTextSize = att.getDimensionPixelSize(R.styleable.SimpleTitleBar_rightTextSize, 16);

        isShowRightLayout = att.getBoolean(R.styleable.SimpleTitleBar_rightLayoutVisible, false);
        isShowRightIcon = att.getBoolean(R.styleable.SimpleTitleBar_rightIconVisible, false);
        isShowRightText = att.getBoolean(R.styleable.SimpleTitleBar_rightTextVisible, false);
        if (isShowRightLayout) {
            setRightAction(new FinishAction((Activity) getContext()));
            isShowRightLayout(isShowRightLayout);
            if (!TextUtils.isEmpty(rightTextTitle) && rightIconResId == 0) {
                setRightText(rightTextTitle);
                setRightTextColor(rightTextColor);
                setRightTextSize(rightTextSize);
                isShowRightText(isShowRightText);
                isShowRightIcon(isShowRightIcon);
            } else if (TextUtils.isEmpty(rightTextTitle) && rightIconResId != 0) {
                setRightIconResId(rightIconResId);
                isShowRightText(isShowRightText);
                isShowRightIcon(isShowRightIcon);
            } else if (!TextUtils.isEmpty(rightTextTitle) && rightIconResId != 0) {
                setRightText(rightTextTitle);
                setRightTextColor(rightTextColor);
                setRightTextSize(rightTextSize);
                setRightIconResId(rightIconResId);
                isShowRightText(isShowRightText);
                isShowRightIcon(isShowRightIcon);
            }
        } else {
            isShowRightLayout(isShowRightLayout);
        }
        //底部分割布局
        isShowBottomDivideLine = att.getBoolean(R.styleable.SimpleTitleBar_bottomDividerLineVisible, true);
        bottomDivideHeight = att.getDimensionPixelSize(R.styleable.SimpleTitleBar_bottomDividerLineHeight, 2);
        bottomDivideColor = att.getColor(R.styleable.SimpleTitleBar_bottomDividerLineColor, context.getResources().getColor(R.color.color_f2f2f2));
        bottomLineFitStatusBar = att.getBoolean(R.styleable.SimpleTitleBar_bottomDividerLineFitStatusBar, true);
        isShowBottomDividerLine(isShowBottomDivideLine);
        setBottomDividerLineHeight(bottomDivideHeight);
        setBottomDivideColor(bottomDivideColor);
        att.recycle();

    }


    public SimpleTitleBar isShowLeftLayout(boolean isShow) {
        this.llLeftLayout.setVisibility(isShow ? View.VISIBLE : View.GONE);
        return this;
    }

    public SimpleTitleBar setLeftAction(@NonNull OnClickListener click) {
        if (click == null) return this;
        this.llLeftLayout.setOnClickListener(click);
        return this;
    }


    public SimpleTitleBar isShowLeftIcon(boolean isShow) {
        this.ivLeftIcon.setVisibility(isShow ? View.VISIBLE : View.GONE);
        return this;
    }

    public SimpleTitleBar isShowLeftText(boolean isShow) {
        this.tvLeftTextTitle.setVisibility(isShow ? View.VISIBLE : View.GONE);
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

    public SimpleTitleBar isShowRightLayout(boolean isShow) {
        this.llRightLayout.setVisibility(isShow ? View.VISIBLE : View.GONE);
        return this;
    }

    public SimpleTitleBar isShowRightIcon(boolean isShow) {
        this.ivRightIcon.setVisibility(isShow ? View.VISIBLE : View.GONE);
        return this;
    }

    public SimpleTitleBar isShowRightText(boolean isShow) {
        this.tvRightText.setVisibility(isShow ? View.VISIBLE : View.GONE);
        return this;
    }

    public SimpleTitleBar setRightIconResId(int resId) {
        this.ivRightIcon.setImageResource(resId);
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

    public SimpleTitleBar setRightAction(@NonNull OnClickListener click) {
        if (click == null) return this;
        this.llRightLayout.setOnClickListener(click);
        return this;
    }

    public SimpleTitleBar isShowBottomDividerLine(boolean isShow) {
        this.bottomDivideLine.setVisibility(isShow ? View.VISIBLE : View.GONE);
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

    public SimpleTitleBar setBottomDivideColor(int color) {
        this.bottomDivideLine.setBackgroundColor(color);
        return this;
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
