package com.zhowin.library.view;


import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhowin.library.R;

/**
 * author      : Z_B
 * date       : 2019/5/20
 * function  :  简单的Android 通用标题栏
 */
public class ZhoSimpleTitleView extends FrameLayout implements View.OnClickListener {
    //根布局
    private RelativeLayout mRootLayout;
    //Title的TextView控件
    private TextView mTvTitle;
    //左边布局
    private LinearLayout mLlLeftLayout;
    //左边ImageView控件
    private ImageView mIvLeft;
    //左边TextView控件
    private TextView mTvLeft;
    //右边布局
    private LinearLayout mLlRightLayout;
    //右边ImageView控件
    private ImageView mIvRight;
    //右边TextView控件
    private TextView mTvRight;
    //Title文字
    private String mTitle;
    //Title字体颜色
    private int mTitleColor;
    //Title字体大小
    private int mTitleSize;
    //Title是否显示
    private boolean mTitleVisibility;
    //左边 ICON 引用的资源ID
    private int mLeftIcon;
    //右边 ICON 引用的资源ID
    private int mRightIcon;
    //左边 ICON 是否显示
    private boolean mLeftIconVisibility;
    //右边 ICON 是否显示
    private boolean mRightIconVisibility;
    //左边文字
    private String mLeftText;
    //左边字体颜色
    private int mLeftTextColor;
    //左边字体大小
    private int mLeftTextSize;
    //左边文字是否显示
    private boolean mLeftTextVisibility;
    //右边文字
    private String mRightText;
    //右边字体颜色
    private int mRightTextColor;
    //右边字体大小
    private int mRightTextSize;
    //右边文字是否显示
    private boolean mRightTextVisibility;
    //上下文
    private Context mContext;

    public ZhoSimpleTitleView(@NonNull Context context) {
        super(context);
    }

