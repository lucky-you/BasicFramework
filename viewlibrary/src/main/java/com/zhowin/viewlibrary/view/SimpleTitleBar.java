package com.zhowin.viewlibrary.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
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
    private ImageView ivLeftIcon;
    private TextView tvLeftTextTitle;
    private boolean isShowLeftLayout ;
    private boolean isShowLeftIcon ;
    private boolean isShowLeftText ;

    private int leftIconResId;
    private int leftTextColor;
    private int leftTextSize;
    private String leftTextTitle;
    private boolean clickLeftIsFinish;//点击左侧是否finish


    private TextView tvTitleText; //标题
    private int titleTextColor;
    private int titleTextSize;
    private String titleText;


    private View llRightLayout; //右侧布局
    private ImageView ivRightIcon;
    private TextView tvRightText;
    private boolean isShowRightLayout;
    private boolean isShowRightIcon;
    private boolean isShowRightText;

    private int rightIconResId;
    private int rightTextColor;
    private int rightTextSize;
    private String rightTextTitle;


    private View bottomDivideLine; //底部分割线
    private boolean isShowBottomDivideLine ;
    private int bottomDivideHeight;
    private int bottomDivideColor;


    private boolean bottomLineFitStatusBar;

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

        setTitleBarTitle(titleText, titleTextColor, titleTextSize);

        //左侧布局
        leftIconResId = att.getResourceId(R.styleable.SimpleTitleBar_leftIcon, R.drawable.icon_back_black);
        leftTextTitle = att.getString(R.styleable.SimpleTitleBar_leftTitle);
        leftTextColor = att.getColor(R.styleable.SimpleTitleBar_leftTextColor, defaultTextColor);
        leftTextSize = att.getDimensionPixelSize(R.styleable.SimpleTitleBar_leftTextSize, 15);
        clickLeftIsFinish = att.getBoolean(R.styleable.SimpleTitleBar_leftIsFinish, true);

        isShowLeftLayout = att.getBoolean(R.styleable.SimpleTitleBar_leftLayoutVisible, true);
        isShowLeftIcon = att.getBoolean(R.styleable.SimpleTitleBar_leftIconVisible, true);
        isShowLeftText = att.getBoolean(R.styleable.SimpleTitleBar_leftTextVisible, false);


        setLeftLayout(leftIconResId, leftTextTitle, leftTextColor, new FinishAction((Activity) getContext()));

        //右侧布局
        rightIconResId = att.getResourceId(R.styleable.SimpleTitleBar_rightIcon, -1);
        rightTextTitle = att.getString(R.styleable.SimpleTitleBar_rightTextTitle);
        rightTextColor = att.getColor(R.styleable.SimpleTitleBar_rightTextColor, defaultTextColor);
        rightTextSize = att.getDimensionPixelSize(R.styleable.SimpleTitleBar_rightTextSize, 16);

        isShowRightLayout = att.getBoolean(R.styleable.SimpleTitleBar_rightLayoutVisible, false);
        isShowRightIcon = att.getBoolean(R.styleable.SimpleTitleBar_rightIconVisible, false);
        isShowRightText = att.getBoolean(R.styleable.SimpleTitleBar_rightTextVisible, false);

        showRightLayout(isShowRightLayout);
        setRightLayout(rightIconResId, rightTextTitle, rightTextColor, null);
        setRightTextSize(rightTextSize);

        //底部分割布局
        isShowBottomDivideLine = att.getBoolean(R.styleable.SimpleTitleBar_bottomDividerLineVisible, true);
        Drawable bottomDrawable = att.getDrawable(R.styleable.SimpleTitleBar_bottomDividerLine);
        bottomDivideHeight = att.getDimensionPixelSize(R.styleable.SimpleTitleBar_bottomDividerLineHeight, dp2px(1));
        bottomDivideColor = att.getColor(R.styleable.SimpleTitleBar_bottomDividerLineColor, defaultTextColor);

        bottomLineFitStatusBar = att.getBoolean(R.styleable.SimpleTitleBar_bottomDividerLineFitStatusBar, true);

        showBottomDivideLine(isShowBottomDivideLine);


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

    public SimpleTitleBar setTitleBarTitle(@NonNull String title) {
        return setTitleBarTitle(title, Color.TRANSPARENT, 17);
    }


    public SimpleTitleBar setTitleBarTitle(String title, @ColorInt int textColor, int textSize) {
        if (textColor != Color.TRANSPARENT) {
            this.tvTitleText.setTextColor(textColor);
        }
        this.tvTitleText.setTextSize(textSize);
        this.tvTitleText.setText(title);
        return this;
    }


    public SimpleTitleBar noBack() {
        ivLeftIcon.setVisibility(View.GONE);
        return this;
    }

    public SimpleTitleBar setLeftLayout(@DrawableRes int icon, @NonNull String text, @ColorInt int color, @NonNull OnClickListener click) {
        return this.setLeftIcon(icon).setLeftText(text).setLeftTextColor(color).setLeftAction(click);
    }

    public SimpleTitleBar setLeftIcon(@DrawableRes int icon) {
        if (icon == -1) {
            MarginLayoutParams layoutParams = (MarginLayoutParams) llLeftLayout.getLayoutParams();
            if (layoutParams != null) {
                layoutParams.leftMargin = 0;
            }
            llLeftLayout.setVisibility(GONE);
            return this;
        }
        llLeftLayout.setVisibility(View.VISIBLE);
        this.ivLeftIcon.setImageResource(icon);
        return this;
    }


    public SimpleTitleBar setLeftText(@NonNull String leftText) {
        if (TextUtils.isEmpty(leftText)) return this;
        llLeftLayout.setVisibility(View.VISIBLE);
        this.tvLeftTextTitle.setText(leftText);
        return this;
    }

    private SimpleTitleBar setLeftTextColor(int color) {
        this.tvLeftTextTitle.setTextColor(color);
        return this;
    }

    public SimpleTitleBar setLeftAction(@NonNull OnClickListener click) {
        if (click == null) return this;
        llLeftLayout.setVisibility(View.VISIBLE);
        this.tvLeftTextTitle.setOnClickListener(click);
        return this;
    }


    public void showRightLayout(boolean isShowRightLayout) {
        llRightLayout.setVisibility(isShowRightLayout ? View.VISIBLE : View.GONE);
    }

    public void
    showBottomDivideLine(boolean isShowBottomDivideLine) {
        bottomDivideLine.setVisibility(isShowBottomDivideLine ? View.VISIBLE : View.GONE);
    }

    public SimpleTitleBar setRightLayout(@DrawableRes int icon, @NonNull String text, @ColorInt int color, @NonNull OnClickListener click) {

        return this.setRightIcon(icon).setRightText(text).setRightTextColor(color).setRightAction(click);
    }


    public SimpleTitleBar setRightIcon(@DrawableRes int icon) {
        if (icon == -1) return this;
        llRightLayout.setVisibility(View.VISIBLE);
        this.ivRightIcon.setImageResource(icon);
        return this;
    }

    public SimpleTitleBar setRightText(@NonNull String rightText) {
        if (TextUtils.isEmpty(rightText)) return this;
        llRightLayout.setVisibility(View.VISIBLE);
        this.tvRightText.setText(rightText);
        return this;
    }

    private SimpleTitleBar setRightTextColor(int color) {
        this.tvRightText.setTextColor(color);
        return this;
    }

    private SimpleTitleBar setRightTextSize(int size) {
        this.tvRightText.setTextSize(size);
        return this;
    }

    public SimpleTitleBar setRightAction(@NonNull OnClickListener click) {
        if (click == null) return this;
        llRightLayout.setVisibility(View.VISIBLE);
        this.ivRightIcon.setOnClickListener(click);
        return this;
    }

    public SimpleTitleBar hideTitle() {
        tvTitleText.setVisibility(View.GONE);
        return this;
    }

    public SimpleTitleBar showTitle() {
        tvTitleText.setVisibility(View.VISIBLE);
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

    private int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }
}