    public ZhoSimpleTitleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(final Context context, AttributeSet attrs) {
        //导入布局
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.include_simple_title_layout, this);
        mRootLayout = findViewById(R.id.rootLayout);
        mTvTitle = findViewById(R.id.tvTitleText);
        mLlLeftLayout = findViewById(R.id.llLeftLayout);
        mIvLeft = findViewById(R.id.ivLeftIcon);
        mTvLeft = findViewById(R.id.tvLeftText);
        mIvRight = findViewById(R.id.ivRightIcon);
        mLlRightLayout = findViewById(R.id.llRightLayout);
        mTvRight = findViewById(R.id.tvRightText);
        mLlLeftLayout.setOnClickListener(this);
        //获得这个控件对应的属性。
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ZhoSimpleTitleView);
        try {
            //标题
            mTitle = typedArray.getString(R.styleable.ZhoSimpleTitleView_title);
            //标题颜色
            mTitleColor = typedArray.getColor(R.styleable.ZhoSimpleTitleView_titleColor, getResources().getColor(R.color.white));
            //标题字体大小
            mTitleSize = typedArray.getDimensionPixelSize(R.styleable.ZhoSimpleTitleView_titleSize, dp2px(context, 16));
            //标题是否显示
            mTitleVisibility = typedArray.getBoolean(R.styleable.ZhoSimpleTitleView_titleVisibility, true);
            //左边图标
            mLeftIcon = typedArray.getResourceId(R.styleable.ZhoSimpleTitleView_leftIcon, R.drawable.svg_black_return_back);
            //左边图标是否显示
            mLeftIconVisibility = typedArray.getBoolean(R.styleable.ZhoSimpleTitleView_leftIconVisibility, true);
            //左边问题
            mLeftText = typedArray.getString(R.styleable.ZhoSimpleTitleView_leftText);
            //左边字体颜色
            mLeftTextColor = typedArray.getColor(R.styleable.ZhoSimpleTitleView_leftTextColor, getResources().getColor(R.color.white));
            //左侧标题字体大小
            mLeftTextSize = typedArray.getDimensionPixelSize(R.styleable.ZhoSimpleTitleView_leftTextSize, dp2px(context, 14));
            //左侧标题是否显示
            mLeftTextVisibility = typedArray.getBoolean(R.styleable.ZhoSimpleTitleView_leftTextVisibility, false);
            //右边文字
            mRightText = typedArray.getString(R.styleable.ZhoSimpleTitleView_rightText);
            //右边字体颜色
            mRightTextColor = typedArray.getColor(R.styleable.ZhoSimpleTitleView_rightTextColor, getResources().getColor(R.color.white));
            //右边图标
            mRightIcon = typedArray.getResourceId(R.styleable.ZhoSimpleTitleView_rightIcon, R.drawable.svg_right_more);
            //右边图标是否显示
            mRightIconVisibility = typedArray.getBoolean(R.styleable.ZhoSimpleTitleView_rightIconVisibility, false);
            //右边标题字体大小
            mRightTextSize = typedArray.getDimensionPixelSize(R.styleable.ZhoSimpleTitleView_rightTextSize, dp2px(context, 14));
            //右边标题是否显示
            mRightTextVisibility = typedArray.getBoolean(R.styleable.ZhoSimpleTitleView_rightTextVisibility, false);
        } finally {
            //回收这个对象
            typedArray.recycle();
        }
        if (!TextUtils.isEmpty(mTitle)) {
            setTitle(mTitle);
        }
        if (mTitleColor != 0) {
            setTitleColor(mTitleColor);
        }

        if (mTitleSize != 0) {
            setTitleSize(mTitleSize);
        }

        if (mLeftIcon != 0) {
            setLeftIcon(mLeftIcon);
        }

        if (mRightIcon != 0) {
            setRightIcon(mRightIcon);
        }

        setTitleVisibility(mTitleVisibility);

        setLeftText(mLeftText);

        setLeftTextColor(mLeftTextColor);

        setLeftTextSize(mLeftTextSize);

        setLeftTextVisibility(mLeftTextVisibility);

        setRightText(mRightText);

        setRightTextColor(mRightTextColor);

        setRightTextSize(mRightTextSize);

        setRightTextVisibility(mRightTextVisibility);

        setLeftIconVisibility(mLeftIconVisibility);

        setRightIconVisibility(mRightIconVisibility);

    }


    public RelativeLayout getRootLayout() {
        return mRootLayout;
    }

    public TextView getTvTitle() {
        return mTvTitle;
    }

    public LinearLayout getLlLeft() {
        return mLlLeftLayout;
    }

    public ImageView getIvLeft() {
        return mIvLeft;
    }

    public TextView getTvLeft() {
        return mTvLeft;
    }

    public LinearLayout getLlRight() {
        return mLlRightLayout;
    }

    public ImageView getIvRight() {
        return mIvRight;
    }

    public TextView getTvRight() {
        return mTvRight;
    }

    public boolean isTitleVisibility() {
        return mTitleVisibility;
    }

    public void setTitleVisibility(boolean titleVisibility) {
        mTitleVisibility = titleVisibility;
        if (mTitleVisibility) {
            mTvTitle.setVisibility(VISIBLE);
        } else {
            mTvTitle.setVisibility(GONE);
        }
    }

    public String getLeftText() {
        return mLeftText;
    }

    public void setLeftText(String leftText) {
        if (TextUtils.isEmpty(leftText)) return;
        mLeftText = leftText;
        mTvLeft.setText(mLeftText);

    }

    public int getLeftTextColor() {
        return mLeftTextColor;
    }

    public void setLeftTextColor(int leftTextColor) {
        mLeftTextColor = leftTextColor;
        mTvLeft.setTextColor(mLeftTextColor);
    }

    public int getLeftTextSize() {
        return mLeftTextSize;
    }

    public void setLeftTextSize(int leftTextSize) {
        mLeftTextSize = leftTextSize;
        mTvLeft.setTextSize(TypedValue.COMPLEX_UNIT_PX, mLeftTextSize);
    }

    public boolean isLeftTextVisibility() {
        return mLeftTextVisibility;
    }

    public void setLeftTextVisibility(boolean leftTextVisibility) {
        mLeftTextVisibility = leftTextVisibility;
        if (mLeftTextVisibility) {
            mTvLeft.setVisibility(VISIBLE);
        } else {
            mTvLeft.setVisibility(GONE);
        }
    }

    public String getRightText() {
        return mRightText;
    }

    public void setRightText(String rightText) {
        if (TextUtils.isEmpty(rightText)) return;
        mRightText = rightText;
        mTvRight.setText(mRightText);

    }

    public int getRightTextColor() {
        return mRightTextColor;
    }

    public void setRightTextColor(int rightTextColor) {
        mRightTextColor = rightTextColor;
        mTvRight.setTextColor(mRightTextColor);
    }

    public int getRightTextSize() {
        return mRightTextSize;
    }

    public void setRightTextSize(int rightTextSize) {
        mRightTextSize = rightTextSize;
        mTvRight.setTextSize(TypedValue.COMPLEX_UNIT_PX, mRightTextSize);
    }


    public boolean isRightTextVisibility() {
        return mRightTextVisibility;
    }

    public void setRightTextVisibility(boolean rightTextVisibility) {
        mRightTextVisibility = rightTextVisibility;
        if (mRightTextVisibility) {
            mTvRight.setVisibility(VISIBLE);
            if (isRightIconVisibility()) {
                mTvRight.setPadding(0, 0, 0, 0);
            }
        } else {
            mTvRight.setVisibility(GONE);
        }
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
        mTvTitle.setText(mTitle);
    }

    public int getTitleColor() {
        return mTitleColor;
    }

    public void setTitleColor(int titleColor) {
        mTitleColor = titleColor;
        mTvTitle.setTextColor(mTitleColor);
    }

    public int getTitleSize() {
        return mTitleSize;
    }

    public void setTitleSize(int titleSize) {
        mTitleSize = titleSize;
        mTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleSize);
    }

    public int getLeftIcon() {
        return mLeftIcon;
    }

    public void setLeftIcon(int leftIcon) {
        mLeftIcon = leftIcon;
        mIvLeft.setImageResource(mLeftIcon);
    }

    public int getRightIcon() {
        return mRightIcon;
    }

    public void setRightIcon(int rightIcon) {
        mRightIcon = rightIcon;
        mIvRight.setImageResource(mRightIcon);
    }

    public boolean isLeftIconVisibility() {
        return mLeftIconVisibility;
    }

    public void setLeftIconVisibility(boolean leftIconVisibility) {
        mLeftIconVisibility = leftIconVisibility;
        if (mLeftIconVisibility) {
            mIvLeft.setVisibility(VISIBLE);
        } else {
            mIvLeft.setVisibility(GONE);
        }
    }

    public boolean isRightIconVisibility() {
        return mRightIconVisibility;
    }

    public void setRightIconVisibility(boolean rightIconVisibility) {
        mRightIconVisibility = rightIconVisibility;
        if (mRightIconVisibility) {
            mIvRight.setVisibility(VISIBLE);
        } else {
            mIvRight.setVisibility(GONE);
        }
    }

    public void setLeftOnClickListener(OnClickListener onClickListener) {
        mLlLeftLayout.setOnClickListener(onClickListener);
    }

    public void setRightOnClickListener(OnClickListener onClickListener) {
        mLlRightLayout.setOnClickListener(onClickListener);
    }

    public void setLeftTextOnClickListener(OnClickListener onClickListener) {
        mTvLeft.setOnClickListener(onClickListener);
    }

    public void setRightTextOnClickListener(OnClickListener onClickListener) {
        mTvRight.setOnClickListener(onClickListener);
    }

    public void setLeftIconOnClickListener(OnClickListener onClickListener) {
        mIvLeft.setOnClickListener(onClickListener);
    }

    public void setRightIconOnClickListener(OnClickListener onClickListener) {
        mIvRight.setOnClickListener(onClickListener);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.llLeftLayout) {
            hideSoftKeyboard((Activity) mContext);
            ((Activity) mContext).finish();
        }
    }

    public int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null
                && activity.getCurrentFocus().getWindowToken() != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }
}
